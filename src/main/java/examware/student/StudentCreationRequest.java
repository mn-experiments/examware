package examware.student;

public record StudentCreationRequest(
        String name,
        boolean hasPayedFee,
        int lessonCount
) {
}
