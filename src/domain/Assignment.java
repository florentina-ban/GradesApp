package domain;

import utils.Constants;

import java.util.Calendar;

public class Assignment extends Entity<String> {
    private String description;
    private int startWeek;
    private int deadlineWeek;
    private UniversityYear year;
    private int semester;

    /**
     * constructor
     * @param id - string - assignment id
     * @param description - string - assignment text
     * @param deadlineWeek - int - number of the week (1-14)
     * @param year - universityYear - structure of the year when the assignment was given
     */
    public Assignment(String description, int deadlineWeek, UniversityYear year) {
        this.description = description;
        this.deadlineWeek = deadlineWeek;
        this.year = year;
        String s = Constants.DATE_FORMATTER.format(Calendar.getInstance().getTime());

        this.startWeek = year.getSemester1().getWeek(s);
        if (this.startWeek >= 1)
            this.semester = 1;
        else {
            this.startWeek = year.getSemester2().getWeek(s);
            this.semester = 2;
        }
    }

    @Override
    public String toString() {
        return "Assigment:  id='" + super.getId() +'\''+
                ", description='" + description + '\'' +
                ", startWeek=" + startWeek +
                ", deadlineWeek=" + deadlineWeek+
                ", semester=" + semester;
    }

    /**
     * @return - string - assignment id
     */
    public String getId() {
        return super.getId();
    }

    /**
     * @param startWeek - integer - the week when the assignment was given;
     */
    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * @return - string -assignment text
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return - int - the week number when the assignment was given
     */
    public int getStartWeek() {
        return startWeek;
    }

    /**
     * @return - int -the week number for the assignment deadline
     */
    public int getDeadlineWeek() {
        return deadlineWeek;
    }

    /**
     * @return - int - semester
     */
    public int getSemester() {
        return semester;
    }
}
