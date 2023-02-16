package com.bankingsystem.security;

import com.bankingsystem.filters.CustomAuthenticationFilter;
import com.bankingsystem.filters.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    // UserDetailsService is an interface provided by Spring Security that defines a way to retrieve user information
    // Implementation is in CustomUserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    // Autowired instance of the AuthenticationManagerBuilder
    @Autowired
    private AuthenticationManagerBuilder authManagerBuilder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CustomAuthenticationFilter instance created
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild());
        // set the URL that the filter should process
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        // disable CSRF protection.  Remember, this must be active if the project works with its own .jsp views.
        http.csrf().disable();
        // set the session creation policy to stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // set up authorization for different request matchers and user roles
        http.authorizeHttpRequests()
                //.requestMatchers("/login").permitAll()
//                .requestMatchers(HttpMethod.GET, "/doctors").authenticated() // list doctors
//                .requestMatchers(HttpMethod.GET, "/doctors/status/*").hasAnyRole("ADMIN", "CONTRIBUTOR") // find doctors by status
//                .requestMatchers(HttpMethod.GET, "/patients").hasAnyRole("ADMIN", "CONTRIBUTOR") // list patients
//                .requestMatchers(HttpMethod.POST, "/doctors").hasRole("ADMIN") // add doctors
//                .requestMatchers(HttpMethod.POST, "/patients").hasAnyRole("CONTRIBUTOR", "ADMIN") // add patients
//                .requestMatchers(HttpMethod.PATCH, "/doctors/*/status").hasAnyRole("CONTRIBUTOR", "ADMIN") // update doctor status
//                .requestMatchers(HttpMethod.PATCH, "/doctors/*/department").hasAnyRole("CONTRIBUTOR", "ADMIN") // update doctor department
//                .requestMatchers(HttpMethod.PUT, "/patients/*").hasAnyRole("CONTRIBUTOR", "ADMIN") // update a patient
                .anyRequest().permitAll(); //

        // add the custom authentication filter to the http security object
        http.addFilter(customAuthenticationFilter);
        // Add the custom authorization filter before the standard authentication filter.
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Build the security filter chain to be returned.
        return http.build();

    }
}
