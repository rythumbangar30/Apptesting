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

    public static Response getCommentsData(String userID){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.GET,baseURI+userID);
        return response;
    }
    @Test
    public void getUser(){
        String url="public/v2/comments/74075";
        Response response=getCommentsData(url);
        System.out.println("User Data :\n"+response.asString());
        assertThat(200,equalTo(response.statusCode()));
    }

    public static Response createCommentsUser(String userID,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,baseURI+userID);
        return response;
    }

    public int createUser(){
        String url="public/v2/comments";
        Map<String,Object> createData=new HashMap<>();
        createData.put("post","worker");
        createData.put("post_id",post_id);
        createData.put("name","Shyam");
        createData.put("email","shyam02@gmail.com");
        createData.put("body","Numquam sint commodi. At est saepe. Assumenda qui voluptas. Dolor earum et");
        String payload=new Gson().toJson(createData);
        Response response=createCommentsUser(url,payload);
        System.out.println("Created User :\n"+response.asString());
        JSONObject jsonObject=new JSONObject(response.asString());
        int userID=jsonObject.getInt("id");
        return userID;
    }

    public static Response updateCommentsUser(String userID,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,baseURI+userID);
        return response;
    }

    public int updateUser(){
    int userID=createUser();
    String url="public/v2/comments/"+userID;
    Map<String,Object> updateData=new HashMap<>();
    updateData.put("post","hustle");
    updateData.put("post_id",post_id);
    updateData.put("name","prem");
    updateData.put("email","premkumar1@gmail.com");
    updateData.put("body","Eos sit velit. Aliquid animi rerum. Repudiandae sed");
    String payload=new Gson().toJson(updateData);
    Response response=updateCommentsUser(url,payload);
    System.out.println("Updated User Data :\n"+response.asString());
    assertThat(200,equalTo(response.statusCode()));
    return userID;
    }

    public static Response deleteCommentsUser(String userID){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.DELETE,baseURI+userID);
        return response;
    }
    @Test
    public void deleteUser(){
        int userID=updateUser();
        String url="public/v2/comments/"+userID;
        Response response=deleteCommentsUser(url);
        System.out.println("Deleted User ID: "+userID);
        assertThat(204,equalTo(response.statusCode()));
    }
}
