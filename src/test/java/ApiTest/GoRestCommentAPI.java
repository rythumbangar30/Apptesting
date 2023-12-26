package ApiTest;

import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;


public class GoRestCommentAPI {

    @Test
    void getDataOrGetSingleUser(){
        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
        baseURI="https://gorest.co.in";
        Response response=given()
                .request(Method.GET,"/public/v2/comments/73627");
        System.out.println(response.statusCode());
        System.out.println(response.asString());
    }

    //@Test
    void createUser(){
        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
        baseURI="https://gorest.co.in";
        String value="{    \"post_id\":\"90685\",\n" +
                "    \"name\":\"Sher\",\n" +
                "    \"email\":\"SherKhan@gmail.com\",\n" +
                "    \"body\":\"Here the status code is 200 OK; this means the server approved the request, and we received a positive response. The error User already exists means the data already exist in the database.\"}";
        Response response=given()
                .header("Authorization","Bearer "+token)
                .contentType(ContentType.JSON)
                .body(value)
                .request(Method.POST,"/public/v2/comments");
        assertEquals(201,response.statusCode());
        JSONObject responseData=new JSONObject("response");
        String name=responseData.getString("name");

    }
    @Test
    void UpdateUser(){
        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
        baseURI="https://gorest.co.in";
        String data="{ \"post\":\"74075\",\n" +
                "    \"post_id\":\"91166\",\n" +
                "    \"name\":\"Sher\",\n" +
                "    \"email\":\"SherKhan@gmail.com\",\n" +
                "    \"body\":\"Here the status code is 200 OK; this means the server approved the request, and we received a positive response. The error User already exists means the data already exist in the database.\"}";

    }
}
