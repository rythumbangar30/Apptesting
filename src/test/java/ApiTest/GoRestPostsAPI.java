//package ApiTest;
//
//import DynamicModel.ApplicationProperties;
//import org.testng.annotations.Test;
//import io.restassured.http.ContentType;
//import io.restassured.http.Method;
//import io.restassured.response.Response;
//
//import static io.restassured.RestAssured.*;
//public class GoRestPostsAPI {
//
//   @Test
//    void getAllUsers(){
//      String url= ApplicationProperties.INSTANCE.getBaseURI();
//      String token=ApplicationProperties.INSTANCE.getToken();
//
//       System.out.println(url);
//
//        Response response=given()
//                .header("Authorization","Bearer "+token)
//                .request(Method.GET,url);
//        System.out.println(response.statusCode());
//        System.out.println(response.asString());
//
//    }
//
//    //@Test
//    void getUser(){
//        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
//        baseURI="https://gorest.co.in";
//        Response response=given()
//                .header("Authorization","Bearer "+token)
//                .request(Method.GET,"public/v2/posts/90761");
//        System.out.println(response.statusCode());
//        System.out.println(response.asString());
//
//    }
//
//   // @Test
//    void createUser(){
//        baseURI="https://gorest.co.in";
//        String value="{  \"user_id\":\"5840945\",\n" +
//                "    \"title\":\"Sharp area of conductor branch\",\n" +
//                "    \"body\":\"Select the POST request method, and go to Body option where we have different options for sending data:\"}";
//        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
//        Response response=given()
//                .header("Authorization","Bearer "+token)
//                .contentType(ContentType.JSON)
//                .body(value)
//                .request(Method.POST,"/public/v2/posts");
//        System.out.println(response.statusCode());
//        System.out.println(response.asString());
//    }
//
//    //@Test
//    void putUser(){
//
//        baseURI="https://gorest.co.in";
//        String value="{  \"user\":\"90668\",\n" +
//                "    \"user_id\":\"5840922\",\n"+
//                "    \"title\":\"Krishna Krishna Hari krishna\",\n" +
//                "    \"body\":\"The era of lord Krishna is being like is an divine in this it will be like amazing divine entity has been come to the earth\"}";
//        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
//        Response response=given()
//                .header("Authorization","Bearer "+token)
//                .contentType(ContentType.JSON)
//                .body(value)
//                .request(Method.PUT,"/public/v2/posts/90668");
//        System.out.println(response.statusCode());
//        System.out.println(response.asString());
//
//    }
//    //@Test
//    void deleteUser(){
//        baseURI="https://gorest.co.in";
//        String token="dabe4659ec508bd25a08d62355d344117c9c9228324cfb14962ecac5404b4597";
//        Response response =given()
//                .header("Authorization","Bearer "+token)
//                .request(Method.DELETE,"/public/v2/posts/90673");
//        System.out.println(response.statusCode());
//        System.out.println(response.asString());
//    }
//
//}
