package DynamicModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import DynamicModel.ApplicationProperties;
import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserPoints extends BaseTest{





    public static Response getUser(String UserUrl){



        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.GET, baseURI+UserUrl);
        return response;

    }

    @Test
    public void testGetUser(){
        String url="public/v2/users/5853415";
        Response response=getUser(url);
        response.then().log().all();
        System.out.println(response.asString());
        assertThat(200,equalTo(response.statusCode()));
    }

    public static Response createUser(String UserUrl,String payload){
//        String url= ApplicationProperties.INSTANCE.getBaseURI();
//        String token=ApplicationProperties.INSTANCE.getToken();
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,baseURI+UserUrl);
        return response;
    }


    public int createUser(){
        String url="public/v2/users";
        Map<String,Object> bodyParams=new HashMap<>();
        bodyParams.put("name","Sarkar");
        bodyParams.put("email","sarkar.75@gmail.com");
        bodyParams.put("gender","male");
        bodyParams.put("status","active");
        String payload=new Gson().toJson(bodyParams);
        Response response=createUser(url,payload);
        System.out.println("User Created \n"+response.asString());
        assertThat(201,equalTo(response.statusCode()));
        JSONObject jsonObject=new JSONObject(response.asString());
        int userID=jsonObject.getInt("id");
        return userID;
    }

    public static Response updateUser(String UserUrl,String payload){
//        String url=ApplicationProperties.INSTANCE.getBaseURI();
//        String token=ApplicationProperties.INSTANCE.getToken();
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,baseURI+UserUrl);
        return response;
    }


    public int updateUser() {
        int updateID=createUser();
        String url="public/v2/users/"+updateID;
        Map<String,Object> userUpdatedData=new HashMap<>();
        userUpdatedData.put("name","shri");
        userUpdatedData.put("email","shri22@gmail.com");
        userUpdatedData.put("gender","male");
        userUpdatedData.put("status","inactive");
        String updatepayload=new Gson().toJson(userUpdatedData);
        Response response=updateUser(url,updatepayload);
        System.out.println("Updated User Data\n"+response.asString());
        assertThat(200,equalTo(response.statusCode()));
        return updateID;
    }

    public static Response deleteUser(String UserUrl){
//        String url=ApplicationProperties.INSTANCE.getBaseURI();
//        String token=ApplicationProperties.INSTANCE.getToken();
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.DELETE,baseURI+UserUrl);
        return response;
    }

    @Test
    public void DeleteUser(){
        int UserId=updateUser();
        String url="public/v2/users/"+UserId;
        Response response=deleteUser(url);
        System.out.println("Deleted UserID :"+UserId);
        assertThat(204,equalTo(response.statusCode()));

    }

}
