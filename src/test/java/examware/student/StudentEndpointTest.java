package examware.student;

import examware.test.Assertions;
import examware.test.EndpointTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StudentEndpointTest extends EndpointTest {

    private static long RANDOM_ID = 1;

    @Test
    void canCreateStudent() {
        var john = new StudentCreationRequest("John", true, 2);

        givenAttemptToWrite().body(john)
                .when().post()
                .then().statusCode(201);

        var student = givenAttemptToRead()
                .when().get("John")
                .thenReturn().body().as(StudentDto.class);

        Assertions.assertThat(student).hasSameDataAs(new StudentDto(RANDOM_ID, "John", true, 2));
    }

    @Test
    void canGetAllUsers() {
        var john = new StudentCreationRequest("John", true, 2);
        var bob = new StudentCreationRequest("Bob", true, 3);


        givenAttemptToWrite().body(john).post();
        givenAttemptToWrite().body(bob).post();

        var johnDto = givenAttemptToRead().get(john.name()).as(StudentDto.class);
        var bobDto = givenAttemptToRead().get(bob.name()).as(StudentDto.class);

        var students = givenAttemptToRead()
                .when().get("all")
                .then().extract().jsonPath().getList(".", StudentDto.class);

        Assertions.assertThat(students).hasSize(2);

        Assertions.assertThat(students).containsExactlyInAnyOrder(johnDto, bobDto);
    }

    @Test
    void canUpdateStudent() {
        var john = new StudentCreationRequest("John", true, 2);

        var newInfo = Map.<String, Object>of("name", "John X", "hasPayedFee", false, "lessonCount", 3);

        givenAttemptToWrite().body(john).post();

        givenAttemptToWrite().body(newInfo).pathParam("name", john.name())
                .when().put("{name}")
                .then().statusCode(204);

        var updatedJohn = givenAttemptToRead().pathParam("name", "John X")
                .when().get("{name}")
                .then().extract().as(StudentDto.class);

        Assertions.assertThat(updatedJohn).hasSameDataAs(new StudentDto(RANDOM_ID, "John X", false, 3));

        givenAttemptToRead().pathParam("name", "John")
                .when().get("{name}")
                .then().statusCode(500);
    }

    @Override
    protected String basePath() {
        return "student";
    }
}
