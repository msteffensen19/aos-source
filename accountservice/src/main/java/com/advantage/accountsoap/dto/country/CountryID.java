//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2019.08.14 at 03:07:29 PM IDT
//


package com.advantage.accountsoap.dto.country;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CountryID.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CountryID"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Uganda,ug"/&gt;
 *     &lt;enumeration value="Uzbekistan,uz"/&gt;
 *     &lt;enumeration value="Austria,at"/&gt;
 *     &lt;enumeration value="Australia,au"/&gt;
 *     &lt;enumeration value="Ukraine,ua"/&gt;
 *     &lt;enumeration value="Uruguay,uy"/&gt;
 *     &lt;enumeration value="Azerbaijan,az"/&gt;
 *     &lt;enumeration value="United Arab Emirates,ae"/&gt;
 *     &lt;enumeration value="Italy,it"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */
@XmlType(name = "CountryID")
@XmlEnum
public enum CountryID {

    @XmlEnumValue("Uganda,ug")
    UGANDA_UG("Uganda,ug", 1L),
    @XmlEnumValue("Uzbekistan,uz")
    UZBEKISTAN_UZ("Uzbekistan,uz", 1L),
    @XmlEnumValue("Austria,at")
    AUSTRIA_AT("Austria,at", 1L),
    @XmlEnumValue("Australia,au")
    AUSTRALIA_AU("Australia,au", 1L),
    @XmlEnumValue("Ukraine,ua")
    UKRAINE_UA("Ukraine,ua", 1L),
    @XmlEnumValue("Uruguay,uy")
    URUGUAY_UY("Uruguay,uy", 1L),
    @XmlEnumValue("Azerbaijan,az")
    AZERBAIJAN_AZ("Azerbaijan,az", 1L),
    @XmlEnumValue("United Arab Emirates,ae")
    UNITED_ARAB_EMIRATES_AE("United Arab Emirates,ae", 1L),
    @XmlEnumValue("Italy,it")
    ITALY_IT("Italy,it", 1L),
    @XmlEnumValue("United States,usa")
    UNITED_STATES_USA("United States,usa", 1L);
    private final String value;

    CountryID(String v, long l) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CountryID fromValue(String v) {
        for (CountryID c: CountryID.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public  static Long getLong(){
        return 0L;
    }

}
