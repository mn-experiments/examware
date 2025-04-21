package examware.student.controller;

import examware.student.Student;
import examware.student.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    StudentDto create(@RequestBody StudentCreationRequest creationRequest) {
        return service.create(new Student(creationRequest)).asDto();
    }

    @PutMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    StudentDto update(@PathVariable String name, @RequestBody Map<String, Object> newInfo) {
        return service.update(name, newInfo).asDto();
    }

    @GetMapping("{name}")
    StudentDto read(@PathVariable String name) {
        return service.retrieve(name).asDto();
    }

    @GetMapping("all")
    List<StudentDto> readAll() {
        return service.retrieveAll().stream().map(Student::asDto).toList();
    }
}
