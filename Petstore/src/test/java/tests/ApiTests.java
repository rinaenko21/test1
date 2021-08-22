package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.ConfigReader;

import java.io.File;
import java.util.logging.Logger;

public class ApiTests {

    private static final Logger LOGGER = Logger.getLogger (String.valueOf (ApiTests.class));
    public static String baseURI =  ConfigReader.getProperty ("baseURI");

    @Test
    public void addPetTest() {

        String body = "{\n" +
                "  \"id\": 3,\n" +
                "  \"category\": {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"lab\"\n" +
                "  },\n" +
                "  \"name\": \"Lucky\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"name\": \"Lucky\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        LOGGER.info ("Sending POST request to create new pet");
        Response response = RestAssured.given()
                .baseUri(baseURI)
                .header ("api-key", "special-key")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body (body)
                .when ()
                .request ("POST", "/pet");

        LOGGER.info ("Verifying status code");
        Assert.assertEquals (200, response.getStatusCode ());
        LOGGER.info ("Verifying response payload contains name Lucky");
        Assert.assertTrue (response.asPrettyString ().trim ().contains ("Lucky"));
    }

    @Test
    public void addPetImageTest() {
        File image = new File("petpic.jpeg");
        LOGGER.info ("Sending POST request to add image");
        Response response = RestAssured.given()
                .baseUri(baseURI)
                .header ("api-key", "special-key")
                .contentType("multipart/form-data")
                .accept("application/json")
                .multiPart(image)
                .when ()
                .request ("POST", "/pet/3/uploadImage");

        LOGGER.info ("Verifying status code");
        Assert.assertEquals (200, response.getStatusCode ());
        LOGGER.info ("Verifying response payload contains name uploaded");
        Assert.assertTrue (response.asPrettyString ().trim ().contains ("uploaded"));
    }

    @Test
    public void findPetById() {
        LOGGER.info ("Sending GET request to find pet");
        Response response = RestAssured.given()
                .baseUri(baseURI)
                .header ("api-key", "special-key")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when ()
                .request ("GET", "/pet/3");
        LOGGER.info ("Verifying status code");
        Assert.assertEquals (200, response.getStatusCode ());
        LOGGER.info ("Verifying response payload contains name Lucky");
        Assert.assertTrue (response.asPrettyString ().trim ().contains ("Lucky"));
    }

    @Test
    public void findSimilarPetsByStatus() {
        LOGGER.info ("Sending GET request to find pets with status available");
        Response response = RestAssured.given()
                .baseUri(baseURI)
                .header ("api-key", "special-key")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when ()
                .request ("GET", "/pet/findByStatus?status=available");

        LOGGER.info ("Verifying status code");
        Assert.assertEquals (200, response.getStatusCode ());
        LOGGER.info ("Verifying response payload contains status available");
        Assert.assertTrue (response.asPrettyString ().trim ().contains ("available"));

    }

}
