package examware.attempt;

import java.time.OffsetDateTime;

public record AttemptDto(long id, OffsetDateTime attemptDate, Integer score) {
}
