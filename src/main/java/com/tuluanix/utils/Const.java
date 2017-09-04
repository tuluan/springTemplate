package com.tuluanix.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Const {
    public static String SCHEMA_LOCAL;

    @Value("${schema.local}")
    public void setSchemaLocal(String schemaLocal) {
        SCHEMA_LOCAL = schemaLocal;
    }
}
