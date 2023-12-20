package helpers;

import config.TestConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DbUtils {
    protected static TestConfigFactory conf = TestConfigFactory.getInstance();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(conf.getDbConfig().getDbUrl(),
                    conf.getDbConfig().getDbUsername(),
                    conf.getDbConfig().getDbPassword());
        } catch (SQLException e) {
            log.error("CONNECTION IS NOT SET -> "+conf.getDbConfig().getDbUrl());
            e.printStackTrace();
        }
        return connection;
    }
}
