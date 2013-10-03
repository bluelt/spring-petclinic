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
package org.springframework.samples.petclinic.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.api.exception.ResourceNotFoundException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * @author YongKwon Park
 */
@Controller
@RequestMapping(value = "/owners")
public class OwnerApi {

    private final ClinicService clinicService;

    @Autowired
    public OwnerApi(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity processFindForm(Owner owner) {
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = this.clinicService.findOwnerByLastName(owner.getLastName());
        if (results.size() < 1) {
            // no owners found
            throw  new ResourceNotFoundException("no owners found");
        }
        if (results.size() > 1) {
            // multiple owners found
            return new ResponseEntity(new Owners(results), HttpStatus.OK);
        } else {
            // 1 owner found
            return new ResponseEntity(results.iterator().next(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "{ownerId}", method = RequestMethod.GET)
    public HttpEntity showOwner(@PathVariable("ownerId") int ownerId) {
        return new HttpEntity(this.clinicService.findOwnerById(ownerId));
    }


    @JacksonXmlRootElement(localName = "owners")
    class Owners {

        Collection<Owner> elements;
        Owners(Collection<Owner> elements) {
            this.elements = elements;
        }

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "owner")
        public Collection<Owner> getOwners() { return elements; }

    }

}
