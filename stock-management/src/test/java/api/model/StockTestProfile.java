package api.model;

import java.util.Collections;
import java.util.Map;

import io.quarkus.test.junit.QuarkusTestProfile;

public class StockTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Collections.singletonMap("quarkus.datasource.jdbc.url",
                "${MYSQL_URL}/test_StockManagement");
    }

    @Override
    public String getConfigProfile() {
        return "test-profile";
    }
}
