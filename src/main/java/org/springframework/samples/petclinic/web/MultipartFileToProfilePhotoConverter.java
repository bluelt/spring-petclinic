package org.springframework.samples.petclinic.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.samples.petclinic.model.ProfilePhoto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author YongKwon Park
 */
public class MultipartFileToProfilePhotoConverter implements Converter<MultipartFile, ProfilePhoto> {

    @Override
    public ProfilePhoto convert(MultipartFile source) {
        if(source.isEmpty()) {
            return new ProfilePhoto(new byte[0]);
        }

        try {
            return new ProfilePhoto(source.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
