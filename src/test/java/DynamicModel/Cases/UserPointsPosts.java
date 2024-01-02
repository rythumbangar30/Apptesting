package DynamicModel.Cases;
import DynamicModel.BaseTest;
import DynamicModel.UserPayload.PostsUser;
import com.github.javafaker.Faker;
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
import static org.hamcrest.Matchers.hasProperty;

public class UserPointsPosts extends BaseTest {
    Faker faker = new Faker();
    PostsUser user = new PostsUser();

    @Test
    public void getUser() {
        System.out.println("%%%%%%%%%%%%%%%%Get Test Case Run In Posts%%%%%%%%%%%%%%%%%");
        String userId = createUser();
        try {
            Response response = getUser(userId);

            assertThat(response.statusCode(), equalTo(200));

            System.out.println("Posts Get User data:\n" + response.asString());

        }
        finally {
            Response response=deleteUser(userId);
            assertThat("Delete has not been done by posts get",response.statusCode(),equalTo(204));
            System.out.println("Posts Deleted User: "+userId);
        }
    }

    @Test
    public void deleteUser() {
        System.out.println("%%%%%%%%%%%%%%%%Delete Test Case Run In Posts%%%%%%%%%%%%%%%%%");
        String userId = createUser();
        Response response = deleteUser(userId);
        assertThat(response.statusCode(), equalTo(204));
        System.out.println("Posts Deleted User :" + userId);

    }

    @Test
    public void addUser() {
        System.out.println("%%%%%%%%%%%%%%%%Add Test Case Run In Posts%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        Response response=deleteUser(userId);
        assertThat("Delete has not been done by posts add",response.statusCode(),equalTo(204));
        System.out.println("Posts Deleted User: "+userId);
    }

    @Test
    public void updateUser() {
        System.out.println("%%%%%%%%%%%%%%%%Update Test Case Run In Posts%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        try {
            Map<String, Object> updateUserData = new HashMap<>();

            updateUserData.put("user_id", user.getPostsUser_id());
            updateUserData.put("title", faker.book().title());
            updateUserData.put("body", user.getPostsBody());
            String payload = new Gson().toJson(updateUserData);

            Response response = updateUser(userId, payload);

            assertThat(response.statusCode(), equalTo(200));

            JSONObject jsonObject = returnJSONResponse(response);

            PostsUser user = new Gson().fromJson(String.valueOf(jsonObject), PostsUser.class);
            System.out.println(user);
            int id=Integer.parseInt(userId);

            assertThat("Error due to update in posts in body",user,hasProperty("body",equalTo(user.getPostsBody())));
            assertThat("Error due to update in posts in userid",user,hasProperty("user_id",equalTo(user.getPostsUser_id())));
            assertThat("Error due to update in posts in id",user, hasProperty("id", equalTo(id)));

            System.out.println("Posts Updated User Data\n" + response.asString());
        }
        finally {
            Response response=deleteUser(userId);
            assertThat("Delete has not been done by posts update",response.statusCode(),equalTo(204));
            System.out.println("Posts Deleted User: "+userId);
        }
    }

    public String createUser() {

        Map<String, Object> userpayload = new HashMap<>();

        user.setPostsUser_id(post_id);
        user.setPostsTitle(faker.job().title());
        user.setPostsBody(faker.internet().macAddress());

        userpayload.put("user_id", user.getPostsUser_id());
        userpayload.put("title", user.getPostsTitle());
        userpayload.put("body", user.getPostsBody());

        String payload = new Gson().toJson(userpayload);
        Response response = createUser(payload);

        //assertThat(response.statusCode(), equalTo(201));

        System.out.println("Posts User Created \n" + response.asString());

        String userId = getUserID(response);
        return userId;
    }

    static Response getUser(String userUrl) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .request(Method.GET, "posts/"+userUrl);
        return response;
    }

    static Response createUser(String payload) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,"posts/");
        return response;
    }

    static Response updateUser(String userUrl, String payload) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,"posts/"+userUrl);
        return response;
    }

    static Response deleteUser(String userUrl) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .request(Method.DELETE,"posts/"+userUrl);
        return response;
    }
}
