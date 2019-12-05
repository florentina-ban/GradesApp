package domain;

public class GradeDto {
    private String studentName;
    private Integer studentId;
    private String assignment;
    private float grade;
    private String professor;

    public GradeDto(String studentName, String assignment, float grade, String professor,int studentId) {
        this.studentName = studentName;
        this.studentId=studentId;
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

    public Integer getStudentId() {
        return studentId;
    }
    public String getGradeId(){
        String s=studentId.toString()+'_'+assignment;
        return s;
    }
}
