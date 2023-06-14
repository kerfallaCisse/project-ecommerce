package statistic_service;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Collections;
import java.util.Map;

public class StatisticTestProfile implements QuarkusTestProfile {



    @Override
    public Map<String, String> getConfigOverrides() {
        return Collections.singletonMap("quarkus.datasource.jdbc.url",
                "${MYSQL_URL}/test_backoffice");
    }

    @Override
    public String getConfigProfile() {
        return "stats-test-profile";
    }

}
