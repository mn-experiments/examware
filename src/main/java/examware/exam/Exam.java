package examware.exam;

import concept.PersistedObject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Exam extends PersistedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Exam() {}

    public Exam(ExamCreationRequest creationRequest) {
        name = creationRequest.name();
    }

    @Override
    protected Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ExamDto asDto() {
        return new ExamDto(name);
    }
}
