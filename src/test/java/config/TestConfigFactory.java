package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConfigFactory {
    private volatile Config config;
    private volatile WebConfig webConfig;
    private volatile DbConfig dbConfig;
    private volatile TestConfigPosts testConfigPosts;
    private volatile TestConfigComments testConfigComments;
    private volatile TestConfigErrors testConfigErrors;


    private TestConfigFactory() {
        config = ConfigFactory.systemProperties()
                .withFallback(ConfigFactory.systemEnvironment())
                .withFallback(ConfigFactory.parseResources("properties.conf"));
    }

    public synchronized WebConfig getWebConfig() {
        if (webConfig == null) {
            webConfig = ConfigBeanFactory.create(config.getConfig("web"), WebConfig.class);
        }
        return webConfig;
    }

    public synchronized DbConfig getDbConfig() {
        if (dbConfig == null) {
            dbConfig = ConfigBeanFactory.create(config.getConfig("db"), DbConfig.class);
        }
        return dbConfig;
    }

    public synchronized TestConfigPosts getTestConfigPosts() {
        if (testConfigPosts == null) {
            testConfigPosts = ConfigBeanFactory.create(config.getConfig("testDataPosts"), TestConfigPosts.class);
        }
        return testConfigPosts;
    }

    public synchronized TestConfigComments getTestConfigComments() {
        if (testConfigComments == null) {
            testConfigComments = ConfigBeanFactory.create(config.getConfig("testDataComments"), TestConfigComments.class);
        }
        return testConfigComments;
    }

    public synchronized TestConfigErrors getTestConfigErrors() {
        if (testConfigErrors == null) {
            testConfigErrors = ConfigBeanFactory.create(config.getConfig("testDataErrors"), TestConfigErrors.class);
        }
        return testConfigErrors;
    }

    public static synchronized TestConfigFactory getInstance() {
        return new TestConfigFactory();
    }
}