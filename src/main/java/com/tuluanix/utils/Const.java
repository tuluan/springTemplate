package com.tuluanix.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Const {
    @Value("${schema.local}")
    public String schemaLocal;
}
