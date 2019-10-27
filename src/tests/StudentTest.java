package tests;

import domain.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testGetters(){
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        assertTrue (student.getId()==12);
        assertTrue (student.getSirName().compareTo("Doe")==0);
        assertTrue (student.getName().compareTo("John")==0);
        assertTrue (student.getGroup()==323);
        assertTrue (student.getEmail().compareTo("jd@scs.ubbcluj.ro")==0);
        assertTrue (student.getLaboratoryGuide().compareTo("unknown")==0);
    }
    @Test
    void testSetters(){
        Student student=new Student("Doe","John",323,"jd@scs.ubbcluj.ro","unknown");
        student.setId(12);
        assertTrue (student.getId()==12);
        student.setId(13);
        assertTrue (student.getId()==13);
        student.setSirName("Smith");
        assertTrue (student.getSirName().compareTo("Smith")==0);
        student.setName("Pam");
        assertTrue (student.getName().compareTo("Pam")==0);
        student.setGroup(300);
        assertTrue(student.getGroup()==300);
        student.setEmail("bla@scs.ubbcluj.ro");
        assertTrue (student.getEmail().compareTo("bla@scs.ubbcluj.ro")==0);
        student.setLaboratoryGuide("Pop");
        assertTrue(student.getLaboratoryGuide().compareTo("Pop")==0);
    }

}