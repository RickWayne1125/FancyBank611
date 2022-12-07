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



## Database

### Stock

| Attribute     | Type    | Desc                |
| ------------- | ------- | ------------------- |
| stock_id      | pk, int |                     |
| stock_name    | string  |                     |
| current_price | Int     | Price of stock unit |

### BoughtStock

| Attribute    | Type | Desc |
| ------------ | ---- | ---- |
| Stock_id     |      |      |
| Customer_id  |      |      |
| Stock_unit   |      |      |
| Bought_price |      |      |

### 

## User Panel Prototype