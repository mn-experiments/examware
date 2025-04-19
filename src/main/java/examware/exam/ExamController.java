package examware.exam;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exam")
public class ExamController {
    private final ExamRepo repo;

    public ExamController(ExamRepo repo) {
        this.repo = repo;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody ExamCreationRequest creationRequest) {
        var exam = new Exam(creationRequest);

        repo.save(exam);
    }

    @GetMapping("{name}")
    ExamDto read(@PathVariable String name) {
        return repo.findByName(name).map(Exam::asDto).orElseThrow(() -> new RuntimeException("not found"));
    }

    @GetMapping("all")
    List<ExamDto> readAll() {
        return repo.findAll().stream().map(Exam::asDto).toList();
    }

    @DeleteMapping("{name}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable String name) {
        repo.deleteByName(name);
    }
}
