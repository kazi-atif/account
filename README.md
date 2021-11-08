This is a Sample project which allows a user to list their accounts and display the transactions for each account

Checkout the using the link - git clone https://github.com/kazi-atif/account.git

Import the maven project in the ide and run AccountApplication.java to run the application

http://localhost:8080/swagger-ui.html - Lists the available endpoints.

The endpoint GET /api/customers/{customerId}/accounts - Allows all accounts for a partcular customerId to be listed.
This endpoint  meets the 1st part of the Sample Project

The endpoint GET /api/transactions/{accountId} - Allows transactions for an accountId to be listed.
This endpoint  meets the 1st part of the Sample Project.

The other endpoints to createAccount, withdraw, deposit were not explictly mentioned in the requirement
but were needed to operate on account and carry on transactions so as to provide meaningful results for the GET calls.

For the ease of verifying the endpoints, a simulation is run executed in PostConstruct 

http://localhost:8080/api/customers/12345/accounts - Lists all accounts for customer with id - 12345

http://localhost:8080/api/transactions/8888 - List all transactions for account with id - 8888



