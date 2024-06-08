import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.praktikum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOrdersTest {

    private OrderSteps orderSteps  = new OrderSteps ();

    int courierId = Integer.parseInt(RandomStringUtils.randomNumeric(1));
    String nearestStation = RandomStringUtils.randomAlphabetic(10);
    int limit = Integer.parseInt(RandomStringUtils.randomNumeric(1));
    int page = Integer.parseInt(RandomStringUtils.randomNumeric(1));

    @Test
    @DisplayName("Получение списка заказов")
    public void getListOrder() {
        orderSteps
                .getListOrders (courierId, limit, nearestStation, page)
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
