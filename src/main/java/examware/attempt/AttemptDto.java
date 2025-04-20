package examware.attempt;

import java.time.OffsetDateTime;

public record AttemptDto(String student, String exam, OffsetDateTime attemptDate, Integer score) {
}
