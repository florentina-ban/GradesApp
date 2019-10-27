package domain;

import utils.Constants;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.util.Calendar;

public class UniversityYear {
    //private String id;
    private int year;
    private SemesterStructure semester1,semester2;

    private static UniversityYear instance=null;

    public static UniversityYear getInstance() {
        if (instance == null)
            instance = new UniversityYear();
        return instance;
    }
    private UniversityYear() {
        //this.id = id;
        int year= Calendar.getInstance().get(Calendar.YEAR);
        int month=Calendar.getInstance().get(Calendar.MONTH);
        if (month<6)
            this.year = year-1;
        else
            this.year = year;

        switch (this.year) {
            case 2019:
                semester1 = new SemesterStructure(Constants.FIRST_DAY2019);
                break;
            case 2020:
                semester1 = new SemesterStructure(Constants.FIRST_DAY2020);
                break;
            default:
                throw new RuntimeException("please update de first day of school folder");
        }
        semester2 = new SemesterStructure(semester1.getSecondSemester());
    }
/*
    public String getId() {
        return id;
    }
 */

    public int getYear() {
        return year;
    }

    public SemesterStructure getSemester1() {
        return semester1;
    }

    public SemesterStructure getSemester2() {
        return semester2;
    }

    public SemesterStructure getSemester(){
       Calendar currentCal=Calendar.getInstance();
       String sem2=this.semester2.getBeginningDate();
       Calendar sem2beginning=Week.transformStringToDate(sem2);
       if (currentCal.compareTo(sem2beginning)<0)
           return semester1;
       else
           return semester2;
            }
}
