package AAT;


import org.junit.jupiter.api.Test;

import java.net.URL;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;



public class aft {

    String ExpectedAppVersion = "1.8.7";
    String AppId              = "1";
    String DeviceType         = "iphone";





    @Test
    public void ParameterCheck() {

        given().
                when().
                get("https://luckyplayapi.playags-games.com/mobile/API/GetInitData?version=1.8.8&appId=1&deviceName=web&unAuthenticatedUserId=2639254358688633&FBUserID=&AppUserID=").
                then().
                assertThat().body("Data.AppVersion", equalToIgnoringCase("web - no version needed"));
    }


    String str = "https://luckyplayapi.playags-games.com/mobile/API/GetInitData" ;


    @Test
    public void JsonSchemaValidator() {

            given().
                    when().
                    get(   str + "?version=1.8.7&appId=1&deviceName=web&unAuthenticatedUserId=2639254358688633&FBUserID=&AppUserID=").
                    then().
                    assertThat().
                    body( matchesJsonSchemaInClasspath("AAT/jsonstest.json"));
    }




    @Test
    public void ResponseTime(){





        when().
                get("https://luckyplayapi.playags-games.com/mobile/API/GetInitData?version=1.8.7&appId=1&deviceName=web&unAuthenticatedUserId=2639254358688633&FBUserID=&AppUserID=").
                then().time(lessThan(850L));
    }





}















