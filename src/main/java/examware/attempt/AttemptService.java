package examware.attempt;

import examware.attempt.controller.AttemptCreationRequest;
import examware.exam.ExamRepo;
import examware.student.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AttemptService {
    private final AttemptRepo repo;
    private final ExamRepo examRepo;
    private final StudentRepo studentRepo;

    public AttemptService(AttemptRepo repo, ExamRepo examRepo, StudentRepo studentRepo) {
        this.repo = repo;
        this.examRepo = examRepo;
        this.studentRepo = studentRepo;
    }

    public Attempt create(AttemptCreationRequest creationRequest) {
        var student = studentRepo.getReferenceById(creationRequest.studentId());
        var exam = examRepo.getReferenceById(creationRequest.examId());

        var attempt = new Attempt(student, exam, creationRequest);

        return repo.save(attempt);
    }

    public Attempt update(Long id, Map<String, Object> newInfo) {
        var attempt = repo.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        attempt.updateWith(newInfo);

        return repo.save(attempt);
    }

    public Attempt retrieve(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    public List<Attempt> retrieveAll() {
        return repo.findAll();
    }
}
