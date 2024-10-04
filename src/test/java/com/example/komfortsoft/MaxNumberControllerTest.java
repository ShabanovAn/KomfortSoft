package com.example.komfortsoft;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MaxNumberControllerTest {

    @Autowired
    private  MockMvc mockMvc;


    @Test
    public void testFindMaxNumber() throws Exception {
        String filePath = "C:/path/test.xlsx";
        int n = 10;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/max-number")
                .param("filePath", filePath)
                .param("n", String.valueOf(n))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindMaxNumber_InvalidFilePath() throws Exception {
        String filePath = "invalidFilePath";
        int n = 10;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/max-number")
                        .param("filePath", filePath)
                        .param("n", String.valueOf(n))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof FileNotFoundException));
    }


    @Test
    public void testFindMaxNumber_InvalidN() throws Exception {
        String filePath = "C:/path/test.xlsx";
        int n = -1;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/max-number")
                        .param("filePath", filePath)
                        .param("n", String.valueOf(n))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(status().isBadRequest());
    }
}