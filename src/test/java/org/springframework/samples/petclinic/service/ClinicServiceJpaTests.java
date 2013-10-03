
package org.springframework.samples.petclinic.service;

import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.TestEnvironmentInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p> Integration test using the jpa profile. 
 * @see AbstractClinicServiceTests AbstractClinicServiceTests for more details. </p>
 *
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @author YongKwon Park
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/business-config.xml"}, initializers = TestEnvironmentInitializer.class)
@ActiveProfiles({"development", "jpa"})
public class ClinicServiceJpaTests extends AbstractClinicServiceTests {

}