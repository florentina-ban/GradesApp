package domain;

//import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UniversityYearTest {

    @Test
    void getYear() {
        UniversityYear year=UniversityYear.getInstance();
        assertTrue(year.getYear()==2019);
    }

    @Test
    void getSemester1() {
        UniversityYear year=UniversityYear.getInstance();
        assertTrue(year.getSemester1().getWeek("22-10-2019")==4);
    }

    @Test
    void getSemester2() {
        UniversityYear year=UniversityYear.getInstance();
        assertTrue(year.getSemester2().getBeginningDate().compareTo("24-02-2020")==0);

    }

    @Test
    void getSemester() {
        UniversityYear year=UniversityYear.getInstance();
        assertTrue(year.getSemester1()==year.getSemester());

    }
}