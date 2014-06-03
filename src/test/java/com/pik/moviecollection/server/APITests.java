package com.pik.moviecollection.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by losiowaty on 03.06.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:rest-service-servlet.xml")
public class APITests {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void addMovieTest() {
        try {
            mockMvc.perform(post("/movies/add").param("data", "{ \"title\": \"tytul\", \"country\": \"kraj\", \"category\": { \"name\": \"kategoria\" } }\""))
                    .andExpect(status().isOk())
                    .andExpect(content().string("{ \"ok\" }"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addBadMovieTest() {
        try {
            mockMvc.perform(post("/movies/add").param("data", "{\"badtitle\" : \"s\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("{ \"error\" }"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
