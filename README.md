This is a simple Bank Application developed in Java Spring Boot.
#TECHNOLOGIES
1.Java
2.Spring Security
3.Spring Security
4.MySQL
6.JWT Token
7.JPA
8.Lombok
9.Spring Web

##FEATURES
This application implements JWT Token for authentication
1.A user first registers to obtain JWT Token that will be used in future requests to avoid the hussle of submitting their email and password all the time.
2.After registering, the user will then create an account using their email and specify account type eg.SAVING which will have an accountID.
3.The user can then perform transactions like withdraw and deposit.
4.When the application starts, an admin account is created automatically.
The admin account can perform the following operations:
  i)Deactivate an account making the user unable to perform any transactions with that account.
  ii)Activate an account using the accountID.
  iii)Delete an account given the accountID.
  iv)Retrieve all accounts belonging to user given the userEmail because it is unique.

5.A user can also logout which invalidates all the valid JWT Token making them to reauthenticate with their email and password.
6.The application has roles which are the ADMIN role and the USER role.

##ENTITIES
1.User table : Contains user information
   Attributes: firstName, lastName, email, password, accounts(Holds all accounts belonging to that particular user)

2.Logout table : Contains information about the JWT Token
   Attributes: token, revoked(boolean), expired(boolean), user(references to which user the token belongs to)

3.AccountTable : Contains information about a specific account
    Attributes : accountID, userID(references to which user the account belongs to), transactionTable(holds all transactions that took place with that account),
    balance, openDate, status(ACTIVE, INACTIVE), lastModified

4.TransactionTable : Contains all transactions for the accounts
    Attributes: transactionID, accountID(references to which account was the transaction for), transactionDate, amount(amount transacted), transactionType(WITHDRAW, DEPOSIT)

##Here is a sample screenshot on the transactions performed by a user![Screenshot from 2024-02-25 05-21-57](https://github.com/hill-200/bank/assets/117020839/26b8277e-00ce-4be9-ae20-b5f27baa8dc8)
![Screenshot from 2024-02-25 05-21-26](https://github.com/hill-200/bank/assets/117020839/27f4e03b-daee-462c-8aac-907af106709d)
![Screenshot from 2024-02-25 05-21-11](https://github.com/hill-200/bank/assets/117020839/36d30d41-2aea-4279-b3b8-72875af72cc9)

    
   
