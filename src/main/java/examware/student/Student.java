package examware.student;

import examware.concept.PersistedObject;
import examware.student.controller.StudentCreationRequest;
import examware.student.controller.StudentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Map;

@Entity
public class Student extends PersistedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private String name;
    private boolean hasPayedFee;
    private int lessonCount;

    // needed by hibernate
    Student() {
    }

    public Student(StudentCreationRequest creationRequest) {
        name = creationRequest.name();
        hasPayedFee = creationRequest.hasPayedFee();
        lessonCount = creationRequest.lessonCount();
    }

    @Override
    protected Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasPayedFee() {
        return hasPayedFee;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public StudentDto asDto() {
        return new StudentDto(id, name, hasPayedFee, lessonCount);
    }

    @Override
    public void updateWith(Map<String, Object> newInfo) {
        name = (String) newInfo.getOrDefault("name", name);
        hasPayedFee = (Boolean) newInfo.getOrDefault("hasPayedFee", hasPayedFee);
        lessonCount = (Integer) newInfo.getOrDefault("lessonCount", lessonCount);
    }
}
