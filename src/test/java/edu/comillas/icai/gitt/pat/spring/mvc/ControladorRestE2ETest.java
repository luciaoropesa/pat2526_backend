package edu.comillas.icai.gitt.pat.spring.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.web.client.TestRestTemplate;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Assertions;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
        "spring.task.scheduling.enabled=false", "spring.profiles.active=test"}
)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControladorRestE2ETest {
    @Autowired
    private  TestRestTemplate restTemplate;

    @Test
    public void contadorExistenteTest() {
        // Given ...
        String contador = "{\"nombre\":\"visitas\",\"valor\":0}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
                "/api/contadores", HttpMethod.POST,
                new HttpEntity<>(contador, headers), String.class);
        // When ...
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/contadores/visitas",
                HttpMethod.GET, HttpEntity.EMPTY, String.class);
        // Then ...
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(contador, response.getBody());
    }
    @Test
    public void contadorNoExistenteTest() {


        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/contadores/noexistente",
                String.class);


        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
