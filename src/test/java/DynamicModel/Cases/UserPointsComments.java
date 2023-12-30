package DynamicModel.Cases;

import DynamicModel.BaseTest;
import DynamicModel.ResponseMethods;
import DynamicModel.UserPayload.CommentsUser;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class UserPointsComments extends BaseTest {
    Faker faker=new Faker();
    CommentsUser user =new CommentsUser();

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


    updateData.put("post_id",user.getCommentsPost_id());
    updateData.put("name",user.getCommentsName());
    updateData.put("email",faker.internet().emailAddress());
    updateData.put("body",user.getCommentsBody());

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

        user.setCommentsPost_id(post_id);
        user.setCommentsName(faker.name().name());
        user.setCommentsEmail(faker.internet().emailAddress());
        user.setCommentsBody(faker.letterify("Numquam sint commodi. At"));
        userpayload.put("post_id",user.getCommentsPost_id());
        userpayload.put("name",user.getCommentsName());
        userpayload.put("email",user.getCommentsEmail());
        userpayload.put("body",user.getCommentsBody());

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
