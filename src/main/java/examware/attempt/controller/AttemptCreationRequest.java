package examware.attempt.controller;

import java.time.OffsetDateTime;

public record AttemptCreationRequest(Long studentId, Long examId, OffsetDateTime attemptDate) {
}
