package tests;
/**
 * Класс реализует базовую функциональность кейсов
 */

import steps.ApiSteps;
import config.TestConfigFactory;
import steps.DbSteps;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected static TestConfigFactory conf = TestConfigFactory.getInstance();
    ApiSteps apiSteps = new ApiSteps();
    DbSteps dbSteps = new DbSteps();

    protected final RequestSpecification requestSpecification = given()
            .auth().preemptive().basic(conf.getWebConfig().getUser(), conf.getWebConfig().getPass())
            .filter(new AllureRestAssured())
            .log().all()
            .baseUri(conf.getWebConfig().getBaseUrl())
            .contentType(ContentType.JSON)
            .then().log().all()
            .expect()
            .request();
}