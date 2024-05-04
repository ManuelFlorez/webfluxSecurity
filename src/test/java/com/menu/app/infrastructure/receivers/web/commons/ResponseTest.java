package com.menu.app.infrastructure.receivers.web.commons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponseTest {

    static class ResponseImpl implements Response<String> { }

    @Test
    void buildError() {
        Response<String> response = new ResponseImpl();
        response.error().accept(new Exception("Failed"));
        assertNotNull(response);
    }
}
