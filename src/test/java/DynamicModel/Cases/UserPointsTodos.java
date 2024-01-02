package DynamicModel.Cases;
import DynamicModel.BaseTest;
import DynamicModel.UserPayload.TodosUser;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class UserPointsTodos extends BaseTest {
    Faker faker=new Faker();
    TodosUser user=new TodosUser();

    @Test
    public void getUser(){
        System.out.println("%%%%%%%%%%%%%%%%Get Test Case Run In Todos%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        try {

            Response response = getUser(userId);

            assertThat(response.statusCode(), equalTo(200));

            System.out.println("Todos User Data: \n" + response.asString());
        }
        finally {
            Response response=deleteUser(userId);
            assertThat("Delete has not been done by Todos get",response.statusCode(),equalTo(204));
            System.out.println("Todos Deleted User: "+userId);
        }
    }

    @Test
    public void addUser(){
        System.out.println("%%%%%%%%%%%%%%%%Add Test Case Run In Todos%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        Response response=deleteUser(userId);
        assertThat("Delete has not been done by Todos add",response.statusCode(),equalTo(204));
        System.out.println("Todos Deleted User: "+userId);

    }
    @Test
    public void updateUser(){
        System.out.println("%%%%%%%%%%%%%%%%Update Test Case Run In Todos%%%%%%%%%%%%%%%%%");
        String userId=createUser();

        try {
            Map<String, Object> updateData = new HashMap<>();

            updateData.put("user_id", user.getTodosUser_id());
            updateData.put("title", user.getTodosTitle());
            updateData.put("due_on", user.getTodosDue_on());
            updateData.put("status", "completed");

            String payload = new Gson().toJson(updateData);

            Response response = updateUser(userId, payload);

            assertThat(response.statusCode(), equalTo(200));

            JSONObject jsonObject = returnJSONResponse(response);

            TodosUser user = new Gson().fromJson(String.valueOf(jsonObject), TodosUser.class);
            int id=Integer.parseInt(userId);

            assertThat("Error due to update in todos in due on",user,hasProperty("due_on",equalTo(user.getTodosDue_on())));
            assertThat("Error due to update in todos in title",user,hasProperty("title",equalTo(user.getTodosTitle())));
            assertThat("Error due to update in todos in userid",user,hasProperty("user_id",equalTo(user.getTodosUser_id())));
            assertThat("Error due to update in todos in status",user,hasProperty("status",equalTo("completed")));
            assertThat(user, hasProperty("id", equalTo(id)));

            System.out.println("Updated User Data :\n" + response.asString());
        }
        finally {
            Response response=deleteUser(userId);
            assertThat("Delete has not been done by Todos update",response.statusCode(),equalTo(204));
            System.out.println("Todos Deleted User: "+userId);
        }
    }
    @Test
    public void deleteUser(){
        System.out.println("%%%%%%%%%%%%%%%%Delete Test Case Run In Todos%%%%%%%%%%%%%%%%%");
        String userId=createUser();
        Response response=deleteUser(userId);
        System.out.println("Todos Deleted User :"+userId);
        assertThat(response.statusCode(),equalTo(204));
    }
    public String createUser(){
        Map<String,Object> userpayload=new HashMap<>();

        user.setTodoUser_id(todouser_id);
        user.setTodoTitle(faker.book().title());
        user.setTodoDue_on(faker.date().birthday());
        user.setTodoStatus("pending");
        userpayload.put("user_id",user.getTodosUser_id());
        userpayload.put("title",user.getTodosTitle());
        userpayload.put("due_on",user.getTodosDue_on());
        userpayload.put("status",user.getTodosStatus());

        String payload=new Gson().toJson(userpayload);
        Response response=createUser(payload);

        assertThat(response.statusCode(),equalTo(201));

        System.out.println("Todos Created User Data :\n"+response.asString());

        String userId=getUserID(response);
        return userId;
    }

    static Response getUser(String userUrl) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .request(Method.GET, "todos/"+userUrl);
        return response;
    }

    static Response createUser(String payload) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,"todos/");
        return response;
    }

    static Response updateUser(String userUrl, String payload) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,"todos/"+userUrl);
        return response;
    }

    static Response deleteUser(String userUrl) {
        Response response = given()
                .header("Authorization", "Bearer " + baseToken)
                .request(Method.DELETE,"todos/"+userUrl);
        return response;
    }

@AfterTest
public void afterTest(){
    System.out.println("beforeTes ");
}
}

