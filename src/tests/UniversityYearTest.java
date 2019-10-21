package tests;

import domain.UniversityYear;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversityYearTest {
    @Test
    void testUniversityYear(){
        UniversityYear year=new UniversityYear("ir",2019);
        assertTrue(year.getSemester1().getBeginningDate().compareTo("30-09-2019")==0);
        assertTrue(year.getSemester1().getWeek("22-10-2019")==4);
        assertTrue(year.getSemester1().getBeginningDateOfWeek(4).compareTo("21-10-2019")==0);
        assertTrue(year.getSemester1().getSecondSemester().compareTo("24-02-2020")==0);
        assertTrue(year.getId().compareTo("ir")==0);
        assertTrue(year.getYear()==2019);
        assertTrue(year.getSemester2().getBeginningDate().compareTo("24-02-2020")==0);

    }

}