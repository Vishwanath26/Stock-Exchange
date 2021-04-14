import navi.Order;
import navi.StockExchange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.IOException;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class StockExchangeTests {

    @InjectMocks
    private StockExchange stockExchange;

    @Test(expected = IOException.class)
    public void readFileWithWrongPathTest() throws Exception {
        ArrayList<Order> orders = stockExchange.readInputFile("/test.txt");
    }

    @Test
    public void readFileWithCorrectPathTest() throws Exception {
        ArrayList<Order> orders = stockExchange.readInputFile("/Users/vishwanath/repos/StockExchange/src/com/navi/orders.txt");
        Assert.assertEquals(6,orders.size());
    }

    @Test
    public void handleSellRecordWithEmptyOrdersTest() throws IndexOutOfBoundsException{
        ArrayList<Order> orders =  stockExchange.handleSellRecord(2, new ArrayList<Order>());
    }

    @Test
    public void handleBuyRecordWithEmptyOrdersTest() throws IndexOutOfBoundsException{
        ArrayList<Order> orders =  stockExchange.handleBuyRecord(2, new ArrayList<Order>());
    }

}
