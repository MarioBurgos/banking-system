package com.bankingsystem.controller.impl;

import com.bankingsystem.classes.Address;
import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.dto.ThirdPartyDTO;
import com.bankingsystem.model.*;
import com.bankingsystem.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdministratorControllerImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private User admin, user;
    private Role adminRole1, adminRole2, userRole;
    private AccountHolder accountHolder;
    private Savings savingsAccount;
    private BalanceDTO balanceDTO;
    private ThirdPartyDTO thirdPartyDTO;

    @BeforeEach
    void setUp() {
        adminRole1 = new Role("ADMIN");
        adminRole2 = new Role("USER");
        userRole = new Role("USER");
        admin = new User("Administrator", "admin", passwordEncoder.encode("password"));
        user = new User("Regular user", "user", passwordEncoder.encode("password"));
        admin.setRoles(List.of(adminRole1, adminRole2));
        user.setRoles(List.of(userRole));
        userRepository.saveAll(List.of(admin, user));

        accountHolder = new AccountHolder("name", new Date(1111, 1, 1), new Address("street", "city", "postalCode", "country"), null, new ArrayList());
        accountHolderRepository.save(accountHolder);
        savingsAccount = new Savings(new Money(new BigDecimal("1000")), accountHolder, null, "secretKey");
        savingsRepository.save(savingsAccount);
        accountHolder.setAccounts(List.of(savingsAccount));
        accountHolderRepository.save(accountHolder);

        balanceDTO = new BalanceDTO();
        balanceDTO.setAccountId(savingsAccount.getId());
        balanceDTO.setAccountType("SAVINGS");
        balanceDTO.setAmount(new BigDecimal("2000"));

        thirdPartyDTO = new ThirdPartyDTO();
        thirdPartyDTO.setKey("key");
        thirdPartyDTO.setName("Name");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        savingsRepository.deleteAll();
    }

    @Test
    void checkBalance_NotLogged_403isForbidden() throws Exception {
        mvcResult = mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void checkBalance_LoggedAsRegularUser_403isForbidden() throws Exception {
        mvcResult = mockMvc.perform(get("/login")
                        .param("username", "user")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = jsonObject.getString("access_token");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        mvcResult = mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void checkBalance_LoggedAsAdministrator_200isOk() throws Exception {
        mvcResult = mockMvc.perform(get("/login")
                        .param("username", "admin")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = jsonObject.getString("access_token");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        mvcResult = mockMvc.perform(get("/accounts/1/balance")
                        .headers(httpHeaders)
                        .param("username", "admin")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateBalance_NotLogged_403isForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(balanceDTO);
        mvcResult = mockMvc.perform(patch("/accounts/"+savingsAccount.getId()+"/balance")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void updateBalance_LoggedAsRegularUser_403isForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(balanceDTO);
        MvcResult mvcResult = mockMvc.perform(get("/login")
                        .param("username", "user")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = jsonObject.getString("access_token");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        mvcResult = mockMvc.perform(patch("/accounts/"+savingsAccount.getId()+"/balance")
                        .headers(httpHeaders)
                        .param("username", "user")
                        .param("password", "password")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void updateBalance_LoggedAsAdministrator_204isNoContent() throws Exception {
        String body = objectMapper.writeValueAsString(balanceDTO);
        mvcResult = mockMvc.perform(get("/login")
                        .param("username", "admin")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = jsonObject.getString("access_token");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        mvcResult = mockMvc.perform(patch("/accounts/" + savingsAccount.getId() + "/balance")
                        .headers(httpHeaders)
                        .param("username", "admin")
                        .param("password", "password")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void addThirdParty_NotLogged_403isForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        mvcResult = mockMvc.perform(post("/thirdparty")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void addThirdParty_LoggedAsRegularUser_403isForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        MvcResult mvcResult = mockMvc.perform(get("/login")
                        .param("username", "user")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = jsonObject.getString("access_token");
        System.out.println(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        System.out.println(httpHeaders);
        mvcResult = mockMvc.perform(post("/thirdparty")
                        .headers(httpHeaders)
                        .param("username", "user")
                        .param("password", "password")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void addThirdParty_LoggedAsAdministrator_201isCreated() throws Exception {
        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        MvcResult mvcResult = mockMvc.perform(get("/login")
                        .param("username", "admin")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = jsonObject.getString("access_token");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        mvcResult = mockMvc.perform(post("/thirdparty")
                        .headers(httpHeaders)
                        .param("username", "admin")
                        .param("password", "password")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}