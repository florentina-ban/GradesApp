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

    public Grade(String professor, float grade, String feedback) {
        this.professor = professor;
        this.grade_given = grade;
        this.feedback=feedback;
        this.date = LocalDateTime.now();
    }

    public Grade(String id,String professor,float grade,String feedback){
        this(professor,grade,feedback);
        this.setId(id);
    }

    public Grade(Integer studentId, String assignmentId, String professor, float grade, String feedback) {
        this(professor, grade,feedback);
        this.setId(studentId.toString()+"_"+assignmentId);
    }

    public float getGrade_given() {
        return grade_given;
    }

    public String getFeedback() {
        return feedback;
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

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
                ", deadline= " + deadline +
                ", date(weeks)" + UniversityYear.getInstance().getSemester().getWeek(Constants.DATE_TIME_FORMATER.format(this.date))+
                ", penalties= " + penalties +
                ", finalGrade= " + finalGrade +
                '}';
    }
}
