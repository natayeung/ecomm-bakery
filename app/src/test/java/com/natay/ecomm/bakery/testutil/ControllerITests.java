package com.natay.ecomm.bakery.testutil;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

/**
 * @author natayeung
 */
@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@EnableAutoConfiguration
public abstract class ControllerITests {

    public static final String LOCALHOST = "http://localhost/";

    @Autowired
    private MockMvc mockMvc;
    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc).build();
    }

    @AfterEach
    public void tearDown() {
        webClient.close();
    }

    public MockMvc mockMvc() {
        return mockMvc;
    }

    public WebClient webClient() {
        return webClient;
    }
}
