package java_pack;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import repositories.AssignmentFileRepository;
import repositories.InMemoryRepository;
import repositories.StudentFileRepository;
import service.Service;
import ui.Console;
import validators.Validator;
import validators.ValidatorFactory;

/*
university year trebuie scos din assinment - eventual un singleton?
la update trebuie sa verific la update daca data curenta e mai mica decat data noua de deadline
 */
public class Main {

    public static void main(String[] args) {

        Validator studentVal = ValidatorFactory.createValidator(Student.class);
        Validator assignmentVal = ValidatorFactory.createValidator(Assignment.class);
        Validator gradeVal = ValidatorFactory.createValidator(Grade.class);

        StudentFileRepository studentFileRepository = new StudentFileRepository(studentVal, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\data\\studenti.txt");
        AssignmentFileRepository assignmentFileRepository = new AssignmentFileRepository(assignmentVal,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\data\\assignments.txt");
        InMemoryRepository<String,Grade> gradesRepo = new InMemoryRepository<>(gradeVal);

        Service service=new Service(studentFileRepository,assignmentFileRepository,gradesRepo);
        Console console=new Console(service);
        console.execute();

    }
}
