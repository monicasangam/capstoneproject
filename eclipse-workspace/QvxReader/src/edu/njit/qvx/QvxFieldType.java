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
 * <p>Java class for QvxFieldType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QvxFieldType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="QVX_SIGNED_INTEGER"/&gt;
 *     &lt;enumeration value="QVX_UNSIGNED_INTEGER"/&gt;
 *     &lt;enumeration value="QVX_IEEE_REAL"/&gt;
 *     &lt;enumeration value="QVX_PACKED_BCD"/&gt;
 *     &lt;enumeration value="QVX_BLOB"/&gt;
 *     &lt;enumeration value="QVX_TEXT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "QvxFieldType")
@XmlEnum
public enum QvxFieldType {

    QVX_SIGNED_INTEGER,
    QVX_UNSIGNED_INTEGER,
    QVX_IEEE_REAL,
    QVX_PACKED_BCD,
    QVX_BLOB,
    QVX_TEXT;

    public String value() {
        return name();
    }

    public static QvxFieldType fromValue(String v) {
        return valueOf(v);
    }

}
