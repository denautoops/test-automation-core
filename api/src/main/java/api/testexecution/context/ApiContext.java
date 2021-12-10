package api.testexecution.context;

import core.context.ApplicationSessionContext;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Method;

import static io.restassured.RestAssured.given;

/**
 * API Context
 * <p>
 *
 * @author Denis.Martynov
 * Created on 5.04.21
 */
public class ApiContext implements ApplicationSessionContext {

    private RequestSpecification requestSpecification;

    public ApiContext() {
    }

    public RequestSpecification getRequestSpecification() {
        return given().spec(requestSpecification);
    }

    @Override
    public void release(Method method, boolean isTestFailed) {
        //todo add functionality
    }

    @Override
    public void init(Method method) {
        requestSpecification = given().filter(new AllureRestAssured())
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON)
                .log()
                .all();
    }
}
