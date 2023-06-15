package api.model;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Collections;
import java.util.Map;


public class ProfitTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Collections.singletonMap("quarkus.datasource.jdbc.url",
                "${MYSQL_URL}/test_Profit");
    }

    @Override
    public String getConfigProfile() {
        return "test-profile";
    }
}
