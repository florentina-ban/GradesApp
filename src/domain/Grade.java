package domain;

import utils.Constants;
import java.time.LocalDateTime;

public class Grade extends Entity<String> {
    private String professor;
    private float grade_given;
    private String feedback;
    private int deadline;

    private LocalDateTime date;
    private float penalties;
    private float finalGrade;

    public Grade(Integer studentId, String assignmentId, String professor, float grade) {
        this.professor = professor;
        this.grade_given = grade;

        this.setId(studentId.toString() + '_' + assignmentId);
        this.date = LocalDateTime.now();
    }

    private void calculateFinalGrade() {
        String mydate = Constants.DATE_TIME_FORMATER.format(this.date);
        int week = UniversityYear.getInstance().getSemester().getWeek(mydate);
        if (deadline - week >= 0)
            this.penalties = 0;
        else if (deadline - week < 0 && deadline - week >= -2)
            this.penalties = week - deadline;
        else if (deadline - week < -2)
            this.penalties = 9;

        this.finalGrade = this.grade_given - this.penalties;
        if (this.finalGrade < 1)
            this.finalGrade = 1;
    }

    public Grade(Integer studentId, String assignmentId, String professor, float grade, String feedback) {
        this(studentId, assignmentId, professor, grade);
        this.feedback = feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public Integer getStudentId() {
        String idStudent = this.getId().split("_")[0];
        return Integer.parseInt(idStudent);
    }

    public String getAssignmentId() {
        return this.getId().split("_")[1];
    }

    public LocalDateTime getDate() {
        return date;
    }

    public float getFinalGrade() {
        if (finalGrade==0)
            this.calculateFinalGrade();
        return finalGrade;
    }

    public float getMaximumGrade() {
        return 10 - this.penalties;
    }

    public String getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "studentID= "+ getStudentId()+
                ", assignmentID= "+getAssignmentId()+
                ", professor= " + professor +
                ", grade_given= " + grade_given +
                ", feedback= " +  feedback +
                ", deadline= " + deadline +
                ", penalties= " + penalties +
                ", finalGrade= " + finalGrade +
                '}';
    }
}
