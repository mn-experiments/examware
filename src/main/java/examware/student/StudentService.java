package examware.student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepo repo;

    public StudentService(StudentRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public Student create(Student newStudent) {
        return repo.save(newStudent);
    }

    @Transactional
    public Student update(String name, Map<String, Object> newInfo) {
        var student = repo.findByName(name).orElseThrow(() -> new RuntimeException("not found"));

        student.updateWith(newInfo);

        return student;
    }

    public Student retrieve(String name) {
        return repo.findByName(name).orElseThrow(() -> new RuntimeException("not found"));
    }

    public Student retrieve(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    public List<Student> retrieveAll() {
        return repo.findAll().stream().toList();
    }
}
