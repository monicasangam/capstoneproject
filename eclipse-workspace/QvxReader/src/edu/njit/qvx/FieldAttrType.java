//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.20 at 07:51:10 PM EST 
//


package edu.njit.qvx;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FieldAttrType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FieldAttrType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="UNKNOWN"/&gt;
 *     &lt;enumeration value="ASCII"/&gt;
 *     &lt;enumeration value="INTEGER"/&gt;
 *     &lt;enumeration value="REAL"/&gt;
 *     &lt;enumeration value="FIX"/&gt;
 *     &lt;enumeration value="MONEY"/&gt;
 *     &lt;enumeration value="DATE"/&gt;
 *     &lt;enumeration value="TIME"/&gt;
 *     &lt;enumeration value="TIMESTAMP"/&gt;
 *     &lt;enumeration value="INTERVAL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "FieldAttrType")
@XmlEnum
public enum FieldAttrType {

    UNKNOWN,
    ASCII,
    INTEGER,
    REAL,
    FIX,
    MONEY,
    DATE,
    TIME,
    TIMESTAMP,
    INTERVAL;

    public String value() {
        return name();
    }

    public static FieldAttrType fromValue(String v) {
        return valueOf(v);
    }

}
