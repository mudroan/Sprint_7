package ru.praktikum.steps;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.dto.CourierCreateRequest;
import ru.praktikum.dto.CourierLoginRequest;
import static io.restassured.RestAssured.given;
import static ru.praktikum.config.RestConfig.HOST;
import static ru.praktikum.model.constants.ApiURI.*;

public class CourierSteps {

    @Step("Создание курьера")
    public ValidatableResponse createCourier (String login, String password, String firstName) {

        CourierCreateRequest courierCreateRequest = new CourierCreateRequest ();
        courierCreateRequest.setLogin (login);
        courierCreateRequest.setPassword (password);
        courierCreateRequest.setFirstName (firstName);

        return given()
                .contentType (ContentType.JSON)
                .baseUri (HOST)
                .body(courierCreateRequest)
                .when()
                .post (CREATE_COURIER)
                .then();
    }

   @Step("Логин курьера в системе")
    public ValidatableResponse loginCourier (String login, String password) {

        CourierCreateRequest courierCreateRequest = new CourierCreateRequest ();
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest ();

        courierCreateRequest.setLogin (login);
        courierCreateRequest.setPassword (password);
        courierLoginRequest.setLogin (login);
        courierCreateRequest.setPassword (password);

        return given()
                .contentType (ContentType.JSON)
                .baseUri (HOST)
                .body(courierCreateRequest)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }
    @Step("Удаление созданного курьера")
    public ValidatableResponse delete (int id) {
        return given()
                .contentType (ContentType.JSON)
                .baseUri (HOST)
                .pathParams ("id", id)
                .when()
                .delete (DELETE)
                .then();
    }
}