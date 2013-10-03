/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.api.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.api.exception.PetClinicApiException;
import org.springframework.samples.petclinic.api.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author YongKwon Park
 */
@ControllerAdvice
public class ApiErrorHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundException(Exception e) {
        return createResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PetClinicApiException.class)
    public ResponseEntity petClinicApiException(Exception e) {
        return createResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity all(Exception e) {
        return createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity createResponse(Exception e, HttpStatus status) {
        return new ResponseEntity(new ApiError(e.getMessage()), status);
    }

}
