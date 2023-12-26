package ApiTest;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class GoRestCRUDALL {
    //@Test
    void getUsers(){

        baseURI="https://gorest.co.in";
    Response response=given()
            .request(Method.GET,"/public/v2/users/1830554");
        System.out.println(response.statusCode());
        System.out.println(response.asString());
    }

    //@Test
    void createUser(){
        baseURI="https://gorest.co.in";
        String simp="{\"name\":\"Parshu\",\n" +
                "    \"email\":\"Parshu86@gmail.com\",\n" +
                "    \"gender\":\"Male\",\n" +
                "    \"status\":\"Active\"}";
        System.out.println(simp);
        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
        Response response=given()
                .header("Authorization","Bearer "+ token)
                .contentType(ContentType.JSON)
                .body(simp)
                .request(Method.POST,"/public/v2/users");
        System.out.println(response.statusCode());
        System.out.println(response.asString());

    }

   // @Test
    void getUser(){

        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
        baseURI="https://gorest.co.in";
        Response response=given()
                .header("Authorization","Bearer "+token)
                .request(Method.GET,"/public/v2/users/5848422");
        System.out.println(response.statusCode());
        System.out.println(response.asString());
    }
   // @Test
    void putUser()throws JSONException {

        baseURI="https://gorest.co.in";
        String simp="{\"name\":\"andy\",\n" +
                "    \"email\":\"Andy.gan@gmail.com\",\n" +
                "    \"gender\":\"Female\",\n" +
                "    \"status\":\"inactive\"}";
        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
        Response response=given()
                .header("Authorization","Bearer "+token)
                .contentType(ContentType.JSON)
                .body(simp)
                .request(Method.PUT,"/public/v2/users/5853124");
        assertEquals(200,response.statusCode());
        System.out.println(response.asString());
        JSONObject object=new JSONObject(response.asString());
        String status=object.getString("status");


        Response getResponse=given()
                .header("Authorization","Bearer "+token)
                .request(Method.GET,"/public/v2/users/5853124");
        JSONObject getObject=new JSONObject(getResponse.asString());
        String getStatus=getObject.getString("status");
        assertThat("inactive",equalTo(getStatus));
    }
    @Test
    void patchUser(){
        baseURI="https://gorest.co.in";
     String simp="{\"gender\":\"male\",\n" +
             "    \"status\":\"active\"}";
     String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
     Response response=given()
             .header("Authorization","Bearer "+token)
             .contentType(ContentType.JSON)
             .body(simp)
             .request(Method.PATCH,"/public/v2/users/5853124");
     JSONObject putObject=new JSONObject(response.asString());
     String emptyCheck=putObject.getString("gender");
     assertThat(emptyCheck,equalTo("male"));
        System.out.println(response.statusCode());
        System.out.println(response.asString());
    }
   // @Test
    void deleteUser(){
        baseURI="https://gorest.co.in";
        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
        Response response=given()
                .header("Authorization","Bearer "+token)
                .request(Method.DELETE,"/public/v2/users/5848422");
        System.out.println(response.statusCode());
        System.out.println(response.asString());
    }
}
