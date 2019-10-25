package repositories;

import domain.Student;
import validators.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentFileRepository extends InFileRepository<Integer, Student> {

    public StudentFileRepository(Validator<Student> validator, String file) {
        super(validator, file);
    }

    @Override
    Student parseLine(String x) {
        String[] parts=x.split(";");
        Student student  = new Student(parts[1],parts[2],Integer.parseInt(parts[3]),parts[4],parts[5]);
        student.setId(Integer.parseInt(parts[0]));
        return student;
    }

    @Override
    List<String> writeLine(Student s) {
        List<String> list=new ArrayList<String>();
        String line= new String(s.getId().toString()+";"+s.getSirName()+";"+s.getName()+";"+s.getGroup().toString()+";"+s.getEmail()+";"+s.getLaboratoryGuide());
        list.add(line);
        return list;
    }
}
