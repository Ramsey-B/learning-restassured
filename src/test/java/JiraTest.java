import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ReusableMethods.JiraMethods;

public class JiraTest {
    Properties prop = new Properties();
    String sessionId;

    @BeforeClass
    public void getProperties() throws IOException {
        FileInputStream evnFile = new FileInputStream("src/test/files/evn.properties");
        prop.load(evnFile);
        sessionId = JiraMethods.LoginInToJira(prop.getProperty("HOST_JIRA"), prop.getProperty("JIRA_LOGIN"));
    }

    @Test
    public void createIssueTest() throws IOException {
        JiraMethods.CreateJiraIssue(prop.getProperty("HOST_JIRA"), sessionId, prop.getProperty("PROJECT_KEY"), "new test", "trying again");
    }
}
