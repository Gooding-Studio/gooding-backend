package com.dnd.gooding.integration;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Ignore
@Transactional
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInitializer.class)
public class IntegrationTest {

    static DockerComposeContainer rdbms;

    static {
        rdbms =
                new DockerComposeContainer(new File("infra/test/docker-compose.yml"))
                        .withExposedService(
                                "local-db-master",
                                3306,
                                Wait.forLogMessage(".*ready for connections.*", 1)
                                        .withStartupTimeout(Duration.ofSeconds(180L)))
                        .withExposedService(
                                "local-db-slave",
                                3306,
                                Wait.forLogMessage(".*ready for connections.*", 1)
                                        .withStartupTimeout(Duration.ofSeconds(180L)))
                        .withExposedService(
                                "local-db-migrate",
                                0,
                                Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
                                        .withStartupTimeout(Duration.ofSeconds(180L)));
        rdbms.start();
    }

    static class IntegrationTestInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Map<String, String> properties = new HashMap<>();
            String rdbmsMasterHost = rdbms.getServiceHost("local-db-master", 3306);
            Integer rdbmsMasterPort = rdbms.getServicePort("local-db-master", 3306);
            String rdbmsSlaveHost = rdbms.getServiceHost("local-db-slave", 3306);
            Integer rdbmsSlavePort = rdbms.getServicePort("local-db-slave", 3306);

            properties.put(
                    "spring.datasource.url",
                    "jdbc:mysql://" + rdbmsMasterHost + ":" + rdbmsMasterPort + "/gooding");

            properties.put(
                    "spring.datasource.slaves.slave1.url",
                    "jdbc:mysql://" + rdbmsSlaveHost + ":" + rdbmsSlavePort + "/gooding");

            TestPropertyValues.of(properties).applyTo(applicationContext);
        }
    }
}
