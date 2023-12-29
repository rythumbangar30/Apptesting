package DynamicModel.Cases;

import DynamicModel.BaseTest;
import DynamicModel.ResponseMethods;
import DynamicModel.UserPayload.PostsUser;
import DynamicModel.UserPayload.UsersUser;
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
import static org.hamcrest.Matchers.hasProperty;

public class UserPointsPosts extends BaseTest {


    public void getUser(int userID){
        String url=userPostsUrl+userID;

        Response response= ResponseMethods.getUser(url);

        assertThat(response.statusCode(),equalTo(200));

        System.out.println("Posts Get User data:\n"+response.asString());
    }


    public int createUser(){
        String url=userPostsUrl;

        String payload=dataUser();

        Response response=ResponseMethods.createUser(url,payload);

        assertThat(response.statusCode(),equalTo(201));

        System.out.println("Posts User Created \n"+response.asString());

        int userId=ResponseMethods.getUserID(response);
        return userId;
    }


    public void updateUser(int userID){
        String url=userPostsUrl+userID;

        Map<String,Object> updateUserData=new HashMap<>();

        updateUserData.put("user_id",base_id);
        updateUserData.put("title","Id volutabrum velut nulla aeger desipio");
        updateUserData.put("body","Vis volva capitulus. Vesica delectatio deduco. Catena truculenter aeneus. Voluptas conventus bis.");

        String payload=new Gson().toJson(updateUserData);

        Response response=ResponseMethods.updateUser(url,payload);

        assertThat(response.statusCode(),equalTo(200));

        JSONObject jsonObject=ResponseMethods.returnJSONResponse(response);

        PostsUser user=new Gson().fromJson(String.valueOf(jsonObject),PostsUser.class);

        assertThat(user,hasProperty("id",equalTo(userID)));

        System.out.println("Posts Updated User Data\n"+response.asString());

    }

    private String dataUser(){

        Map<String,Object> userpayload=new HashMap<>();

        userpayload.put("user","shyam");
        userpayload.put("user_id",base_id);
        userpayload.put("title","Cunae angelus tepidus vix cupio cubitum");
        userpayload.put("body","Eveniet ago eos. Exercitationem studio perspiciatis. Varietas autem sophismata. Accusantium ultio soleo. Sumo talus audeo. Timidus credo vinculum");

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
        String url=userPostsUrl+userID;
        Response response=ResponseMethods.deleteUser(url);
        assertThat(response.statusCode(),equalTo(204));
        System.out.println("Posts Deleted User :"+userID);
        }
    }

}
