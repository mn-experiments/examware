package examware.attempt;

import org.springframework.stereotype.Service;

@Service
public class AttemptValidator {
    public void validateCreation(Attempt attempt) {
        if (!attempt.getStudent().hasPayedFee()) {
            throw new RuntimeException("student has not payed the fee");
        }
        if (attempt.getStudent().getLessonCount() < 10) {
            throw new RuntimeException("student has not attended enough lessons");
        }
    }
}
