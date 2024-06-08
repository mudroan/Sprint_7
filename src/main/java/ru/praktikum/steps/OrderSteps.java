package ru.praktikum.steps;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.dto.GetOrdersRequest;
import ru.praktikum.dto.OrderCreateRequest;
import java.util.List;
import static io.restassured.RestAssured.given;
import static ru.praktikum.config.RestConfig.HOST;
import static ru.praktikum.model.constants.ApiURI.ORDER;

public class OrderSteps {

    @Step("Создание заказа")
    public ValidatableResponse createOrder (String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color ) {

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest ();

        orderCreateRequest.setFirstName (firstName);
        orderCreateRequest.setLastName (lastName);
        orderCreateRequest.setAddress (address);
        orderCreateRequest.setMetroStation (metroStation);
        orderCreateRequest.setPhone (phone);
        orderCreateRequest.setRentTime (rentTime);
        orderCreateRequest.setDeliveryDate (deliveryDate);
        orderCreateRequest.setComment (comment);
        orderCreateRequest.setColor (color);

        return given()
                .contentType (ContentType.JSON)
                .baseUri (HOST)
                .body(orderCreateRequest)
                .when()
                .post (ORDER)
                .then();
    }

    @Step ("Получение списка заказов")
    public ValidatableResponse getListOrders (int courierId, int limit, String nearestStation, int page) {

        GetOrdersRequest getOrdersRequest = new GetOrdersRequest ();

        getOrdersRequest.setCourierId (courierId);
        getOrdersRequest.setLimit (limit);
        getOrdersRequest.setNearestStation (nearestStation);
        getOrdersRequest.setPage (page);

        return given()
                .contentType (ContentType.JSON)
                .baseUri (HOST)
                .body(getOrdersRequest)
                .when()
                .get (ORDER)
                .then();
    }
}
