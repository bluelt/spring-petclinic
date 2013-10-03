package org.springframework.samples.petclinic.api.exception;

/**
 * @author YongKwon Park
 */
public class ResourceNotFoundException extends PetClinicApiException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
