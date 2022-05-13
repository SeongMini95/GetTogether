package com.jsm.gettogether.domain.emailcertify.converter;

import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CertifyDivConverter implements AttributeConverter<CertifyDiv, String> {

    @Override
    public String convertToDatabaseColumn(CertifyDiv attribute) {
        return attribute.getCode();
    }

    @Override
    public CertifyDiv convertToEntityAttribute(String dbData) {
        return CertifyDiv.ofCode(dbData);
    }
}
