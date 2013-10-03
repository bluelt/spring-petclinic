package org.springframework.samples.petclinic;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author YongKwon Park
 */
public class TestEnvironmentInitializer extends EnvironmentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        addPropertySource(context.getEnvironment(), buildPropertySource());
    }

}
