package domain;

import exceptions.DateException;
import utils.Constants;

import java.util.Calendar;

public class SemesterStructure {
    private Calendar beginningDate;
    private Week[] weeks;

    /**
     * constructor
     * @param s - string representing beginning of the semester in format yyyy-MM-dd
     */
    public SemesterStructure(String s) {
        String[] parts = s.split("-");

        this.beginningDate =Calendar.getInstance();
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day= Integer.parseInt(parts[0]);
        beginningDate.set(year,month,day);

        Calendar cal=Calendar.getInstance();
        cal.set(year,month,day);
        String aux= Constants.DATE_FORMATTER.format(cal.getTime());
        weeks = new Week[15];
        for (int i=1; i<=14; i++){
            weeks[i]=new Week(aux);
            aux=weeks[i].getEnd();
        }

    }

    /**
     *
     * @returns the date of the firs day of school as a string
     */
    public String getBeginningDate() {
        return Constants.DATE_FORMATTER.format(beginningDate.getTime());
    }

    /**
     *
     * @param n - int - nnumber of the week in the semester (1-14)
     * @return - string - the date when that week beginns
     * @throws DateException if n in not in the (1-14) interval
     */
    public String getBeginningDateOfWeek(int n){
        if (n<1 || n>14)
            throw new DateException("n must be a number between 1 and 14");

        return weeks[n].getBeginning();

    }

    /**
     * @param s - string - a date by the format yyyy-MM-dd
     * @return - int - the number of the week for that date, or -1 - if the date is not in the semester
     */
    public int getWeek(String s){
        int week;
        String[] parts = s.split("-");
        int year=Integer.parseInt(parts[2]);
        int month=Integer.parseInt(parts[1])-1;
        int day = Integer.parseInt(parts[0]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,day);

        for (week=1; week<14; week++){
            if (cal.compareTo(Week.transformStringToDate(weeks[week].getBeginning()))>=0 && cal.compareTo(Week.transformStringToDate(weeks[week].getEnd()))<0 )
                return week;
        }
        return -1;
    }

    /**
     * using de beginning of the university year it calculates the beginnning of the second semester
     * @return - string - representing the date of the beginning of the second semester (int format yyyy-MM-dd)
     */
    public String getSecondSemester(){
        String s = getBeginningDateOfWeek(14);
        String[] parts = s.split("-");

        Calendar aux =Calendar.getInstance();
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day= Integer.parseInt(parts[0]);
        aux.set(year,month,day);
        aux.add(Calendar.WEEK_OF_MONTH,6);
        return Constants.DATE_FORMATTER.format(aux.getTime());
    }
}
