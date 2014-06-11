package com.pik.moviecollection.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
            MvcResult data = mockMvc.perform(post("/movies/add").param("data", "{ \"title\": \"tytul\", \"country\": \"kraj\", \"year\": 2004, \"category\": { \"name\": \"kategoria\" } }\""))
                    .andExpect(status().isOk())
                    .andReturn();

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

    @Test
    public void listMovies() {

        try {

            MvcResult data = mockMvc.perform(get("/movies/list/1/1"))
                                    .andExpect(status().isOk())
                                    .andReturn();

            String json = data.getResponse().getContentAsString();

            ObjectMapper mapper = new ObjectMapper();

            List<Movie> wynik = mapper.readValue(json, List.class);

            assertEquals(1, wynik.size());

        }
    }
    private String addTestMovie() {

        Movie m = new Movie();

        m.setTitle("TestTytul");
        m.setCountry("Polska");
        m.setYear(2003);

        EntityManager connection = EntityConnection.getConnection();
        MovieManager movieManager = new MovieManagerImpl(connection);
        CategoryManager categoryManager = new CategoryManagerImpl(connection);

        return movieManager.addMovie(m);
    }

    @Test
    public void addAndRemoveTest() {

        String id = addTestMovie();

        try {
            mockMvc.perform(get("/movies/remove/" + id))
                    .andExpect(status().isOk())
                    .andExpect(content().string("{ \"ok\" }"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
