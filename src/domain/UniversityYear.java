package domain;

import java.util.Calendar;

public class UniversityYear {
    private String id;
    private int year;
    private SemesterStructure semester1,semester2;

    public UniversityYear(String id, int year,String s) {
        this.id = id;
        this.year = year;
        semester1 = new SemesterStructure(s);
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
