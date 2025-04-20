package examware.attempt;

import examware.exam.ExamCreationRequest;
import examware.student.StudentCreationRequest;
import examware.test.Assertions;
import examware.test.EndpointTest;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

public class AttemptEndpointTest extends EndpointTest {

    public static final OffsetDateTime ATTEMPT_DATE = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);
    private static long RANDOM_ID = 1;

    @Override
    protected String basePath() {
        return "attempt";
    }

    @Test
    void canCreateAttempt() {
        givenAttemptToWrite().body(new StudentCreationRequest("Bob", true, 1)).basePath("student").post();
        givenAttemptToWrite().body(new ExamCreationRequest("B")).basePath("exam").post();

        var userId = givenAttemptToRead().basePath("student").get("Bob").then().extract().jsonPath().getLong("id");
        var examId = givenAttemptToRead().basePath("exam").get("B").then().extract().jsonPath().getLong("id");

        givenAttemptToWrite().body(new AttemptCreationRequest(userId, examId, ATTEMPT_DATE))
                .when().post()
                .then().statusCode(201);

        var attempts = givenAttemptToRead().get("all")
                .then().extract().jsonPath().getList("", AttemptDto.class);

        Assertions.assertThat(attempts).hasSize(1);

        var attempt = attempts.get(0);

        Assertions.assertThat(attempt).hasSameDataAs(new AttemptDto(RANDOM_ID, ATTEMPT_DATE, null));
    }

    @Test
    void canUpdateAttempt() {
        givenAttemptToWrite().body(new StudentCreationRequest("Bob", true, 1)).basePath("student").post();
        givenAttemptToWrite().body(new ExamCreationRequest("B")).basePath("exam").post();

        var userId = givenAttemptToRead().basePath("student").get("Bob").then().extract().jsonPath().getLong("id");
        var examId = givenAttemptToRead().basePath("exam").get("B").then().extract().jsonPath().getLong("id");


        givenAttemptToWrite().body(new AttemptCreationRequest(userId, examId, ATTEMPT_DATE)).post();
        var attemptId = givenAttemptToRead().get("all").then().extract().jsonPath().getString("[0].id");

        givenAttemptToWrite().body(Map.<String, Object>of("score", 78))
                .put(attemptId);

        var dto = givenAttemptToRead().get(attemptId)
                .then().statusCode(200).extract().jsonPath().getObject("", AttemptDto.class);

        Assertions.assertThat(dto).hasSameDataAs(new AttemptDto(1L, ATTEMPT_DATE, 78));
    }
}
