package ApiTest;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.*;

public class TestAnnotation {
    @Test(description="This is testcase1")
    public void testcase1()
    {
        System.out.println("HR");
    }
    @Test(description="This is testcase2")
    public void testcase2()
    {
        System.out.println("Software Developer");
    }
    @Test(description="This is testcase3")
    public void testcase3()
    {
        System.out.println("QA Analyst");
    }
    @Test
    public void WebStudentLogin(){
        System.out.println("Login through web");
    }
    @Test
    public void MobileStudentLogin(){
        System.out.println("login through mobile");
    }
    @Test(dependsOnMethods = {"WebStudentLogin"})
    public void ApiStudentLogin(){
        System.out.println("Login through API");
    }
    @Test(groups = {"IT_Department"})
    public void personalLoan(){
        System.out.println("Loan sensation");
    }
    @BeforeSuite
    public void beforeLoan(){
        System.out.println("First month payment will come");
    }
    @BeforeTest
    public void beforeTest(){
        System.out.println("It will be executed first");
    }
    @BeforeMethod
    public void before_method(){
        System.out.println("This method will run before every test");
    }
    @BeforeGroups("IT_Department")
    public void before_group(){
        System.out.println("This method will run before IT group");
    }
    @Test
    public void ArrayList(){
        Map<String,String> maping=new HashMap<>();
        maping.put("id","250");
        maping.put("name","bull");
        maping.put("address","Indore");
        assertThat(maping,hasKey("id"));
        assertThat(maping,hasValue("250"));
        assertThat(maping,hasEntry("name","bull"));
    }
}
