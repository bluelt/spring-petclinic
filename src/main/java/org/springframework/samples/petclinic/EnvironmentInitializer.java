package org.springframework.samples.petclinic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author YongKwon Park
 */
public class EnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentInitializer.class);

    public static final String PETCLINIC_PROPERTYSOURCE = "petClinicPropertySource";

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment springEnvironment = context.getEnvironment();

        /*
            Default profile is "jpa" (see business-config.xml for more details).
            When using Spring JDBC, profile is 'jdbc'
            When using Spring Data JPA, profile is 'spring-data-jpa'-->
         */
        addActiveProfiles(springEnvironment, "jpa");
        addPropertySource(springEnvironment, buildPropertySource());
    }

    PropertySource<?> buildPropertySource() {
        try {
            List<Resource> resources = new ArrayList<>();
            resources.add(new ClassPathResource("spring/data-access.properties"));

            PropertiesFactoryBean bean = new PropertiesFactoryBean();
            bean.setLocations(resources.toArray(new Resource[resources.size()]));
            bean.setLocalOverride(true);
            bean.afterPropertiesSet();

            return new PropertiesPropertySource(PETCLINIC_PROPERTYSOURCE, bean.getObject());
        } catch (IOException e) {
            throw new ApplicationContextException("Unexpected exception on initialization: " + e.getMessage(), e);
        }
    }

    void addActiveProfiles(ConfigurableEnvironment environment, String...profiles) {
        LOGGER.debug("Activating profile '" + StringUtils.arrayToCommaDelimitedString(profiles) + "'");

        Set<String> merge = new LinkedHashSet<>();
        merge.addAll(Arrays.asList(environment.getActiveProfiles()));
        merge.addAll(Arrays.asList(profiles));

        environment.setActiveProfiles(merge.toArray(new String[merge.size()]));
    }

    void addPropertySource(ConfigurableEnvironment environment, PropertySource<?> source) {
        environment.getPropertySources().addLast(source);
    }

}
