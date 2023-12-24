package tests;
/**
 * Класс реализует базовую функциональность кейсов
 */

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeSuite;
import steps.ApiSteps;
import config.TestConfigFactory;
import steps.DbSteps;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class BaseTest {
    protected static TestConfigFactory conf = TestConfigFactory.getInstance();
    public ApiSteps apiSteps = new ApiSteps();
    public DbSteps dbSteps = new DbSteps();

    @BeforeSuite
    public void restAssuredSetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    public final RequestSpecification getRequestSpecification() {
        return given()
                .filter(new AllureRestAssured())
                .urlEncodingEnabled(false)
                .auth().preemptive().basic(conf.getWebConfig().getUser(), conf.getWebConfig().getPass())
                .baseUri(conf.getWebConfig().getBaseUrl())
                .contentType(ContentType.JSON)
                .then()
                .request();
    }
}