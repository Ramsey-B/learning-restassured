
import Models.CharacterModel;
import ReusableMethods.JiraMethods;
import ReusableMethods.SwapiMethods;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.jayway.restassured.RestAssured.given;
import static java.util.Objects.compare;
import static org.hamcrest.Matchers.hasItem;

public class SwapiTest {
    Properties prop = new Properties();
    String sessionId;

    @BeforeClass
    public void getProperties() throws IOException {
        FileInputStream evnFile = new FileInputStream("src/test/files/evn.properties");
        prop.load(evnFile);
        sessionId = JiraMethods.LoginInToJira(prop.getProperty("HOST_JIRA"), prop.getProperty("JIRA_LOGIN"));
    }

    @Test(dataProvider = "CharacterData", dataProviderClass = DataProviderTest.class)
    public void getCharacterTest(int id, CharacterModel character) {
        Response res = given().
                when().
                get(prop.getProperty("HOST_PEOPLE") + id + "/?format=json").
                then().
                assertThat().
                statusCode(200).
                extract().response();
        Assert.assertTrue(SwapiMethods.ComparePeople(res.as(CharacterModel.class), character));
    }

    @Test(dataProvider = "CharacterData", dataProviderClass = DataProviderTest.class)
    public void getAllCharacterTest(int id, CharacterModel character) {
        given().
                when().
                get(prop.getProperty("HOST_PEOPLE")).
                then().
                assertThat().
                body("results.getAt('name')", hasItem(character.name));
    }

    @Test(dataProvider = "CharacterData", dataProviderClass = DataProviderTest.class)
    public void validateCharacterClassTest(int id, CharacterModel character) {
        ResponseBody res = given().
                when().
                get(prop.getProperty("HOST_PEOPLE") + id + "/?format=json").
                getBody();
        System.out.println("Validating response from GET request to " +prop.getProperty("HOST_PEOPLE") + id + "/?format=json is of type \"CharacterModel\"");
        System.out.println(ReflectionToStringBuilder.toString(res.as(CharacterModel.class).hashCode()));
        System.out.println(ReflectionToStringBuilder.toString(character.hashCode()));
        Assert.assertTrue(compare(res.as(CharacterModel.class), character));
        Assert.fail("Error: Response is not of type \"CharacterModel\"");
    }
}
