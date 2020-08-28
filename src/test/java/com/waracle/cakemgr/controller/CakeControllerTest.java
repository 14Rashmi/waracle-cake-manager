package com.waracle.cakemgr.controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.CakeManagerApplication;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.service.CakeService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CakeManagerApplication.class)
@WebAppConfiguration
public class CakeControllerTest {

    protected MockMvc mvc;
    @Mock
    CakeService cakeService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void testGetCakes() throws Exception {
        String uri = "/cakes";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Cake[] cakes = mapFromJson(content, Cake[].class);
        assertTrue(cakes.length > 0);
    }

    @Test
    public void testPostCakes() throws Exception {
        String uri = "/cakes";
        Cake cake = new Cake("New Cake", "New Cake Description", "http://cakeImage.com");

        String inputJson = mapToJson(cake);
        MvcResult mvcResult = mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\n" +
                "        \"title\": \"New Cake\",\n" +
                "        \"desc\": \"New Cake Description\",\n" +
                "        \"image\": \"http://cakeImage.com\",\n" +
                "    }");

        // Adding duplicate cake
        mvcResult = mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
        content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Exception occurred while saving cake: New Cake");

    }

    @Test
    public void testLoadCakesForWebPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("cakes"))
                .andExpect(model().attributeExists("cakeModel"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddCakeForWebPage() throws Exception {
        Cake cake = new Cake("New Cake", "New Cake Description", "http://cakeImage.com");

        mvc.perform(post("/addCake")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("cakeModel", cake)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(3))
                .andExpect(model().attribute("message", "Cake added successfully"))
                .andExpect(model().attributeExists("cakes"))
                .andExpect(model().attributeExists("cakeModel"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

}
