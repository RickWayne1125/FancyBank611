# FancyBank

|         | 12/1 - Decide Design | 12/4 - Basic design (frontend, database -> documents) | 12/11 - Most classes finished |
| ------- | -------------------- | ----------------------------------------------------- | ----------------------------- |
| Rick    |                      |                                                       |                               |
| Prithvi |                      |                                                       |                               |
| Mirack  |                      |                                                       |                               |

## Overall Design

### Currency

|              | Type   | Desc                                     |
| ------------ | ------ | ---------------------------------------- |
| currencyName | String |                                          |
| symbol       | String |                                          |
| rateToUSD    | Double | The currency rate comparing to US dollar |

### Money

|                   | Type     | Desc                                     |
| ----------------- | -------- | ---------------------------------------- |
| unit              | int      |                                          |
| currency          | Currency |                                          |
| convert(Currency) | Money    | Convert the currency into a new currency |
| setUnit(int)      | int      |                                          |
| getUnit()         | int      |                                          |

### Bank

|        | Type    | Desc |
| ------ | ------- | ---- |
| Name   | String  |      |
| ID     | Integer |      |
| isOpen | Boolean |      |
| Branch | String  |      |

### Account <\<abstract>>

|                          | Type    | Desc                                                         |
| ------------------------ | ------- | ------------------------------------------------------------ |
| accountNumber            | Integer |                                                              |
| rountingNumber           | String  |                                                              |
| swiftCode                | String  |                                                              |
| currentBalance           | Money   |                                                              |
| getBalance()             | Money   |                                                              |
| updateBalance()          | Boolean | Return the status of operation                               |
| close()                  | Boolean |                                                              |
| open(String)             | Boolean |                                                              |
| transact(Money, Account) | Boolean | Make a transaction from the current account to the target account |

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
| ------------ | ------ | ------------------------------------ |
| id           | int    |                                      |
| name         | String |                                      |
| currentPrice | Money  | Current price of this stock per unit |

### Person \<\<abstract>>

|            | Type   | Desc |
| ---------- | ------ | ---- |
| id         | int    |      |
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
| ----------- | ------ | ---- |
| User_id     | Pk     |      |
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

##  Panel Prototype