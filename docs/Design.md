<<<<<<< HEAD
# Design Document

Our overall structure is like this below (including service, DAO and also object classes):

![FancyBank611](FancyBank611.png)

You can also check the image in this folder to get a better view.

## Project Structure

For the project structure, please check the `MVC` document in the same folder.

## Object Model

### Currency

|              | Type   | Desc                                     |
|--------------| ------ | ---------------------------------------- |
| currencyName | String |                                          |
| symbol       | String |                                          |
| exchangeRate | Double | The currency rate comparing to US dollar |

### Money

|                   | Type     | Desc                                     |
|-------------------|----------| ---------------------------------------- |
| amount            | double   |                                          |
| currency          | Currency |                                          |
| convert(Currency) | Money    | Convert the currency into a new currency |
| setUnit(int)      | int      |                                          |
| getUnit()         | int      |                                          |

### Bank

|          | Type    | Desc |
|----------| ------- | ---- |
| bankName | String  |      |
| bankId   | Integer |      |
| isOpen   | Boolean |      |
| Branch   | String  |      |

### Account <\<abstract>>

|                          | Type         | Desc                                                         |
| ------------------------ |--------------| ------------------------------------------------------------ |
| accountNumber            | Integer      |                                                              |
| rountingNumber           | String       |                                                              |
| swiftCode                | String       |                                                              |
| currentBalance           | List\<Money> |                                                              |
| getBalance()             | List\<Money> |                                                              |
| updateBalance()          | Boolean      | Return the status of operation                               |
| close()                  | Boolean      |                                                              |
| open(String)             | Boolean      |                                                              |
| transact(Money, Account) | Boolean      | Make a transaction from the current account to the target account |

### Saving <- Account

|                | Type | Desc |
| -------------- | ---- | ---- |
| Saving(String) |      |      |

### Checking <- Account

|                  | Type | Desc |
| ---------------- | ---- | ---- |
| Checking(String) |      |      |

### Loan <- Account

|              | Type                  | Desc |
| ------------ | --------------------- | ---- |
| interestRate | Integer/Double        |      |
| dueDate      | Date (unix timestamp) |      |
| paymentFreq  | Integer               |      |
| Loan(String) |                       |      |

### Security Account

|                                | Type             | Desc                                                         |
| ------------------------------ | ---------------- | ------------------------------------------------------------ |
| stocks                         | Map<Stock, int\> | key is the Stock, value is the units bought for this stock. So when updating the currenct price for one stock, we can just use the object to calculate the current money of this stock.<br />$Stock.currentPrice*unit$ |
| realized                       | Money            |                                                              |
| unrealized                     | Money            |                                                              |
| buyStockByMoney(Stock, Money)  | Boolean          |                                                              |
| sellStockByMoney(Stock, Money) | Boolean          |                                                              |
| buyStockByUnit(Stock, int)     | Boolean          |                                                              |
| sellStockByUnit(Stock, int)    | Boolean          |                                                              |
| getBoughtStocks()              | List<Stock\>     |                                                              |

### Stock

|              | Type   | Desc                                 |
|--------------| ------ | ------------------------------------ |
| stockId      | int    |                                      |
| stockName    | String |                                      |
| currentPrice | Money  | Current price of this stock per unit |

### Person \<\<abstract>>

|            | Type   | Desc |
|------------|--------| ---- |
| username   | String |      |
| firstName  | String |      |
| lastName   | String |      |
| middleName | String |      |
| email      | String |      |
| contact    | String |      |
| address    | String |      |

### Manager <- Person

|           | Type            | Desc |
| --------- | --------------- | ---- |
| stocks    | List<Stock\>    |      |
| customers | List\<Customer> |      |

### Customer <- Person

|                               | Type           | Desc |
| ----------------------------- | -------------- | ---- |
| hasCollateral                 | Boolean        |      |
| accounts                      | List\<Account> |      |
| createSavingAccount(String)   | Boolean        |      |
| createCheckingAccount(String) | Boolean        |      |
| getTotalDebt()                | Money          |      |
| createSecurityAccount(String) | Boolean        |      |

