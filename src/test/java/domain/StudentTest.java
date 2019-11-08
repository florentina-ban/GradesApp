package domain;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @org.junit.jupiter.api.Test
    void getId() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        assertTrue (student.getId()==12);
    }

    @org.junit.jupiter.api.Test
    void getSirName() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        assertTrue (student.getSirName().compareTo("Doe")==0);
    }

    @org.junit.jupiter.api.Test
    void getName() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        assertTrue (student.getName().compareTo("John")==0);
    }

    @org.junit.jupiter.api.Test
    void getGroup() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        assertTrue (student.getGroup()==323);
    }

    @org.junit.jupiter.api.Test
    void getEmail() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        assertTrue (student.getEmail().compareTo("jd@scs.ubbcluj.ro")==0);
    }

    @org.junit.jupiter.api.Test
    void getLaboratoryGuide() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        assertTrue (student.getLaboratoryGuide().compareTo("unknown")==0);
    }

    @org.junit.jupiter.api.Test
    void setSirName() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        student.setSirName("Smith");
        assertTrue (student.getSirName().compareTo("Smith")==0);
    }

    @org.junit.jupiter.api.Test
    void setName() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        student.setName("Alan");
        assertTrue (student.getName().compareTo("Alan")==0);
    }

    @org.junit.jupiter.api.Test
    void setGroup() {
        Student student = new Student("Doe", "John", 323, "jd@scs.ubbcluj.ro", "unknown");
        student.setId(12);
        student.setGroup(500);
        assertTrue (student.getGroup()==500);
    }

}