package examware.student;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: gotta refactor the assertions or the DTOs, excluding the ID each time is not ergonomic.
//  HTTP calls can be refactored into a superclass.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepo studentRepo;

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
        RestAssured.basePath = "student";
    }

    @AfterEach
    void cleanup() {
        studentRepo.deleteAll();
    }

    @Test
    void canCreateStudent() {
        var john = new StudentCreationRequest("John", true, 2);

        RestAssured.given()
                .body(john)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().statusCode(201);

        var student = RestAssured
                .given().accept(ContentType.JSON)
                .when().get("John")
                .thenReturn().body().as(StudentDto.class);

        assertThat(student)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new StudentDto(123L, "John", true, 2));
//        assertEquals(1, student.id());
//        assertEquals("John", student.name());
//        assertTrue(student.hasPayedFee());
//        assertEquals(2, student.lessonCount());
    }

    @Test
    void canGetAllUsers() {
        var john = new StudentCreationRequest("John", true, 2);
        var bob = new StudentCreationRequest("Bob", true, 3);

        var johnDto = new StudentDto(1L, "John", true, 2);
        var bobDto = new StudentDto(2L, "Bob", true, 3);

        RestAssured.given()
                .body(john)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().statusCode(201);

        RestAssured.given()
                .body(bob)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().statusCode(201);

        var students = RestAssured
                .given().accept(ContentType.JSON)
                .when().get("all")
                .then().extract().jsonPath().getList(".", StudentDto.class);

        assertThat(students).hasSize(2);

        assertThat(students).allSatisfy(
                it -> assertThat(it)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isIn(johnDto, bobDto));
    }

    @Test
    void canUpdateStudent() {
        var john = new StudentCreationRequest("John", true, 2);

        var newInfo = Map.<String, Object>of("name", "John X", "hasPayedFee", false, "lessonCount", 3);

        RestAssured.given()
                .body(john)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().statusCode(201);

        RestAssured.given()
                .body(newInfo)
                .contentType(ContentType.JSON)
                .pathParam("name", john.name())
                .when().put("{name}")
                .then().statusCode(200);

        var updatedJohn = RestAssured
                .given()
                .pathParam("name", "John X")
                .when()
                .get("{name}")
                .then().extract().as(StudentDto.class);

        assertThat(updatedJohn)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new StudentDto(123L, "John X", false, 3));

        RestAssured
                .given()
                .pathParam("name", "John")
                .when()
                .get("{name}")
                .then().statusCode(500);
    }

}
