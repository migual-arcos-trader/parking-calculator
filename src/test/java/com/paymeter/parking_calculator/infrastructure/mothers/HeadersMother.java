package com.paymeter.parking_calculator.infrastructure.mothers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public final class HeadersMother {

    private HeadersMother() {
        // Utility class
    }

    public static HttpHeaders createHttpHeadersApplicationJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
