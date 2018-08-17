package ReusableMethods;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

public class JiraMethods {
    public static String LoginInToJira(String baseUrl, String loginCreds) {
        RestAssured.baseURI = baseUrl;
        Response res = given().
                header("Content-Type", "application/json").
                body(loginCreds).
                when().
                post("/rest/auth/1/session").
                then().
                statusCode(200).
                extract().response();
        JsonPath resJson = res.jsonPath();
        return resJson.get("session.value");
    }

    public static void CreateJiraIssue(String baseUrl,String sessionId, String projectKey, String summary, String description) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(baseUrl + "/rest/api/2/issue");
        request.setHeader("Cookie", "JSESSIONID="+sessionId);
        request.addHeader("content-type", "application/json");
        StringEntity reqBody = new StringEntity("{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       {\n" +
                "          \"key\": \"" +projectKey+ "\" \n" +
                "       },\n" +
                "       \"summary\": \"" +summary+ "\",\n" +
                "       \"description\": \"" +description+ "\",\n" +
                "       \"issuetype\": {\n" +
                "          \"name\": \"Bug\"\n" +
                "       }\n" +
                "   }\n" +
                "}", ContentType.APPLICATION_JSON);
        request.setEntity(reqBody);
        client.execute(request);
        System.out.println("ERROR LOGGED TO JIRA");
    }
}
