package DynamicModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserPointsPosts extends BaseTest{
    public static Response getPostsData(String UserId){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.GET,baseURI+UserId);
        return response;
    }

    public void getUser(){
        String url="public/v2/posts/91270";
        Response response=getPostsData(url);
        System.out.println(response.asString());
        assertThat(200,equalTo(response.statusCode()));

    }

    public static Response createPostUser(String UserUrl,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,baseURI+UserUrl);
        return response;
    }

    public int createUser(){
        String url="public/v2/posts";
        Map<String,Object> createdData=new HashMap<>();
        createdData.put("user","shyam");
        createdData.put("user_id","5852902");
        createdData.put("title","Cunae angelus tepidus vix cupio cubitum");
        createdData.put("body","Eveniet ago eos. Exercitationem studio perspiciatis. Varietas autem sophismata. Accusantium ultio soleo. Sumo talus audeo. Timidus credo vinculum");
        String payload=new Gson().toJson(createdData);
        Response response=createPostUser(url,payload);
        System.out.println("User Created \n"+response.asString());
        JSONObject jsonObject=new JSONObject(response.asString());
        int userId=jsonObject.getInt("id");
        return userId;
    }

    public static Response UpdateUserData(String UserID,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,baseURI+UserID);
        return response;
    }

    public int UpdateUser(){
        int updateID=createUser();
        String url="public/v2/posts/"+updateID;
        Map<String,Object> updateUserData=new HashMap<>();
        updateUserData.put("user","Krisna");
        updateUserData.put("user_id",base_id);
        updateUserData.put("title","Id volutabrum velut nulla aeger desipio");
        updateUserData.put("body","Vis volva capitulus. Vesica delectatio deduco. Catena truculenter aeneus. Voluptas conventus bis.");
        String payload=new Gson().toJson(updateUserData);
        Response response=UpdateUserData(url,payload);
        System.out.println("Updated User Data\n"+response.asString());
        assertThat(200,equalTo(response.statusCode()));
        return updateID;
    }

    public static Response deleteUser(String UserId){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.DELETE,baseURI+UserId);
        return response;
    }
    @Test
    public void deleteUserData(){
        int UserID=UpdateUser();
        String url="public/v2/posts/"+UserID;
        Response response=deleteUser(url);
        System.out.println("Deleted User :\n"+UserID);
        assertThat(204,equalTo(response.statusCode()));

    }

}
