package DynamicModel;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    public static String baseToken;
    public String post_id;
    public String todouser_id;
    public String comments_id;
    @BeforeSuite
    public static void beforeSuite(){

        RestAssured.baseURI = ApplicationProperties.INSTANCE.getBaseURI();
        baseToken = ApplicationProperties.INSTANCE.getToken();

    }
    @BeforeClass
    public void beforeClass(){


        post_id="5886294";
        comments_id="91902";
        todouser_id="5859523";

    }
    public static String getUserID(Response response){
        JSONObject jsonObject=new JSONObject(response.asString());
        int userID=jsonObject.getInt("id");
        String id=Integer.toString(userID);
        return id;
    }
    public static JSONObject returnJSONResponse(Response response) {
        String json=response.asString();
        JSONObject jsonObject=new JSONObject(json);
        return jsonObject;
    }


}
