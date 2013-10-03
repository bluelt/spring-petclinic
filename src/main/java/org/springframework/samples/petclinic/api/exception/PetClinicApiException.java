package org.springframework.samples.petclinic.api.exception;

/**
 * @author YongKwon Park
 */
public class PetClinicApiException extends RuntimeException {

    public PetClinicApiException(String message) {
        super(message);
    }

}
