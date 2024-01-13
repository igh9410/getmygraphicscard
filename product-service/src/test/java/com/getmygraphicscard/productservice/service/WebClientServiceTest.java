package com.getmygraphicscard.productservice.service;

import com.getmygraphicscard.productservice.entity.Item;
import com.getmygraphicscard.productservice.entity.Root;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
public class WebClientServiceTest {

    @Autowired
    private WebClientServiceImpl webClientServiceImpl;

    private static MockWebServer mockWebServer;


    @BeforeAll
    static void setUpMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(6000);
    }

    @Test
    void requestGraphicsCardInfoTest() throws IOException {
        ClassPathResource naverResource = new ClassPathResource("NaverShop.json");
        String naverString = IOUtils.toString(naverResource.getInputStream(), StandardCharsets.UTF_8);
        Root TestRoot = webClientServiceImpl.requestGraphicsCardInfo("RTX 3060ti").block();
        List<Item> TestItems = TestRoot.getItems();
        StringBuilder sb = new StringBuilder();
        TestItems.forEach(item -> sb.append(item.toString()));
        System.out.println(sb.toString());
    }

    @Test
    void addGraphicsCardToDBTest() {
        String chipset = "RTX 3070";
        Mono<Root> graphicsCard = webClientServiceImpl.requestGraphicsCardInfo(chipset);

        webClientServiceImpl.addGraphicsCardToDB(graphicsCard, chipset);

    }

    @AfterAll
    static void removeWebServer() throws IOException {
        mockWebServer.shutdown();
    }


}
