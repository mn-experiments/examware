package examware.attempt.controller;

import java.time.OffsetDateTime;

public record AttemptDto(long id, OffsetDateTime attemptDate, Integer score) {
}
