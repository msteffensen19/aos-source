package com.advantage.online.store.user.model;

import javax.persistence.*;

/**
 * @author Binyamin Regev on 15/11/2015.
 */
@Entity
@Table(name = "COUNTRY")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COUNTRY_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="ISO_NAME")
    private String isoName;

    @Column(name="PHONE_PREFIX")
    private int phonePrefix;

    public Country(String name, int phonePrefix) {
        this.setName(name);
        this.setPhonePrefix(phonePrefix);
    }
    public Country(String name, String isoName) {
        this.setName(name);
        this.setIsoName(isoName);
    }
    public Country(String name, String isoName, int phonePrefix) {
        this.setName(name);
        this.setIsoName(isoName);
        this.setPhonePrefix(phonePrefix);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    public int getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(int phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    @Override
    public String toString() {
        return "Country Information: " +
                "name=\"" + this.getName() + "\" " +
                "ISO name=\"" + this.getIsoName() + "\" " +
                "international code=" + this.getPhonePrefix()
                ;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
