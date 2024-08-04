package com.backend.api.domain.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class DateUtilTest {

    @Test
    void testConvertLocalDateTimeToString() {
        LocalDateTime localDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        String actualConvertLocalDateTimeToStringResult = DateUtil.convertLocalDateTimeToString(localDateTime);
        assertEquals("1970-01-01T00:00:00Z", actualConvertLocalDateTimeToStringResult);
    }
}
