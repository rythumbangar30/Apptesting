package DynamicModel.Cases;
import DynamicModel.BaseTest;
import DynamicModel.UserPayload.UsersUser;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class UserPoints extends BaseTest {

    Faker faker=new Faker();
    UsersUser user=new UsersUser();

    @Test
    public void deleteUser(){
        System.out.println("%%%%%%%%%%%%%%%%Delete Test Case Run In Users%%%%%%%%%%%%%%%%%");
        String userId = createUser();

        Response response = deleteUser(userId);
        assertThat(response.statusCode(), equalTo(204));
        System.out.println("Users Deleted User: " + userId);

    }

    @Test
    void addUser(){
        System.out.println("%%%%%%%%%%%%%%%%Add Test Case Run In Users%%%%%%%%%%%%%%%%%");
        String userId = createUser();

        Response response=deleteUser(userId);
        assertThat(response.statusCode(),equalTo(204));
        System.out.println("Users Deleted User: "+userId);

    }
    @Test
    void updateUser(){
        System.out.println("%%%%%%%%%%%%%%%%Update Test Case Run In Users%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        try {

            Map<String,Object> userUpdatedData=new HashMap<>();

            userUpdatedData.put("name",user.getUsersName());
            userUpdatedData.put("email",user.getUsersEmail());
            userUpdatedData.put("gender",user.getUsersGender());
            userUpdatedData.put("status","inactive");

            String updatepayload=new Gson().toJson(userUpdatedData);

            Response response=updateUser(userId,updatepayload);

            assertThat(response.statusCode(),equalTo(200));

            System.out.println("Users Updated User Data\n"+response.asString());

            JSONObject jsonObject=returnJSONResponse(response);

            UsersUser user=new Gson().fromJson(String.valueOf(jsonObject),UsersUser.class);

            assertThat("Error due to update in users in name",user,hasProperty("name",equalTo(user.getUsersName())));
            assertThat("Error due to update in users in email",user,hasProperty("email",equalTo(user.getUsersEmail())));
            assertThat("Error due to update in users in gender",user,hasProperty("gender",equalTo(user.getUsersGender())));
            assertThat("Error due to update in users in status",user,hasProperty("status",equalTo("inactive")));
        }
        finally {

            Response response=deleteUser(userId);
            assertThat(response.statusCode(),equalTo(204));
            System.out.println("Users Deleted User: "+userId);

        }
    }
    @Test
    void getUser(){
        System.out.println("%%%%%%%%%%%%%%%%Get Test Case Run In Users%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        try {
            Response response= getUser(userId);

            assertThat(response.statusCode(),equalTo(200));

            System.out.println("Users Get User Data:\n"+response.asString());
        }
        finally {
            Response response=deleteUser(userId);
            assertThat(response.statusCode(),equalTo(204));
            System.out.println("Users Deleted User: "+userId);
        }
    }

    public String createUser(){
        Map<String,Object> userpayload=new HashMap<>();

        user.setUsersName(faker.name().username());
        user.setUsersEmail(faker.internet().emailAddress());
        user.setUsersGender("male");
        user.setUsersStatus("active");
        userpayload.put("name",user.getUsersName());
        userpayload.put("email",user.getUsersEmail());
        userpayload.put("gender", user.getUsersGender());
        userpayload.put("status", user.getUsersStatus());
        String payload=new Gson().toJson(userpayload);

        Response response = createUser(payload);

//        assertThat(response.statusCode(), equalTo(201));

        System.out.println("Users User Created \n" + response.asString());

        String userId=getUserID(response);
        return userId;
    }


    static Response getUser(String userUrl){
       Response response=given()
            .header("Authorization","Bearer "+baseToken)
            .request(Method.GET,"users/"+userUrl);
       return response;
    }

    static Response createUser(String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,"users/");
        return response;
    }

    static Response updateUser(String userUrl,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,"users/"+userUrl);
        return response;
    }

    static Response deleteUser(String userUrl){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.DELETE,"users/"+userUrl);
        return response;
    }



}
