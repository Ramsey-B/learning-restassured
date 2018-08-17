import Models.CharacterModel;
import org.testng.annotations.DataProvider;

public class DataProviderTest {
    @DataProvider(name = "CharacterData")
    public static Object[][] characters() {
        return new Object[][]{{1, new CharacterModel(
                "Luke Skywalker",
                "172",
                "77",
                "blond",
                "fair",
                "blue",
                "19BBY",
                "male"
        )}
                , {2, new CharacterModel(
                        "C-3PO",
                "167",
                "75",
                "n/a",
                "gold",
                "yellow",
                "112BBY",
                "n/a"
        )}};
    }
}
