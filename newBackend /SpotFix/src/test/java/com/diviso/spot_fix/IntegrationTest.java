package com.diviso.spot_fix;

import com.diviso.spot_fix.config.AsyncSyncConfiguration;
import com.diviso.spot_fix.config.DatabaseTestcontainer;
import com.diviso.spot_fix.config.JacksonConfiguration;
import com.diviso.spot_fix.config.RedisTestContainer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        SpotFixApp.class,
        JacksonConfiguration.class,
        AsyncSyncConfiguration.class,
        com.diviso.spot_fix.config.JacksonHibernateConfiguration.class,
    }
)
@ImportTestcontainers({ DatabaseTestcontainer.class, RedisTestContainer.class })
public @interface IntegrationTest {}
