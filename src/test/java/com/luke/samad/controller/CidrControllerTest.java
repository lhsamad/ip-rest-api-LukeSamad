package com.luke.samad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.samad.dto.CidrInputDto;
import com.luke.samad.service.CidrService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = CidrController.class)
public class CidrControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CidrService cidrService;

    @Test
    public void addCidr() throws Exception {
        mockMvc.perform(post("/cidr")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new CidrInputDto("10.0.0.1/24"))))
            .andExpect(status().isOk())
            .andExpect(content().string("10.0.0.1/24 added successfully"));

        verify(cidrService, times(1)).addCidr(any(String.class));
    }

    @Test
    public void getAllCidrs() throws Exception {
        mockMvc.perform(get("/cidrs")).andExpect(status().isOk());
        verify(cidrService, times(1)).getAllCidrs();
    }

    @Test
    public void getCidr() throws Exception {
        mockMvc.perform(get("/cidr").param("cidr", "10.0.0.1/24"))
            .andExpect(status().isOk());
        verify(cidrService, times(1)).getCidr(any(String.class));
    }

    @Test
    public void acquired() throws Exception {
        mockMvc.perform(get("/acquire/10.0.0.1"))
            .andExpect(status().isOk());
        verify(cidrService, times(1)).acquired(any(String.class));
    }

    @Test
    public void release() throws Exception {
        mockMvc.perform(get("/release/10.0.0.1"))
            .andExpect(status().isOk());
        verify(cidrService, times(1)).release(any(String.class));
    }
}
