package DynamicModel.Cases;
import DynamicModel.BaseTest;
import DynamicModel.ResponseMethods;
import DynamicModel.UserPayload.UsersUser;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class UserPoints extends BaseTest {


    public void getUser(int userID){
        String url=userClassUrl+userID;

        Response response= ResponseMethods.getUser(url);

        assertThat(response.statusCode(),equalTo(200));

        System.out.println("Users Get User Data:\n"+response.asString());

        // response.then().log().all();
        }

    public int createUser(){
        String payload = dataUser();
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

        JSONObject jsonObject=ResponseMethods.returnJSONResponse(response);

        UsersUser user=new Gson().fromJson(String.valueOf(jsonObject),UsersUser.class);

        assertThat(user,hasProperty("id",equalTo(userID)));
    }



    private String dataUser(){
        Map<String,Object> userpayload=new HashMap<>();

        userpayload.put("name","sarkar");
        userpayload.put("email","sarkar.77@gmail.com");
        userpayload.put("gender", "male");
        userpayload.put("status", "active");

        String payload=new Gson().toJson(userpayload);
        return payload;
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
