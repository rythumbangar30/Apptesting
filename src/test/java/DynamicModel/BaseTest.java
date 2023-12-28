package DynamicModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    public static String baseURI;
    public static String baseToken;
    public static String base_id;
    public static String post_id;
    public static String todouser_id;
    @BeforeMethod
    public void beforeMethod(){

    baseURI = ApplicationProperties.INSTANCE.getBaseURI();
    baseToken = ApplicationProperties.INSTANCE.getToken();

    }
    @BeforeTest
    public void beforeTest(){

        base_id="5852902";
        post_id="91166";
        todouser_id="5859523";
    }

}
