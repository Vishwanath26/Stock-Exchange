package navi;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class StockExchange {

    private ArrayList<Order> readInputFile(String filePath) {
        File file = new File(filePath);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st;
        ArrayList<Order> orders = new ArrayList<>();

        while (true) {
            try {
                if ((st = br.readLine()) != null) {
                    String[] stockData = st.split(" ");
                    Order newOrder = new Order();

                    newOrder.setOrderId(stockData[0]);
                    newOrder.setTime(LocalTime.parse(stockData[1], DateTimeFormatter.ofPattern("HH:mm")));
                    newOrder.setStock(stockData[2]);
                    newOrder.setOrderType(stockData[3]);
                    newOrder.setPrice(Float.parseFloat(stockData[4]));
                    newOrder.setQuantity(Integer.parseInt(stockData[5]));

                    orders.add(newOrder);
                } else {
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return orders;
    }

    private ArrayList<Order> handleSellRecord(int currentRecordIndex, ArrayList<Order> orders) {
        Order currentOrder = orders.get(currentRecordIndex);

        for (int j = currentRecordIndex + 1; j < orders.size() && currentOrder.getQuantity() > 0; j++) {

            if (orders.get(j).getOrderType() == OrderType.BUY && orders.get(j).getStock().equals(currentOrder.getStock())) {

                if (orders.get(j).getPrice() >= currentOrder.getPrice()) {

                    int soldQuantity = (currentOrder.getQuantity() - orders.get(j).getQuantity()) > 0 ? (currentOrder.getQuantity() - orders.get(j).getQuantity()) : currentOrder.getQuantity();

                    System.out.println(orders.get(j).getOrderId() + " " + currentOrder.getPrice() + " " + soldQuantity + " " + currentOrder.getOrderId());

                    currentOrder.setQuantity(currentOrder.getQuantity() > orders.get(j).getQuantity() ? currentOrder.getQuantity() - orders.get(j).getQuantity() : 0);
                    orders.get(j).setQuantity(currentOrder.getQuantity() == 0 ? orders.get(j).getQuantity() - soldQuantity : 0);
                }
            }
        }
        return orders;
    }

    private ArrayList<Order> handleBuyRecord(int currentRecordIndex, ArrayList<Order> orders) {
        Order currentOrder = orders.get(currentRecordIndex);

        for (int j = currentRecordIndex + 1; j < orders.size() && currentOrder.getQuantity() > 0; j++) {

            if (orders.get(j).getOrderType() == OrderType.SELL && orders.get(j).getStock().equals(currentOrder.getStock())) {

                if (orders.get(j).getPrice() <= currentOrder.getPrice()) {

                    int soldQuantity = (currentOrder.getQuantity() - orders.get(j).getQuantity()) > 0 ? (currentOrder.getQuantity() - orders.get(j).getQuantity()) : currentOrder.getQuantity();

                    System.out.println(currentOrder.getOrderId() + " " + orders.get(j).getPrice() + " " + soldQuantity + " " + orders.get(j).getOrderId());

                    currentOrder.setQuantity(currentOrder.getQuantity() > orders.get(j).getQuantity() ? currentOrder.getQuantity() - orders.get(j).getQuantity() : 0);
                    orders.get(j).setQuantity(currentOrder.getQuantity() == 0 ? orders.get(j).getQuantity() - soldQuantity : 0);

                }
            }
        }
        return orders;
    }

    private void calculateStockExchange() {

        ArrayList<Order> stockExchangeOrders = readInputFile("/Users/vishwanath/repos/StockExchange/src/com/navi/orders.txt");

        /**
         * Algo - for each stockExchangeOrder try to complete it using all possible options(other stockOrders) which are recorded after it.
         * Time Complexity - O(n*n) as we are using two loops to iterate th stockExchangeOrders list
         */

        for (int i = 0; i < stockExchangeOrders.size(); i++) {

            Order currentOrder = stockExchangeOrders.get(i);

            if (currentOrder.getOrderType() == OrderType.SELL)
                handleSellRecord(i, stockExchangeOrders);

            else
                handleBuyRecord(i, stockExchangeOrders);
        }

    }

    public static void main(String[] args) {
        StockExchange stockExchange = new StockExchange();
        stockExchange.calculateStockExchange();
    }
}
