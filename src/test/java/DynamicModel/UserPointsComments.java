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
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserPointsComments extends BaseTest{


    public void getUser(int userID){
        String url=userCommentsUrl+userID;
        Response response=ResponseMethods.getUser(url);
        assertThat(response.statusCode(),equalTo(200));
        System.out.println("Comments User Data :\n"+response.asString());
    }


    public int createUser(){
        String url=userCommentsUrl;
        Map<String,Object> createData=new HashMap<>();
        createData.put("post","worker");
        createData.put("post_id",post_id);
        createData.put("name","Shyam");
        createData.put("email","shyam02@gmail.com");
        createData.put("body","Numquam sint commodi. At est saepe. Assumenda qui voluptas. Dolor earum et");
        String payload=new Gson().toJson(createData);
        Response response=ResponseMethods.createUser(url,payload);
        assertThat(response.statusCode(),equalTo(201));
        System.out.println("Comments Created User :\n"+response.asString());
        int userID=ResponseMethods.getUserID(response);
        return userID;
    }


    public void updateUser(int userID){
    String url=userCommentsUrl+userID;
    Map<String,Object> updateData=new HashMap<>();
    updateData.put("post","hustle");
    updateData.put("post_id",post_id);
    updateData.put("name","prem");
    updateData.put("email","premkumar1@gmail.com");
    updateData.put("body","Eos sit velit. Aliquid animi rerum. Repudiandae sed");
    String payload=new Gson().toJson(updateData);
    Response response=ResponseMethods.updateUser(url,payload);
    assertThat(response.statusCode(),equalTo(200));
    System.out.println("Comments Updated User Data :\n"+response.asString());

    }
    @Test
    public void deleteUser(){
        int userID=createUser();
        try {
            getUser(userID);
            updateUser(userID);
        }
        finally {
            String url = userCommentsUrl + userID;
            Response response = ResponseMethods.deleteUser(url);
            assertThat(response.statusCode(), equalTo(204));
            System.out.println("Comments Deleted User ID: " + userID);
        }
    }
}
