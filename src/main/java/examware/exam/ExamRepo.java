package examware.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepo extends JpaRepository<Exam, Long> {
    Optional<Exam> findByName(String name);
    void deleteByName(String name);
}
