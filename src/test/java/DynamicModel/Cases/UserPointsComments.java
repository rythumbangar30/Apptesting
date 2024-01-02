package DynamicModel.Cases;
import DynamicModel.ApplicationProperties;
import DynamicModel.BaseTest;
import DynamicModel.SwaggerConfiguration;
import DynamicModel.UserPayload.CommentsUser;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static DynamicModel.BaseTest.baseToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class UserPointsComments extends BaseTest  {
    Faker faker=new Faker();
    CommentsUser user =new CommentsUser();

    @AfterTest
    public void afterTest(){
        System.out.println("beforeTes ");
    }
    @Test
    public void getUser(){
        System.out.println("%%%%%%%%%%%%%%%%Get Test Case Run In Comments%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        try {
        Response response= getUser(userId);

        assertThat(response.statusCode(),equalTo(200));

        System.out.println("Comments User Data :\n"+response.asString());

        }
        finally {
            Response response=deleteUser(userId);
            assertThat("Delete has not been done by comments get",response.statusCode(),equalTo(204));
            System.out.println("Posts Deleted User: "+userId);
        }
    }

    @Test
    public void addUser(){
        System.out.println("%%%%%%%%%%%%%%%%Add Test Case Run In Comments%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        Response response=deleteUser(userId);
        assertThat("Delete has not been done by comments add",response.statusCode(),equalTo(204));
        System.out.println("Posts Deleted User: "+userId);
    }

    @Test
    public void updateUser(){
    System.out.println("%%%%%%%%%%%%%%%%Update Test Case Run In Comments%%%%%%%%%%%%%%%%%");
    String userId=createUser();

    try {
        Map<String, Object> updateData = new HashMap<>();


        updateData.put("post_id", user.getCommentsPost_id());
        updateData.put("name", user.getCommentsName());
        updateData.put("email", faker.internet().emailAddress());
        updateData.put("body", user.getCommentsBody());

        String payload = new Gson().toJson(updateData);

        Response response = updateUser(userId, payload);

        assertThat(response.statusCode(), equalTo(200));

        JSONObject jsonObject = returnJSONResponse(response);

        CommentsUser user = new Gson().fromJson(String.valueOf(jsonObject), CommentsUser.class);
        int id=Integer.parseInt(userId);

        assertThat("Error due to update in comments in postid",user,hasProperty("post_id",equalTo(user.getCommentsPost_id())));
        assertThat("Error due to update in comments in body",user,hasProperty("body",equalTo(user.getCommentsBody())));
        assertThat("Error due to update in comments in name",user,hasProperty("name",equalTo(user.getCommentsName())));
        assertThat(user, hasProperty("id", equalTo(id)));

        System.out.println("Comments Updated User Data :\n" + response.asString());
    }
    finally {
        Response response=deleteUser(userId);
        assertThat("Delete has not been done by comments update",response.statusCode(),equalTo(204));
        System.out.println("Posts Deleted User: "+userId);
    }
    }
    @Test
    public void deleteUser(){
        System.out.println("%%%%%%%%%%%%%%%%Delete Test Case Run In Comments%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        Response response = deleteUser(userId);
        assertThat(response.statusCode(), equalTo(204));
        System.out.println("Comments Deleted User ID: " + userId);
    }
    public String createUser(){
        Map<String,Object> userpayload=new HashMap<>();

        user.setCommentsPost_id(comments_id);
        user.setCommentsName(faker.name().name());
        user.setCommentsEmail(faker.internet().emailAddress());
        user.setCommentsBody(faker.letterify("Numquam sint commodi. At"));
        userpayload.put("post_id",user.getCommentsPost_id());
        userpayload.put("name",user.getCommentsName());
        userpayload.put("email",user.getCommentsEmail());
        userpayload.put("body",user.getCommentsBody());

        String payload=new Gson().toJson(userpayload);
        Response response=createUser(payload);

        assertThat(response.statusCode(),equalTo(201));

        System.out.println("Comments Created User :\n"+response.asString());

        String userId=getUserID(response);
        return userId;
    }


    static Response getUser(String userUrl) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .request(Method.GET,"comments/"+userUrl);
        return response;
    }

    static Response createUser(String payload) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,"comments/");
        return response;
    }

    static Response updateUser(String userUrl, String payload) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,"comments/"+userUrl);
        return response;
    }

    static Response deleteUser(String userUrl) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .request(Method.DELETE,"comments/"+userUrl);
        return response;
    }
}
