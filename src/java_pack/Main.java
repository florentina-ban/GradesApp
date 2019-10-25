package java_pack;

import domain.Assignment;
import domain.Student;
import domain.UniversityYear;
import javafx.animation.ScaleTransition;
import repositories.AssignmentFileRepository;
import repositories.InMemoryRepository;
import repositories.StudentFileRepository;
import ui.Console;
import validators.Validator;
import validators.ValidatorFactory;

import javax.swing.*;
import java.util.Calendar;

/*
university year trebuie scos din assinment - eventual un singleton?
la update trebuie sa verific la update daca data curenta e mai mica decat data noua de deadline
 */
public class Main {

    public static void main(String[] args) {

        Validator validator = ValidatorFactory.createValidator(Student.class);
        Validator asVal= ValidatorFactory.createValidator(Assignment.class);

        StudentFileRepository studentFileRepository = new StudentFileRepository(validator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\data\\studenti.txt");
        AssignmentFileRepository assignmentFileRepository = new AssignmentFileRepository(asVal,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\data\\assignments.txt");
        Console console=new Console(studentFileRepository,assignmentFileRepository);
        console.execute();

    }
}
