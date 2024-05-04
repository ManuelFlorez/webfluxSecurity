package com.menu.app.infrastructure.receivers.web.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(SpringExtension.class)
class ValidationHandlerTest {

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    void processMapperTest() throws JsonProcessingException {
        ValidationHandler validationHandler = new ValidationHandler(objectMapper);

        when( objectMapper.writeValueAsBytes(any()) ).thenThrow(JsonProcessingException.class);

        StepVerifier.create(validationHandler.processMapper(null, null))
                .expectError()
                .verify();

    }

}
