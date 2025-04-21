package examware.student;

import examware.student.controller.StudentCreationRequest;
import examware.student.controller.StudentDto;
import examware.test.EndpointTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentEndpointTest extends EndpointTest {

    @Test
    void canCreateStudent() {
        var john = new StudentCreationRequest("John", true, 2);

        var createdDto = givenAttemptToWrite().body(john)
                .when().post()
                .then().statusCode(201).extract().as(StudentDto.class);

        var retrievedDto = givenAttemptToRead()
                .when().get("John")
                .thenReturn().body().as(StudentDto.class);

        assertThat(retrievedDto).isEqualTo(new StudentDto(createdDto.id(), "John", true, 2));
    }

    @Test
    void canGetAllUsers() {
        var john = new StudentCreationRequest("John", true, 2);
        var bob = new StudentCreationRequest("Bob", true, 3);

        var johnDto = givenAttemptToWrite().body(john).post().as(StudentDto.class);
        var bobDto = givenAttemptToWrite().body(bob).post().as(StudentDto.class);

        var students = givenAttemptToRead()
                .when().get("all")
                .then().extract().jsonPath().getList(".", StudentDto.class);

        assertThat(students).hasSize(2);

        assertThat(students).containsExactlyInAnyOrder(johnDto, bobDto);
    }

    @Test
    void canUpdateStudent() {
        var john = new StudentCreationRequest("John", true, 2);

        var newInfo = Map.<String, Object>of("name", "John X", "hasPayedFee", false, "lessonCount", 3);

        var johnDto = givenAttemptToWrite().body(john).post().as(StudentDto.class);

        var updatedJohn = givenAttemptToWrite().body(newInfo).pathParam("name", johnDto.name())
                .when().put("{name}")
                .then().statusCode(200).extract().as(StudentDto.class);

        assertThat(updatedJohn).isEqualTo(new StudentDto(johnDto.id(), "John X", false, 3));

        givenAttemptToRead().pathParam("name", "John")
                .when().get("{name}")
                .then().statusCode(500);
    }

    @Override
    protected String basePath() {
        return "student";
    }
}
