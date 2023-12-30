package DynamicModel.Cases;
import DynamicModel.BaseTest;
import DynamicModel.ResponseMethods;
import DynamicModel.UserPayload.TodosUser;
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

public class UserPointsTodos extends BaseTest {
    Faker faker=new Faker();
    TodosUser user=new TodosUser();
    private void getUser(int userID){
        String url=userTodosUrl+userID;

        Response response= ResponseMethods.getUser(url);

        assertThat(response.statusCode(),equalTo(200));

        System.out.println("Todos User Data: \n"+response.asString());
    }

    private int createUser(){
        String url=userTodosUrl;
        String payload=dataUser();

        Response response=ResponseMethods.createUser(url,payload);

        assertThat(response.statusCode(),equalTo(201));

        System.out.println("Todos Created User Data :\n"+response.asString());

        int userID=ResponseMethods.getUserID(response);
        return userID;
    }

    private void updateUser(int userID){
        String url=userTodosUrl+userID;

        Map<String,Object> updateData=new HashMap<>();

        updateData.put("user_id",user.getTodosUser_id());
        updateData.put("title",user.getTodosTitle());
        updateData.put("due_on",user.getTodosDue_on());
        updateData.put("status","completed");

        String payload=new Gson().toJson(updateData);

        Response response=ResponseMethods.updateUser(url,payload);

        assertThat(response.statusCode(),equalTo(200));

        JSONObject jsonObject=ResponseMethods.returnJSONResponse(response);

        TodosUser user=new Gson().fromJson(String.valueOf(jsonObject), TodosUser.class);

        assertThat(user,hasProperty("id",equalTo(userID)));

        System.out.println("Updated User Data :\n"+response.asString());
    }
    private String dataUser(){
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
        return payload;
    }
    @Test
    private void deleteUser(){
        int userID=createUser();
        try{
            getUser(userID);
            updateUser(userID);
        }
        finally {
            String url=userTodosUrl+userID;
            Response response=ResponseMethods.deleteUser(url);
            System.out.println("Todos Deleted User :"+userID);
            assertThat(response.statusCode(),equalTo(204));
        }
    }
}
