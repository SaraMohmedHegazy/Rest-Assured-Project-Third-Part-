import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Rest_TestCases {
@Test
    public void Validate_StatusCode(){
  Response responce = RestAssured.get("https://ipinfo.io/161.185.160.93/geo");
    Assert.assertEquals(responce.statusCode(),200);
}
@Test
public void validate_response_time(){
   Response response = RestAssured.get("https://ipinfo.io/161.185.160.93/geo");
  Assert.assertTrue(response.getTime()>=2000&&response.getTime()<=5000);
    System.out.println(response.getTime());
}
    @Test
    public void validate_response_Size(){
        Response response = RestAssured.get("https://ipinfo.io/161.185.160.93/geo");
        Assert.assertTrue(response.getBody().asString().length()>=200&&response.getBody().asString().length()<=500);
        System.out.println(response.getBody().asString().length());
    }

@Test
    public void Validate_IP(){
    Response response = RestAssured.get("https://ipinfo.io/161.185.160.93/geo");
    String IP =response.jsonPath().getString("ip");
    Assert.assertEquals(IP,"161.185.160.93","IP is not as expected");

}
    @Test
    public void Validate_City(){
        Response response = RestAssured.get("https://ipinfo.io/161.185.160.93/geo");
        String IP =response.jsonPath().getString("city");
        Assert.assertEquals(IP,"New York City","City is not as expected");

    }
    @Test
    public void validate_response_data(){
    given().get("https://ipinfo.io/161.185.160.93/geo").then().
            assertThat().body("region", equalTo("New York")).and().
            assertThat().body("country", equalTo("US")).and().
            assertThat().body("loc", equalTo("40.7143,-74.0060")).and().
            assertThat().body("org", equalTo("AS22252 The City of New York")).and().
            assertThat().body("postal", equalTo("10001")).and().
            assertThat().body("timezone", equalTo("America/New_York")).and().
            assertThat().body("readme", equalTo("https://ipinfo.io/missingauth"));

    }
    @Test
    public void Validate_invalidApi(){
    given().get("https://ipinfo.io/161.185.160.93345/geo").then().
            assertThat().statusCode(404);
    }
    @Test
    public void Validate_Response_Formate(){
    Response response = RestAssured.get("https://ipinfo.io/161.185.160.93/geo");
    Assert.assertTrue(response.contentType().contains("application/json"));
    }
    @Test
    public void Validate_Add_New_IP_Address(){
    JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", "203.0.113.5");
        jsonObject.put("city", "Los Angeles");
        jsonObject.put("region", "California");
        jsonObject.put("country", "US");
        jsonObject.put("loc", "34.0522,-118.2437");
        jsonObject.put("org", "AS12345 Example Organization");
        jsonObject.put("postal", "90001");
        jsonObject.put("timezone", "America/Los_Angeles");
        jsonObject.put("readme", "https://ipinfo.io/missingauth");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .post("https://ipinfo.io/");
        Assert.assertEquals(response.statusCode(), 200);


    }
    @Test
    public void validate_get_new_ip(){
    given().get("https://ipinfo.io/203.0.113.5/geo").then()
            .assertThat().statusCode(200);
    }

}
