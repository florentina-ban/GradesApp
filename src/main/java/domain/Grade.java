package domain;

import utils.Constants;
import java.time.LocalDateTime;

public class Grade extends Entity<String> {
    private String professor;
    private float grade_given;
    private String feedback;
    private int deadline;

    private LocalDateTime date;

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

    public Grade(Integer studentId, String assignmentId, String professor, float grade, String feedback, LocalDateTime myDate) {
        this(professor, grade,feedback);
        this.setId(studentId.toString()+"_"+assignmentId);
        this.date=myDate;
    }

    public Float getGrade_given() {
        return grade_given;
    }

    public String getFeedback() {
        return feedback;
    }

    public Integer getDeadline() {
        return deadline;
    }

    private float calculatePentalties(){
        float penalties=0;
        String mydate = Constants.DATE_TIME_FORMATER.format(this.date);
        int week = UniversityYear.getInstance().getSemester().getWeek(mydate);
        if (deadline - week >= 0)
            penalties = 0;
        else if (deadline - week < 0 && deadline - week >= -2)
            penalties = week - deadline;
        else if (deadline - week < -2)
            penalties = 9;
        return penalties;
    }
    private float calculateFinalGrade() {
        float penalties=0,finalGrade;
        int week = UniversityYear.getInstance().getSemester().getWeek(Constants.DATE_TIME_FORMATER.format(this.date));
        if (deadline - week >= 0)
            penalties = 0;
        else if (deadline - week < 0 && deadline - week >= -2)
            penalties = week - deadline;
        else if (deadline - week < -2)
            penalties = 9;

        finalGrade = this.grade_given - penalties;
        if (finalGrade < 1)
            finalGrade = 1;

        return finalGrade;
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

    public Float getPenalties() {
        return this.calculatePentalties();
    }

    public String getAssignmentId() {
        return this.getId().split("_")[1];
    }

    public LocalDateTime getDate() {
        return date;
    }

    public float getFinalGrade() {
        return this.calculateFinalGrade();
    }

    public float getMaximumGrade() {
        return 10 - this.getPenalties();
    }

    public String getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "studentID: "+ getStudentId()+
                ", assignmentID: "+getAssignmentId()+
                ", professor: " + professor +
                ", grade_given: " + grade_given +
                ", deadline: " + deadline +
                ", date(weeks): " + UniversityYear.getInstance().getSemester().getWeek(Constants.DATE_TIME_FORMATER.format(this.date))+
                ", penalities: " + this.getPenalties()+
                ", finalGrade: " + this.getFinalGrade()+
                ", date: "+this.getDate()+
                '}';
    }
}
