package examware.attempt;

import examware.attempt.controller.AttemptCreationRequest;
import examware.exam.ExamRepo;
import examware.exam.ExamService;
import examware.student.StudentRepo;
import examware.student.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AttemptService {
    private final AttemptRepo repo;
    private final ExamService examService;
    private final StudentService studentService;
    private final AttemptValidator validator;

    public AttemptService(AttemptRepo repo,
                          ExamService examService,
                          StudentService studentService,
                          AttemptValidator validator) {
        this.repo = repo;
        this.examService = examService;
        this.studentService = studentService;
        this.validator = validator;
    }

    @Transactional
    public Attempt create(AttemptCreationRequest creationRequest) {
        var student = studentService.retrieve(creationRequest.studentId());
        var exam = examService.retrieve(creationRequest.examId());

        var attempt = new Attempt(student, exam, creationRequest);
        validator.validateCreation(attempt);

        return repo.save(attempt);
    }

    @Transactional
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
