package examware.student.controller;

public record StudentCreationRequest(
        String name,
        boolean hasPayedFee,
        int lessonCount
) {
}
