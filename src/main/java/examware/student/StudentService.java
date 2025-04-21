package examware.student;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepo repo;

    public StudentService(StudentRepo repo) {
        this.repo = repo;
    }

    public Student create(Student newStudent) {
        return repo.save(newStudent);
    }

    public Student update(String name, Map<String, Object> newInfo) {
        var student = repo.findByName(name).orElseThrow(() -> new RuntimeException("not found"));

        student.updateWith(newInfo);

        return repo.save(student);
    }

    public Student retrieve(String name) {
        return repo.findByName(name).orElseThrow(() -> new RuntimeException("not found"));
    }

    public List<Student> retrieveAll() {
        return repo.findAll().stream().toList();
    }
}
