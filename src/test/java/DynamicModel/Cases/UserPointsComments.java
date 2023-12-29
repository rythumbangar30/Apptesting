package DynamicModel.Cases;

import DynamicModel.BaseTest;
import DynamicModel.ResponseMethods;
import DynamicModel.UserPayload.CommentsUser;
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
import static org.hamcrest.Matchers.hasProperty;

public class UserPointsComments extends BaseTest {


    public void getUser(int userID){
        String url=userCommentsUrl+userID;

        Response response= ResponseMethods.getUser(url);

        assertThat(response.statusCode(),equalTo(200));

        System.out.println("Comments User Data :\n"+response.asString());
    }


    public int createUser(){
        String url=userCommentsUrl;
        String payload=dataUser();

        Response response=ResponseMethods.createUser(url,payload);

        assertThat(response.statusCode(),equalTo(201));

        System.out.println("Comments Created User :\n"+response.asString());

        int userID=ResponseMethods.getUserID(response);
        return userID;
    }


    public void updateUser(int userID){
    String url=userCommentsUrl+userID;

    Map<String,Object> updateData=new HashMap<>();


    updateData.put("post_id",post_id);
    updateData.put("name","prem");
    updateData.put("email","premkumar1@gmail.com");
    updateData.put("body","Eos sit velit. Aliquid animi rerum. Repudiandae sed");

    String payload=new Gson().toJson(updateData);

    Response response=ResponseMethods.updateUser(url,payload);

    assertThat(response.statusCode(),equalTo(200));

    JSONObject jsonObject=ResponseMethods.returnJSONResponse(response);

    CommentsUser user=new Gson().fromJson(String.valueOf(jsonObject), CommentsUser.class);

    assertThat(user,hasProperty("id",equalTo(userID)));

    System.out.println("Comments Updated User Data :\n"+response.asString());

    }
    private String dataUser(){
        Map<String,Object> userpayload=new HashMap<>();

        userpayload.put("post","worker");
        userpayload.put("post_id",post_id);
        userpayload.put("name","Shyam");
        userpayload.put("email","shyam02@gmail.com");
        userpayload.put("body","Numquam sint commodi. At est saepe. Assumenda qui voluptas. Dolor earum et");

        String payload=new Gson().toJson(userpayload);
        return payload;
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
