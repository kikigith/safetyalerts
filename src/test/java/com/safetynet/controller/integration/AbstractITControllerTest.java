package com.safetynet.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractITControllerTest {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
