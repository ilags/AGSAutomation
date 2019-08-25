package AAT;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;


public class GetInitData {


    String ExpectedAppVersion = " version = 1.8.7";
    String AppId              = "appId = 1";
    String DeviceType         = " deviceName = iphone";



    // Creating BaseUrl for all cases below instead of define them again and again.
    @BeforeAll
     public static void setUri()
    {
        RestAssured.baseURI = "https://luckyplayapi.playags-games.com/mobile/API/GetInitData?";

    }



    // Example Test to check a Specific Key and its value in Response
    @Test
    public void ParameterCheck() {
        given().
                when().
                get(  baseURI +  ExpectedAppVersion  +  "&" + DeviceType +  AppId +  "&unAuthenticatedUserId=2639254358688633&FBUserID=&AppUserID=").
                then().
                assertThat().body("Data.AppVersion", equalToIgnoringCase("web - no version needed"));
    }


    // Example Test to validate the json Response against the json file in (AAT/Jsonstest.json)
    @Test
    public void JsonSchemaValidator() {
            given().
                    when().
                    get(    baseURI + "version=1.8.7&appId=1&deviceName=web&unAuthenticatedUserId=2639254358688633&FBUserID=&AppUserID=").
                    then().
                    assertThat().
                    body( matchesJsonSchemaInClasspath("AAT/jsonstest.json"));
    }


    // Example Test to check that the time that took the response will be less then 1000MS
    @Test
    public void ResponseTime(){
        given().
        when().
                get( baseURI + "version=1.8.7&appId=1&deviceName=web&unAuthenticatedUserId=2639254358688633&FBUserID=&AppUserID=").
                then().
                time(lessThan(1000L));
    }
}













