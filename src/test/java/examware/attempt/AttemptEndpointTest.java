package examware.attempt;

import examware.exam.ExamCreationRequest;
import examware.student.StudentCreationRequest;
import examware.test.EndpointTest;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AttemptEndpointTest extends EndpointTest {
    @Override
    protected String basePath() {
        return "attempt";
    }

    @Test
    void canCreateAttempt() {
        givenAttemptToWrite().body(new StudentCreationRequest("Bob", true, 1)).basePath("student").post();
        givenAttemptToWrite().body(new ExamCreationRequest("B")).basePath("exam").post();

        givenAttemptToWrite().body(new AttemptCreationRequest("Bob", "B"))
                .when().post()
                .then().statusCode(201);

        var attempts = givenAttemptToRead().get("all")
                .then().extract().jsonPath().getList("", AttemptDto.class);

        assertThat(attempts).hasSize(1);

        var attempt = attempts.get(0);

        assertThat(attempt).usingRecursiveComparison()
                .ignoringFields("attemptDate")
                .isEqualTo(new AttemptDto("Bob", "B", null, null));
    }

    @Test
    void canUpdateAttempt() {
        givenAttemptToWrite().body(new StudentCreationRequest("Bob", true, 1)).basePath("student").post();
        givenAttemptToWrite().body(new ExamCreationRequest("B")).basePath("exam").post();

        givenAttemptToWrite().body(new AttemptCreationRequest("Bob", "B")).post();

        givenAttemptToWrite().body(Map.<String, Object>of("score", 78))
                .put("1");

        var dto = givenAttemptToRead().get("1")
                .then().statusCode(200).extract().jsonPath().getObject("", AttemptDto.class);

        assertThat(dto)
                .usingRecursiveComparison().ignoringFields("attemptDate")
                .isEqualTo(new AttemptDto("Bob", "B", null, 78));
    }
}
