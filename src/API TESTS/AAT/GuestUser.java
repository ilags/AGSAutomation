package AAT;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.matcher.RestAssuredMatchers;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class GuestUser {

    public Long userID;
    public String token = "";
    // public String appId = "";
    public RequestSpecification httpRequest = RestAssured.given();
    public Response response;



    // public  RestAssured.baseURI = RestAssured.baseURI("");


    @BeforeClass
    public static void setUri()
    {
        RestAssured.baseURI = "https://luckyplayapi.playags-games.com";
    }


    // verify new user - token adn Id should be empty
    @Test(priority = 0)
    public void CreationNewGuestUser() {

        //call authenticate user

        response = httpRequest.get(baseURI + "/Authentication/AuthenticateAnonymousUser?controller=Authentication&action=AuthenticateAnonymousUser&deviceName=" +
                "web&deviceID=&ver=1.8.8&isReload=false&sub=0&ebID=0&deviceIDFA=&deviceIDFARealOne=&appId=1&FBUserID=&AppUserID=&Token=");


        //save to string 2 parameter

        JsonPath JsonPathValidator = response.jsonPath();
        userID = JsonPathValidator.get("Data.UserId");
        System.out.println(userID.toString());
        token = JsonPathValidator.get("Data.AppToken");
        System.out.println(token);


    }



    // test that you get correct params (as configurate  by BO) when enter as new guest user // should we add alerts list (welcome pop up).
    @Test(priority = 1)
    public void NewUserParams()

    {

        given().
                queryParam("AppUserID",userID).queryParam("Token",token)
                .get(baseURI + "/VegasSportsAPIService/GetPlayingUserInfo?controller=VegasSportsAPIService&" +
                        "action=GetPlayingUserInfo&sub=0&ebID=0&appId=1&FBUserID=&")
                .then().assertThat().body("Data.User.IsFirstLogin", equalTo(true))
                .and().body("Data.User.FirstName", equalToIgnoringCase("Guest"))
                .and().body("Data.User.Cash", anything("3000000"))  // not tested on data type
                .and().body("Data.User.Experience", anything("5"))
                .and().body("Data.User.LevelID", anything("1"))
                .and().body("Data.User.MegaWheelSpinsLeft", anything("1"))
                .and().body("Data.User.WheelSpinsLeft", anything("1"));

    }



    @Test(priority = 2)
    public void VerifyTHorRumble()
    {
        given().queryParam("AppUserID",userID).queryParam("Token",token).when().
                get(baseURI+"/LiveEvents/GetLiveEvent?controller=LiveEvents&action=GetLiveEvent&FBUserID=&_=1568206325611")
                .then().assertThat().body("Data.LiveEvents[0].Value",is(nullValue())).and().body("Data.LiveEvents[1].Value",is(nullValue()));
    }
}
