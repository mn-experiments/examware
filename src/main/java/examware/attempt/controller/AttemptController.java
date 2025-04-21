package examware.attempt.controller;

import examware.attempt.Attempt;
import examware.attempt.AttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("attempt")
public class AttemptController {
    private final AttemptService service;

    public AttemptController(AttemptService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AttemptDto create(@RequestBody AttemptCreationRequest creationRequest) {
        return service.create(creationRequest).asDto();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    AttemptDto update(@PathVariable Long id, @RequestBody Map<String, Object> newInfo) {
        return service.update(id, newInfo).asDto();
    }

    @GetMapping("{id}")
    AttemptDto read(@PathVariable Long id) {
        return service.retrieve(id).asDto();
    }

    @GetMapping("all")
    List<AttemptDto> readAll() {
        return service.retrieveAll().stream().map(Attempt::asDto).toList();
    }
}
