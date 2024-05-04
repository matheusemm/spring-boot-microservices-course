package com.sivalabs.bookstore.order.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.bookstore.order.AbstractIntegrationTest;
import com.sivalabs.bookstore.order.WithMockOAuth2User;
import org.junit.jupiter.api.Test;

class GetOrdersTest extends AbstractIntegrationTest {

    @Test
    @WithMockOAuth2User(username = "user")
    void shouldGetOrderSuccessfully() throws Exception {
        mockMvc.perform(get("/api/orders")).andExpect(status().isOk());
    }
}
