package DynamicModel;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

import static DynamicModel.BaseTest.baseToken;
import static DynamicModel.BaseTest.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserPointsTodos extends BaseTest {
    public static Response getTodoUser(String userID){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.GET,baseURI+userID);
        return response;
    }
    //@Test
    public void getUser(){
        String url="public/v2/todos/38109";
        Response response=getTodoUser(url);
        System.out.println("User Data: \n"+response.asString());
        assertThat(200,equalTo(response.statusCode()));
    }

    public static Response createTodosUser(String userID,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,baseURI+userID);
        return response;
    }

    public int createUser(){
        String url="public/v2/todos/";
        Map<String,Object> createData=new HashMap<>();
        createData.put("user_id",todouser_id);
        createData.put("title","Depraedor sed defigo caelestis avarus amet");
        createData.put("due_on","2024-02-02");
        createData.put("status","completed");
        String payload=new Gson().toJson(createData);
        Response response=createTodosUser(url,payload);
        JSONObject jsonObject=new JSONObject(response.asString());
        int userID=jsonObject.getInt("id");
        System.out.println("Created User Data :\n"+response.asString());
        assertThat(201,equalTo(response.statusCode()));
        return userID;
    }

    public static Response updateTodosData(String userID,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,baseURI+userID);
        return response;
    }

    //@Test
    public int updateUser(){
        int userID=createUser();
        String url="public/v2/todos/"+userID;
        Map<String,Object> updateData=new HashMap<>();
        updateData.put("user_id",todouser_id);
        updateData.put("title","vulgus condico caries");
        updateData.put("due_on","2024-02-03");
        updateData.put("status","pending");
        String payload=new Gson().toJson(updateData);
        Response response=updateTodosData(url,payload);
        System.out.println("Updated User Data :\n"+response.asString());
        assertThat(200,equalTo(response.statusCode()));
        return userID;
    }

    public static Response deleteTodosUser(String userID){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.DELETE,baseURI+userID);
        return response;
    }
    @Test
    public void deleteUser(){
        int userID=updateUser();
        String url="public/v2/todos/"+userID;
        Response response=deleteTodosUser(url);
        System.out.println("Deleted User :"+userID);
        assertThat(204,equalTo(response.statusCode()));
    }
}
