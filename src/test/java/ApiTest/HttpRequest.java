package ApiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

import static org.hamcrest.Matchers.*;
public class HttpRequest {
    //@Test
    void getUsers(){

        given()

                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page",equalTo(2))
                .log().all();

     }

     @Test
     void createUser() {
         HashMap data = new HashMap();
         data.put("name", "pavan");
         data.put("job", "trainer");
         String body = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
         System.out.println(data);
         System.out.println(body);


         baseURI = "https://reqres.in";
         Response response = given()
                 .contentType(ContentType.JSON)
                 .body(body)
                 .request(Method.POST, "/api/users");
         System.out.println(response.statusCode());
         System.out.println(response.asString());

     }
}
