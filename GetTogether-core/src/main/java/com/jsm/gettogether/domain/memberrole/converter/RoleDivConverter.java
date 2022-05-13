package com.jsm.gettogether.domain.memberrole.converter;

import com.jsm.gettogether.domain.memberrole.enums.RoleDiv;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleDivConverter implements AttributeConverter<RoleDiv, String> {

    @Override
    public String convertToDatabaseColumn(RoleDiv attribute) {
        return attribute.getCode();
    }

    @Override
    public RoleDiv convertToEntityAttribute(String dbData) {
        return RoleDiv.ofCode(dbData);
    }
}