### Transact \<\<interface>>

|                                                         | Type               | Desc |
| ------------------------------------------------------- | ------------------ | ---- |
| makeTransaction(TransactionType, object, object, Money) | Boolean            |      |
| getTransaction(int)                                     | Transaction        |      |
| getTransactions()                                       | List\<Transaction> |      |

### Transaction \<\<abstract>>

|                    | Type               | Desc |
| ------------------ | ------------------ | ---- |
| id                 | int                |      |
| date               | Date               |      |
| from               | Account            |      |
| to                 | Account            |      |
| amount             | Money              |      |
| transactionType    | TransactionType    |      |
| transactionStatus  | TransacttionStatus |      |
| printTransaction() | Void               |      |

### TransactionType

- REGULAR_TRANSACTION
- BILL_PAY
- SERVICE_FEE
- ...

### TransactionStatus

- SUCCESS
- FAILED
- PENDING

## Database

### User

This table includes both customers and managers

| Attribute   | Type   | Desc |
|-------------| ------ | ---- |
| Username    | Pk     |      |
| First_name  | String |      |
| Middle_name | String |      |
| last_name   | String |      |
| Password    |        |      |
| email       | String |      |
| contact     | String |      |
| address     | String |      |
| is_customer | Bool   |      |

### Currency

| Attribute     | Type       | Desc                               |
| ------------- | ---------- | ---------------------------------- |
| currency_name | pk, string |                                    |
| symbol        | string     |                                    |
| usd_rate      | double     | the currency rate comparing to USD |

### Bank(ATM)

| Attribute | Type    | Desc |
| --------- | ------- | ---- |
| bank_id   | pk, int |      |
| bank_name | string  |      |
| branch    | string  |      |
| is_open   | bool    |      |

### Account

The balance of the account can be get from the Money table, as one account can hold money of different currency

| Attribute     | Type    | Desc                          |
| ------------- | ------- | ----------------------------- |
| account_no    | pk, int | account number                |
| user_id       | fk, int |                               |
| routing_no    | string  | routing number                |
| swift_code    | string  |                               |
| account_type  | string  | Saving/Checking/Security/Loan |
| Interest_rate | Double  |                               |

### Money

| Attribute  | Type   | Desc |
| ---------- | ------ | ---- |
| account_no | pk fk  |      |
| currency   | pk fk  |      |
| Amount     | double |      |

### Loan

| Attribute  | Type   | Desc                               |
| ---------- | ------ | ---------------------------------- |
| account_no | pk, fk | One loan account bind with one row |
| start_date | date   |                                    |
| End_date   | date   |                                    |

### Stock

| Attribute     | Type    | Desc                     |
| ------------- | ------- | ------------------------ |
| stock_id      | pk, int |                          |
| stock_name    | string  |                          |
| current_price | Int     | Price of stock unit(USD) |

### BoughtStock

| Attribute  | Type   | Desc |
| ---------- | ------ | ---- |
| Stock_id   | pk, fk |      |
| account_no | pk, fk |      |
| Stock_unit | Int    |      |

##  User Interface 
UI Design:
1) abstract AbstractPanel
All main pages inherit a common abstract class called AbstractPanel. AbstractPanel extends a JPanel, and defines an abstract method called getBasePanel().
This is used in Frontend.java so that we can switch between panels effortlessly without having to create new JFrames.


2) ViewFactory:
A Factory class to return different views. All functions return an AbstractPanel instance.

3) Frontend.java
A singleton class that keeps track of the state. The state here comprises of the user object and type of user (manager or customer).
This class always returns the same instance of the frontend and is used to traverse within the app.
back() is used to pop the panel on the top to go back to the previous screen while next() is used to traverse forward.

4) Helpers.java
Utility functions helping in working with the functions in Controller.java, to abstract out the functions away from ui classes.

5) .form files. Form files are bound to it's classes (classes with the same name) and are used to design the ui components
