

This is the final project from Ironhack's Java Development Bootcamp.  It's an API that connects to a Local database and retrieves information about a Banking system.

The main entity models are _User_ and _Account_, that are implemented as _abstract_ entities.

The son entities of _User_ are _AccountHolder_, _Administrator_ and _ThirdParty_, each one with them own caracteristics.

On the side of _Account_, there are the son entities that extend the properties of the superclass:  _Checking_, _StudentChecking_, _Savings_ and _CreditCard_.

Particularly, some entities have a few requirements:
- _Saving_ accounts have an interest rate thad needs to be added when the account is accessed.
- _Checking_ and _StudentChecking_ accounts are created depending on the primary owner's age (if the age is less than 24 years old).
- _CreditCards_ have an interest added to the balance monthly.

Each one of the accounts have a penalty fee that is deducted from the balance automatically if it drops below de minimum balance.

- _Admins_ are able to access the balance for any account and to modify it.
- _AccountHolders_ are able to access only their own account balance and to transfer money from their accounts to any other account, regardless of owner).
- _ThirdParty_ can receive and send money to other accounts.

<br>

__Database Diagram:__

<img src="/src/main/resources/static/db_diagram.JPG" alt="database diagram" title="Database diagram" style="display:inline-block; margin: 0 auto;">

It can be seen here that the two entities _User_ and _Role_ are independent of _BankingUser,_ and they shouldn't be.  I will need more time to think how implement them correctly, because the main User class (_BankingUser_) it's an _abstract_ entity, and it brought me trouble.  If the deadline wasn't so close I'm sure I would have sorted it out.
