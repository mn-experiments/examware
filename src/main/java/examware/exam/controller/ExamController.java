package examware.exam.controller;

import examware.exam.Exam;
import examware.exam.ExamService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exam")
public class ExamController {
    private final ExamService service;

    public ExamController(ExamService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    ExamDto create(@RequestBody ExamCreationRequest creationRequest) {
        return service.create(new Exam(creationRequest)).asDto();
    }

    @GetMapping("{name}")
    ExamDto read(@PathVariable String name) {
        return service.retrieve(name).asDto();
    }

    @GetMapping("all")
    List<ExamDto> readAll() {
        return service.retrieveAll().stream().map(Exam::asDto).toList();
    }

    @DeleteMapping("{name}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable String name) {
        service.delete(name);
    }
}
