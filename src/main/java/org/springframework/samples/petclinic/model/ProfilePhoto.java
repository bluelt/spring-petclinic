package org.springframework.samples.petclinic.model;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author YongKwon Park
 */
@Embeddable
public class ProfilePhoto {

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "profile_photo", length = 100000)
    private byte[] data;


    ProfilePhoto() { }

    public ProfilePhoto(byte[] data) {
        this.data = data;
    }

    public ProfilePhoto(InputStream stream) {
        try {
            this.data = ByteStreams.toByteArray(stream);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return BaseEncoding.base64().encode(data);
    }

}
