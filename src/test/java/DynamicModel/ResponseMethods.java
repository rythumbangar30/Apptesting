package DynamicModel;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;

import static DynamicModel.BaseTest.baseToken;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class ResponseMethods extends BaseTest{
    public static Response getUser(String userUrl){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.GET,userUrl);
        return response;
    }

    public static Response createUser(String userUrl,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.POST,userUrl);
        return response;
    }

    public static Response updateUser(String userUrl,String payload){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .request(Method.PUT,userUrl);
        return response;
    }

    public static Response deleteUser(String userUrl){
        Response response=given()
                .header("Authorization","Bearer "+baseToken)
                .request(Method.DELETE,userUrl);
        return response;
    }

    public static int getUserID(Response response){
        JSONObject jsonObject=new JSONObject(response.asString());
        int userID=jsonObject.getInt("id");
        return userID;
    }
}
