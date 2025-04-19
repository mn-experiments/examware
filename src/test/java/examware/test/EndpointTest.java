package examware.test;

import examware.exam.ExamRepo;
import examware.student.StudentRepo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    private ExamRepo examRepo;

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
        studentRepo.deleteAll();
        examRepo.deleteAll();
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
