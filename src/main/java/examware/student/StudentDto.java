package examware.student;

public record StudentDto(
        long id,
        String name,
        boolean hasPayedFee,
        int lessonCount
) {
}
