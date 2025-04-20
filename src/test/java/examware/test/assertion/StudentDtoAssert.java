package examware.test.assertion;

import examware.student.StudentDto;

public class StudentDtoAssert extends DtoAssert<StudentDto> {
    public StudentDtoAssert(StudentDto studentDto) {
        super(studentDto, StudentDtoAssert.class);
    }
//
//    /**
//     * Compares the data of the DTOs ignoring the ID field
//     */
//    public StudentDtoAssert hasSameData(StudentDto other) {
//        usingRecursiveComparison()
//                .ignoringFields("id")
//                .isEqualTo(other);
//
//        return this;
//    }

}
