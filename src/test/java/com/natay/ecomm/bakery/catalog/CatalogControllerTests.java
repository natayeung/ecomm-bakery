package com.natay.ecomm.bakery.catalog;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class CatalogControllerTests {

    private final String url = "http://localhost/";

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

    @Test
    public void shouldDisplayTitleOfProduct() throws Exception {
        HtmlPage htmlPage = webClient.getPage(url);

        List<DomElement> titles = htmlPage.getElementsById("title");
        assertThat(titles)
                .extracting(DomElement::getTextContent)
                .contains("Black Forest Cake", atIndex(0))
                .contains("Carrot Cake", atIndex(1))
                .contains("Rainbow Sprinkles Cake", atIndex(2));
    }

    @Test
    public void shouldDisplayPriceOfProduct() throws IOException {
        HtmlPage htmlPage = webClient.getPage(url);

        List<DomElement> prices = htmlPage.getElementsById("price");
        assertThat(prices)
                .extracting(DomElement::getTextContent)
                .contains("£ 27.95", atIndex(0))
                .contains("£ 21.95", atIndex(1))
                .contains("£ 24.95", atIndex(2));
    }
}