package examware.attempt;

import examware.concept.PersistedObject;
import examware.exam.Exam;
import examware.student.Student;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Map;

@Entity
public class Attempt extends PersistedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime attemptDate;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exam exam;

    private Attempt() {}

    public Attempt(Student student, Exam exam, AttemptCreationRequest creationRequest) {
        this.student = student;
        this.exam = exam;
        this.attemptDate = creationRequest.attemptDate();
    }

    @Override
    protected Long getId() {
        return id;
    }

    public AttemptDto asDto() {
        return new AttemptDto(id, attemptDate, score);
    }

    @Override
    public void updateWith(Map<String, Object> newInfo) {
        score = (Integer) newInfo.getOrDefault("score", score);
    }
}
