package navi;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Stream;

public class StockExchange {

    public ArrayList<Order> readInputFile(String filePath) throws IOException {

        Stream<String> lines = Files.lines(Paths.get(filePath));

        ArrayList<Order> orders = new ArrayList<>();

        lines.forEach(s -> {
            String[] stockData = s.split(" ");
            Order newOrder = new Order();

            newOrder.setOrderId(stockData[0]);
            newOrder.setTime(LocalTime.parse(stockData[1], DateTimeFormatter.ofPattern("HH:mm")));
            newOrder.setStock(stockData[2]);
            newOrder.setOrderType(stockData[3]);
            newOrder.setPrice(Float.parseFloat(stockData[4]));
            newOrder.setQuantity(Integer.parseInt(stockData[5]));

            orders.add(newOrder);
        });
        lines.close();

        return orders;
    }

    public ArrayList<Order> handleSellRecord(int currentRecordIndex, ArrayList<Order> orders) {

        Order currentOrder;
        try {
            currentOrder = orders.get(currentRecordIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound exception");
            return new ArrayList<Order>();
        }

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

    public ArrayList<Order> handleBuyRecord(int currentRecordIndex, ArrayList<Order> orders) {

        Order currentOrder;
        try {
            currentOrder = orders.get(currentRecordIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound exception");
            return new ArrayList<Order>();
        }

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

    public void calculateStockExchange() {

        ArrayList<Order> stockExchangeOrders;

        try {
            stockExchangeOrders = readInputFile("/Users/vishwanath/repos/StockExchange/src/com/navi/orders.txt");
        } catch (IOException e) {
            System.out.println("Exception in reading file");
            e.printStackTrace();
            return;
        }

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
