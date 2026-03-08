package ru.rsreu.sea_fishing.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class HomePageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePageIsAvailable() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    public void testAccessNonExistentPage() throws Exception {
        mockMvc.perform(get("/some-fake-page-123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testHomePageHasBookingButton() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().string(containsString("ЗАПИСАТЬСЯ НА ЭКСКУРСИЮ")))
                .andExpect(content().string(containsString("href=\"/booking\"")));
    }

}