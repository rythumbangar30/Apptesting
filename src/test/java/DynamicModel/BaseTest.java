package DynamicModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    public static String baseURI;
    public static String baseToken;
    @BeforeMethod
            public void beforeMethod(){

    baseURI = ApplicationProperties.INSTANCE.getBaseURI();
    baseToken = ApplicationProperties.INSTANCE.getToken();

    }


}
