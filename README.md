# Stock-Exchange

An order matching system for a stock exchange implememted in Java.


## Input-Output

Input is read from a text file which has follwoing parameters - 
1. Order ID.
2. Time.
3. Stock.
4. Buy/Sell.
5. Price.
6. Quantity.
### Example - #1 09:45 BAC sell 240.12 100

Output is printed to console in <buy-order-id> <sell-price> <qty> <sell-order-id> format
### Example - #3 237.45 90 #2


## Algorithm - 
For each stockExchangeOrder try to complete it using all possible options(other stockOrders) which are recorded after it.
Time Complexity - O(n*n) as we are using two loops to iterate th stockExchangeOrders list

## Unit-Tests

Some unit tests are wriiten in a seperate test file using Mockito and Junit.

