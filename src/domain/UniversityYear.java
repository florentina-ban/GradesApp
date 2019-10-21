package domain;

import utils.Constants;

import java.util.Calendar;

public class UniversityYear {
    private String id;
    private int year;
    private SemesterStructure semester1,semester2;

    public UniversityYear(String id, int year) {
        this.id = id;
        this.year = year;
        switch (year) {
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

    public String getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public SemesterStructure getSemester1() {
        return semester1;
    }

    public SemesterStructure getSemester2() {
        return semester2;
    }
}
