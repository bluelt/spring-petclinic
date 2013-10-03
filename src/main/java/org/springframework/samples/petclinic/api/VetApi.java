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
import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author YongKwon Park
 */
@Controller
@RequestMapping("/vets")
public class VetApi {

    private final ClinicService clinicService;

    @Autowired
    public VetApi(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping
    public ResponseEntity vets() {
        return new ResponseEntity(new Vets(clinicService.findVets()), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public ResponseEntity atomVets(HttpServletResponse response) {
        response.setContentType("blabla");

        return new ResponseEntity(new VetFeed(clinicService.findVets()), HttpStatus.OK);
    }


    @JacksonXmlRootElement(localName = "vets")
    class Vets {

        Collection<Vet> elements;
        Vets(Collection<Vet> elements) {
            this.elements = elements;
        }

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "vet")
        public Collection<Vet> getVets() {
            return elements;
        }

    }

    class VetFeed extends Feed {

        VetFeed(Collection<Vet> vets) {
            super("atom_1.0");

            List<Entry> entries = new ArrayList<>(vets.size());
            for(Vet vet : vets) {
                Entry entry = new Entry();
                entry.setId(String.format("tag:springsource.org,%s", vet.getId()));
                entry.setTitle(String.format("Vet: %s %s", vet.getFirstName(), vet.getLastName()));

                Content summary = new Content();
                summary.setValue(vet.getSpecialties().toString());
                entry.setSummary(summary);

                entries.add(entry);
            }

            setId("tag:springsource.org");
            setTitle("Veterinarians");
            setEntries(entries);
        }
    }

}
