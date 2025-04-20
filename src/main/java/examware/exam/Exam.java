package examware.exam;

import examware.concept.PersistedObject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Map;

@Entity
public class Exam extends PersistedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // needed by hibernate
    Exam() {}

    public Exam(ExamCreationRequest creationRequest) {
        name = creationRequest.name();
    }

    @Override
    protected Long getId() {
        return id;
    }

    @Override
    public void updateWith(Map<String, Object> newInfo) {

    }

    public String getName() {
        return name;
    }

    public ExamDto asDto() {
        return new ExamDto(name);
    }
}
