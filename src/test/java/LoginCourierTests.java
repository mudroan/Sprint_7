import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.steps.CourierSteps;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.model.constants.Missing.MISSING_LOGIN;
import static ru.praktikum.model.constants.Missing.MISSING_PASSWORD;

public class LoginCourierTests {

    private CourierSteps courierSteps  = new CourierSteps ();

    String login = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomAlphabetic(10);

    String unknownLogin = RandomStringUtils.randomAlphabetic(10);
    String unknownPassword =  RandomStringUtils.randomAlphabetic(10);

    @Test
    @DisplayName("Логин курьера в системе")
    public void loginCourierSuccessful() {
        courierSteps
                .createCourier (login, password, "");

        courierSteps
                .loginCourier(login, password)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Невозможно найти курьера без логина")
    public void loginCourierWithMissingLoginFailed() {
        courierSteps
                .createCourier (login, password, "");

        courierSteps
                .loginCourier(MISSING_LOGIN, password)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Невозможно найти курьера без пароля")
    @Issue ("При вызове сервиса без указания пароля возвращается ошибка HTTP/1.1 504 Gateway time out. По требованиям, результат должен быть как в кейсе \"Без указания логина - 400 ошибка\"")
    public void loginCourierWithMissingPasswordFailed() {
        courierSteps
                .createCourier (login, password, "");

        courierSteps
                .loginCourier(login, MISSING_PASSWORD)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Запрос с несуществующей парой логин-пароль")
    public void loginCourierWithUnknownPasswordFailed() {
        courierSteps
                .loginCourier(unknownLogin, unknownPassword)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        RestAssured.filters (new RequestLoggingFilter (), new ResponseLoggingFilter ());
        Integer id = courierSteps.loginCourier (login, password)
                .extract().body().path("id");
        if(id != null) {
            courierSteps.delete(id);
        }
    }
}
