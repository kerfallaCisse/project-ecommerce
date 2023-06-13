package statistic_service;

import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Collections;
import java.util.Map;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class StatisticTestProfile {

    String database_url = ConfigProvider.getConfig().getValue("quarkus.datasource.jdbc.url", String.class);

    @Override
    public Map<String, String> getConfigOverrides() {
        return Collections.singletonMap("quarkus.datasource.jdbc.url",
                database_url);
    }

    @Override
    public String getConfigProfile() {
        return "stats-test-profile";
    }

}
