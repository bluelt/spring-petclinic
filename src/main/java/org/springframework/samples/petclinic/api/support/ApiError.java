package org.springframework.samples.petclinic.api.support;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Calendar;
import java.util.Date;

/**
 * @author YongKwon Park
 */
@JacksonXmlRootElement(localName = "error")
public class ApiError {

    private String message;
    private Date timestamp;

    public ApiError(String message) {
        this.message = message;
        this.timestamp = Calendar.getInstance().getTime();
    }

    public String getMessage() { return message; }
    public Date getTimestamp() { return timestamp; }

}
