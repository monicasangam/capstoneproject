//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.04.06 at 04:52:58 PM EDT 
//


package edu.njit.qvx;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QvxFieldExtent.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QvxFieldExtent"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="QVX_FIX"/&gt;
 *     &lt;enumeration value="QVX_COUNTED"/&gt;
 *     &lt;enumeration value="QVX_ZERO_TERMINATED"/&gt;
 *     &lt;enumeration value="QVX_QV_SPECIAL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "QvxFieldExtent")
@XmlEnum
public enum QvxFieldExtent {

    QVX_FIX,
    QVX_COUNTED,
    QVX_ZERO_TERMINATED,
    QVX_QV_SPECIAL;

    public String value() {
        return name();
    }

    public static QvxFieldExtent fromValue(String v) {
        return valueOf(v);
    }

}
