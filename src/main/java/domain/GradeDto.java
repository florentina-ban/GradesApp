package domain;

public class GradeDto {
    private String studentName;
    private String assignment;
    private float grade;
    private String professor;

    public GradeDto(String studentName, String assignment, float grade, String professor) {
        this.studentName = studentName;
        this.assignment = assignment;
        this.grade = grade;
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "student: '" + studentName + '\'' +
                ", assignment: '" + assignment + '\'' +
                ", grade: " + grade +
                ", professor: '" + professor + '\'';
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAssignment() {
        return assignment;
    }

    public float getGrade() {
        return grade;
    }

    public String getProfessor() {
        return professor;
    }
}
