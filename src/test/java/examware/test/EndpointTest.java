package examware.test;

import examware.attempt.AttemptRepo;
import examware.exam.ExamRepo;
import examware.student.StudentRepo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class EndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AttemptRepo attemptRepo;

    @Autowired
    private ExamRepo examRepo;

    private static Logger log = LoggerFactory.getLogger(EndpointTest.class);

    private static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:17");

    @BeforeAll
    static void setup() {
        db.start();
    }

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", db::getJdbcUrl);
        registry.add("spring.datasource.username", db::getUsername);
        registry.add("spring.datasource.password", db::getPassword);
    }

    @BeforeEach
    void smallSetup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @AfterEach
    void cleanup() {
        log.info("===== CLEANING UP DB BETWEEN TESTS =====");
        attemptRepo.deleteAll();
        studentRepo.deleteAll();
        examRepo.deleteAll();
        log.info("===== DONE CLEANING UP DB BETWEEN TESTS =====");
    }

    protected abstract String basePath();

    protected RequestSpecification givenAttemptToWrite() {
        return io.restassured.RestAssured.given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .basePath(basePath());
    }

    protected RequestSpecification givenAttemptToRead() {
        return io.restassured.RestAssured.given()
                .accept(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .basePath(basePath());
    }

}
