import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.steps.CourierSteps;
import static org.hamcrest.CoreMatchers.is;
import static ru.praktikum.model.constants.Missing.MISSING_LOGIN;

public class CreateCourierTests {

    private CourierSteps courierSteps  = new CourierSteps ();

     String login = RandomStringUtils.randomAlphabetic(10);
     String password = RandomStringUtils.randomAlphabetic(10);
     String firstName = RandomStringUtils.randomAlphabetic(10);

    @Test
    @DisplayName ("Создание курьера")
    public void createSuccessfulCourierTest() {
        courierSteps
                .createCourier (login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName ("Невозможно создать курьера без логина")
    public void createCourierWithoutLogin() {
        courierSteps
                .createCourier (MISSING_LOGIN, password, firstName)
                .statusCode (400)
                .body ("message", is ("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName ("Невозможно создать курьера с одинаковым логином")
    public void createSameCourier() {
        courierSteps
                .createCourier(login, password, firstName);

        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown() {
       Integer id = courierSteps.loginCourier (login, password)
                .extract().body().path("id");
       if(id != null) {
           courierSteps.delete(id);
       }
    }
}