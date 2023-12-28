package DynamicModel;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    public static String baseToken;
    public String base_id;
    public String post_id;
    public String todouser_id;
    public String userClassUrl;
    public String userPostsUrl;
    public String userCommentsUrl;
    public String userTodosUrl;
    @BeforeSuite
    public static void beforeSuite(){

        RestAssured.baseURI = ApplicationProperties.INSTANCE.getBaseURI();
        baseToken = ApplicationProperties.INSTANCE.getToken();

    }
    @BeforeClass
    public void beforeTest(){

        base_id="5852902";
        post_id="91166";
        todouser_id="5859523";
        userClassUrl="public/v2/users/";
        userPostsUrl="public/v2/posts/";
        userCommentsUrl="public/v2/comments/";
        userTodosUrl="public/v2/todos/";
    }


}
