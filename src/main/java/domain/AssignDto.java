package domain;

public class AssignDto {
    private String assignmentId;
    private Float sumOfGrades;
    private Integer noOfGrades;
    private Float grade;

    public AssignDto(String assignmentId) {
        this.sumOfGrades=0f;
        this.assignmentId = assignmentId;
        this.grade=0f;
        this.noOfGrades=0;
    }

    public void addGrade(float gr){
        sumOfGrades+=gr;
        noOfGrades++;
        if (noOfGrades>0)
            this.grade=sumOfGrades/noOfGrades;
        else
            this.grade=0f;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public Float getGrade() {
        return grade;
    }
}
