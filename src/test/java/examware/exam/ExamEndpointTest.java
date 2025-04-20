package examware.exam;

import examware.test.EndpointTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExamEndpointTest extends EndpointTest {

    @Override
    protected String basePath() {
        return "exam";
    }

    @Test
    void canManipulateExam() {
        var request = new ExamCreationRequest("B");

        var examDto = givenAttemptToWrite().body(request)
                .when().post()
                .then().statusCode(201).extract().as(ExamDto.class);

        var dto = givenAttemptToRead().pathParam("name", request.name())
                .when().get("{name}")
                .then().statusCode(200).extract().jsonPath().getObject("", ExamDto.class);

        assertThat(dto).isEqualTo(examDto);

        givenAttemptToWrite()
                .when().delete("B")
                .then().statusCode(204);

        givenAttemptToRead()
                .when().get("B")
                .then().statusCode(500);
    }

    @Test
    void canListExams() {
        var exam1 = givenAttemptToWrite().body(new ExamCreationRequest("B")).post().as(ExamDto.class);
        var exam2 = givenAttemptToWrite().body(new ExamCreationRequest("B197")).post().as(ExamDto.class);

        var exams = givenAttemptToRead().get("all")
                .then().statusCode(200).and()
                .extract().jsonPath().getList("", ExamDto.class);

        assertThat(exams).containsExactlyInAnyOrder(exam1, exam2);
    }
}
