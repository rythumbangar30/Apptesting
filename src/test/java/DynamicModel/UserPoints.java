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
//    public static Response getUser(String UserUrl){
//        Response response=given()
//                .header("Authorization","Bearer "+baseToken)
//                .request(Method.GET, UserUrl);
//        return response;
//    }

//    public static Response createUser(String UserUrl,String payload){
//        Response response=given()
//                .header("Authorization","Bearer "+baseToken)
//                .contentType(ContentType.JSON)
//                .body(payload)
//                .request(Method.POST,UserUrl);
//        return response;
//    }

//    public static Response updateUser(String UserUrl,String payload){
//        Response response=given()
//                .header("Authorization","Bearer "+baseToken)
//                .contentType(ContentType.JSON)
//                .body(payload)
//                .request(Method.PUT,UserUrl);
//        return response;
//    }

//    public static Response deleteUser(String UserUrl){
//
//        Response response=given()
//                .header("Authorization","Bearer "+baseToken)
//                .request(Method.DELETE,UserUrl);
//        return response;
//    }

    Users userData;

    public void getUser(int userID){
        String url=userClassUrl+userID;
        Response response=ResponseMethods.getUser(url);
        assertThat(response.statusCode(),equalTo(200));
        System.out.println("Users Get User Data:\n"+response.asString());
        // response.then().log().all();
        }

    public int createUser(){
        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("name", "Sarkar");
        bodyParams.put("email", "sarkar.77@gmail.com");
        bodyParams.put("gender", "male");
        bodyParams.put("status", "active");
        String payload = new Gson().toJson(bodyParams);
        Response response = ResponseMethods.createUser(userClassUrl, payload);
        assertThat(response.statusCode(), equalTo(201));
        System.out.println("Users User Created \n" + response.asString());
        int userID=ResponseMethods.getUserID(response);
        return userID;
    }
    public void updateUser(int userID) {
        String url=userClassUrl+userID;
        Map<String,Object> userUpdatedData=new HashMap<>();
        userUpdatedData.put("name","shri");
        userUpdatedData.put("email","shri22@gmail.com");
        userUpdatedData.put("gender","male");
        userUpdatedData.put("status","inactive");
        String updatepayload=new Gson().toJson(userUpdatedData);
        Response response=ResponseMethods.updateUser(url,updatepayload);
        assertThat(response.statusCode(),equalTo(200));
        System.out.println("Users Updated User Data\n"+response.asString());
    }
    @Test
    public void deleteUser(){
        int userId = createUser();
        try {
            getUser(userId);
            updateUser(userId);
        }
        finally {
            String s = userClassUrl + userId;
            Response response = ResponseMethods.deleteUser(s);
            assertThat(response.statusCode(), equalTo(204));
            System.out.println("Users Deleted User: " + userId);
        }
    }

}
