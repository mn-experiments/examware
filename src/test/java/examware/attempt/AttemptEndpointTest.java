package examware.attempt;

import examware.attempt.controller.AttemptCreationRequest;
import examware.attempt.controller.AttemptDto;
import examware.exam.controller.ExamCreationRequest;
import examware.exam.controller.ExamDto;
import examware.student.controller.StudentCreationRequest;
import examware.student.controller.StudentDto;
import examware.test.EndpointTest;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AttemptEndpointTest extends EndpointTest {

    public static final OffsetDateTime ATTEMPT_DATE = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);

    @Override
    protected String basePath() {
        return "attempt";
    }

    @Test
    void canCreateAttempt() {
        var student = givenAttemptToWrite()
                        .body(new StudentCreationRequest("Bob", true, 1))
                        .basePath("student")
                        .post().as(StudentDto.class);

        var exam = givenAttemptToWrite()
                .body(new ExamCreationRequest("B"))
                .basePath("exam")
                .post().as(ExamDto.class);

        var createdAttempt = givenAttemptToWrite()
                .body(new AttemptCreationRequest(student.id(), exam.id(), ATTEMPT_DATE))
                .when().post()
                .then().statusCode(201).extract().as(AttemptDto.class);

        var retrievedAttempt = givenAttemptToRead().get(Long.toString(createdAttempt.id()))
                .then().extract().as(AttemptDto.class);

        assertThat(retrievedAttempt).isEqualTo(new AttemptDto(createdAttempt.id(), ATTEMPT_DATE, null));
    }

    @Test
    void canUpdateAttempt() {
        var student = givenAttemptToWrite()
                        .body(new StudentCreationRequest("Bob", true, 1))
                        .basePath("student")
                        .post().as(StudentDto.class);

        var exam = givenAttemptToWrite()
                .body(new ExamCreationRequest("B"))
                .basePath("exam")
                .post().as(ExamDto.class);

        var attempt = givenAttemptToWrite()
                .body(new AttemptCreationRequest(student.id(), exam.id(), ATTEMPT_DATE)).post()
                .as(AttemptDto.class);

        var updatedAttempt = givenAttemptToWrite().body(Map.<String, Object>of("score", 78))
                .put(Long.toString(attempt.id()))
                .as(AttemptDto.class);

        assertThat(updatedAttempt).isEqualTo(new AttemptDto(attempt.id(), ATTEMPT_DATE, 78));
    }
}
