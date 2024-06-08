import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.steps.OrderSteps;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTests {

        private OrderSteps orderSteps = new OrderSteps();

        @Parameterized.Parameter(0)
        public List<String> color;

        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String metroStation = RandomStringUtils.randomNumeric(10);
        String phone = RandomStringUtils.randomAlphabetic(10);
        int rentTime = Integer.parseInt(RandomStringUtils.randomNumeric(1));
        String deliveryDate = "2024-06-05";
        String comment = RandomStringUtils.randomAlphabetic(10);

        @Parameterized.Parameters
        public static Object[] data() {
            return new Object[]{
                    List.of("BLACK"),
                    List.of("GREY"),
                    List.of("BLACK", "GREY"),
                    List.of()
            };
        }

        @Test
        @DisplayName("Создание заказа")
        public void createOrderSuccesfull() {
            orderSteps
                    .createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                    .statusCode(201)
                    .body("track", notNullValue());
        }
    }
