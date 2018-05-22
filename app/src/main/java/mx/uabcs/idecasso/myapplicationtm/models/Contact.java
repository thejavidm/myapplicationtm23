package mx.uabcs.idecasso.myapplicationtm.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.annotation.Ignore;

@Parcel
public class Contact {
    public Long _id;
    private String name;
    private String lastname;
    private String email;
    private String address;
    private String createdAt;
    private String modifiedAt;
    @Ignore
    private List<Phone> phones;

    public Contact() {
        phones = new ArrayList<>();
    }

    public Contact(String name, String lastname, String email, String address, String createdAt, String modifiedAt, List<Phone> phones) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
