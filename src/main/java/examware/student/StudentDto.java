package examware.student;

public record StudentDto(
        String name,
        boolean hasPayedFee,
        int lessonCount
) {
}
