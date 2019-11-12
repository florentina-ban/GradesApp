package domain;

import exceptions.DateException;
import utils.Constants;

import java.util.Calendar;

public class Assignment extends Entity<String> {
    private String description;
    private int startWeek;
    private int deadlineWeek;
    private int semester;

    /**
     * constructor
     * @param description - string - assignment text
     * @param deadlineWeek - int - number of the week (1-14)
     */
    public Assignment(String description, int deadlineWeek) {
        this.description = description;
        this.deadlineWeek = deadlineWeek;
        String s = Constants.DATE_FORMATTER.format(Calendar.getInstance().getTime());

        this.startWeek = UniversityYear.getInstance().getSemester1().getWeek(s);
        if (this.startWeek != 1)
            this.semester = 1;
        else {
            this.startWeek = UniversityYear.getInstance().getSemester2().getWeek(s);
            if(this.startWeek!=-1)
                this.semester = 2;
            else
                throw new DateException("cannot add assignment during holidays");
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
    public Integer getStartWeek() {
        return startWeek;
    }

    /**
     * @return - int -the week number for the assignment deadline
     */
    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    /**
     * @return - int - semester
     */
    public int getSemester() {
        return semester;
    }
}
