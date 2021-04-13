package navi;


import java.time.LocalTime;

public class Order {

    private String orderId;
    private LocalTime time;
    private String stock;
    private OrderType orderType;
    private Float price;
    private int quantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        if (orderType.equalsIgnoreCase(String.valueOf(OrderType.BUY)))
            this.orderType = OrderType.BUY;
        else
            this.orderType = OrderType.SELL;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
