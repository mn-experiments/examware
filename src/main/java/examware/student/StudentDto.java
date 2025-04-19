package examware.student;

public record StudentDto(
        Long id,
        String name,
        boolean hasPayedFee,
        int lessonCount
) {
}
