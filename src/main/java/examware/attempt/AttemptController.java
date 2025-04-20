package examware.attempt;

import examware.exam.ExamRepo;
import examware.student.StudentRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("attempt")
public class AttemptController {
    private final AttemptRepo repo;
    private final StudentRepo studentRepo;
    private final ExamRepo examRepo;

    public AttemptController(AttemptRepo repo, StudentRepo studentRepo, ExamRepo examRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
        this.examRepo = examRepo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AttemptDto create(@RequestBody AttemptCreationRequest creationRequest) {
        var student = studentRepo.getReferenceById(creationRequest.studentId());
        var exam = examRepo.getReferenceById(creationRequest.examId());

        var attempt = new Attempt(student, exam, creationRequest);

        return repo.save(attempt).asDto();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    AttemptDto update(@PathVariable Long id, @RequestBody Map<String, Object> newInfo) {
        var attempt = repo.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        attempt.updateWith(newInfo);

        return repo.save(attempt).asDto();
    }

    @GetMapping("{id}")
    AttemptDto read(@PathVariable Long id) {
        return repo.findById(id).map(Attempt::asDto).orElseThrow(() -> new RuntimeException("not found"));
    }

    @GetMapping("all")
    List<AttemptDto> readAll() {
        return repo.findAll().stream().map(Attempt::asDto).toList();
    }
}
