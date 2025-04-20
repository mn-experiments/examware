package examware.exam;

import examware.test.Assertions;
import examware.test.EndpointTest;
import org.junit.jupiter.api.Test;

public class ExamEndpointTest extends EndpointTest {

    private static long RANDOM_ID = 1;

    @Override
    protected String basePath() {
        return "exam";
    }

    @Test
    void canManipulateExam() {
        var request = new ExamCreationRequest("B");

        givenAttemptToWrite().body(request)
                .when().post()
                .then().statusCode(201);

        var dto = givenAttemptToRead().pathParam("name", request.name())
                .when().get("{name}")
                .then().statusCode(200).extract().jsonPath().getObject("", ExamDto.class);

        Assertions.assertThat(dto).hasSameDataAs(new ExamDto(RANDOM_ID, "B"));

        givenAttemptToWrite()
                .when().delete("B")
                .then().statusCode(204);

        givenAttemptToRead()
                .when().get("B")
                .then().statusCode(500);
    }

    @Test
    void canListExams() {
        givenAttemptToWrite().body(new ExamCreationRequest("B")).post();
        givenAttemptToWrite().body(new ExamCreationRequest("B197")).post();

        var exam1 = givenAttemptToRead().get("B").as(ExamDto.class);
        var exam2 = givenAttemptToRead().get("B197").as(ExamDto.class);

        var exams = givenAttemptToRead().get("all")
                .then().statusCode(200).and()
                .extract().jsonPath().getList("", ExamDto.class);

        Assertions.assertThat(exams).containsExactlyInAnyOrder(exam1, exam2);
    }
}
