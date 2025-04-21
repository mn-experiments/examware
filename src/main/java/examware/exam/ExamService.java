package examware.exam;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamService {
    private final ExamRepo repo;

    public ExamService(ExamRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public Exam create(Exam newExam) {
        return repo.save(newExam);
    }

    public Exam retrieve(String name) {
        return repo.findByName(name).orElseThrow(() -> new RuntimeException("not found"));
    }

    public List<Exam> retrieveAll() {
        return repo.findAll();
    }

    @Transactional
    public void delete(String name) {
        repo.deleteByName(name);
    }
}
