package examware.student.controller;

public record StudentDto(
        long id,
        String name,
        boolean hasPayedFee,
        int lessonCount
) {
}
