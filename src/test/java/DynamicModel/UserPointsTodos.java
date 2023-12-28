package DynamicModel;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserPointsTodos extends BaseTest {

    public void getUser(int userID){
        String url=userTodosUrl+userID;
        Response response=ResponseMethods.getUser(url);
        assertThat(response.statusCode(),equalTo(200));
        System.out.println("Todos User Data: \n"+response.asString());
    }


    public int createUser(){
        String url=userTodosUrl;
        Map<String,Object> createData=new HashMap<>();
        createData.put("user_id",todouser_id);
        createData.put("title","Depraedor sed defigo caelestis avarus amet");
        createData.put("due_on","2024-02-02");
        createData.put("status","completed");
        String payload=new Gson().toJson(createData);
        Response response=ResponseMethods.createUser(url,payload);
        assertThat(response.statusCode(),equalTo(201));
        System.out.println("Todos Created User Data :\n"+response.asString());
        int userID=ResponseMethods.getUserID(response);
        return userID;
    }


    //@Test
    public void updateUser(int userID){
        String url=userTodosUrl+userID;
        Map<String,Object> updateData=new HashMap<>();
        updateData.put("user_id",todouser_id);
        updateData.put("title","vulgus condico caries");
        updateData.put("due_on","2024-02-03");
        updateData.put("status","pending");
        String payload=new Gson().toJson(updateData);
        Response response=ResponseMethods.updateUser(url,payload);
        assertThat(response.statusCode(),equalTo(200));
        System.out.println("Updated User Data :\n"+response.asString());
    }

    @Test
    public void deleteUser(){
        int userID=createUser();
        try{
            getUser(userID);
            updateUser(userID);
        }
        finally {
            String url=userTodosUrl+userID;
            Response response=ResponseMethods.deleteUser(url);
            System.out.println("Todos Deleted User :"+userID);
            assertThat(response.statusCode(),equalTo(204));

        }
        }
}
