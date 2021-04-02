package foodiegram.devops.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

<<<<<<< HEAD
import static foodiegram.devops.rest.SystemResource.*;
=======
>>>>>>> 34ea68c14f6a60cbf35ba5a4bccbbd87557aef6a
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:test.properties")
class SystemResourceIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadBadge() {
        this.webTestClient
<<<<<<< HEAD
                .get().uri(SYSTEM + VERSION_BADGE)
=======
                .get().uri(SystemResource.SYSTEM + SystemResource.VERSION_BADGE)
>>>>>>> 34ea68c14f6a60cbf35ba5a4bccbbd87557aef6a
                .exchange()
                .expectStatus().isOk()
                .expectBody(byte[].class)
                .value(Assertions::assertNotNull)
                .value(svg -> assertTrue(new String(svg).startsWith("<svg")));
    }

    @Test
    void testReadInfo() {
        this.webTestClient
<<<<<<< HEAD
                .get().uri(SYSTEM + APP_INFO)
=======
                .get().uri(SystemResource.SYSTEM + SystemResource.APP_INFO)
>>>>>>> 34ea68c14f6a60cbf35ba5a4bccbbd87557aef6a
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(Assertions::assertNotNull)
                .value(body -> assertEquals(3, body.split("::").length));
    }
}
