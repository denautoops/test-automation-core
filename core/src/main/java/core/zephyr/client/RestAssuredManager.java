package core.zephyr.client;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredManager {

    private static final String AUTH_HEADER_VALUE_PATTERN = "Basic %s";

    private RequestSpecification requestSpecification;

    public RestAssuredManager(final String encodedCredentials) {
        initRequestSpecification(encodedCredentials);
    }

    private void initRequestSpecification(final String encodedCredentials) {
        Header header = new Header("Authorization", String.format(AUTH_HEADER_VALUE_PATTERN, encodedCredentials));

        requestSpecification = given().log()
                .all()
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON)
                .header(header);
    }

    public RequestSpecification getRequestSpecification() {
        return given().spec(requestSpecification);
    }
}
