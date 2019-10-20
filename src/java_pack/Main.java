package java_pack;

import domain.Student;
import domain.UniversityYear;
import exceptions.ValidationException;
import repositories.InMemoryRepository;
import validators.Validator;
import validators.ValidatorFactory;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        Student s1=new Student(1234,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student(5242,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s3=new Student(1323,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s4=new Student(1343,"ion","bacu",221,"icd@scs.ubbclusdj.ro","popescu");


        Validator validator = ValidatorFactory.createValidator(Student.class);

        InMemoryRepository<Integer,Student> inMemoryRepository = new InMemoryRepository<>(validator);
        inMemoryRepository.save(s1);
        inMemoryRepository.save(s2);
        inMemoryRepository.save(s3);
        try{
            inMemoryRepository.save(s4);
        }catch (ValidationException e){
            System.out.println(e.getMessages());
        }

        for (Student st:inMemoryRepository.findAll()) {
            System.out.println(st);
        }
        inMemoryRepository.delete(1323);
        System.out.println();
        for (Student st:inMemoryRepository.findAll()) {
            System.out.println(st);
        }
        UniversityYear year=new UniversityYear("ir212",2019,"30-09-2019");

        System.out.println(year.getSemester1().getBeginningDate());
        System.out.println(year.getSemester1().getBeginningDateOfWeek(4));
        System.out.println(year.getSemester1().getBeginningDateOfWeek(8));
        System.out.println(year.getSemester1().getBeginningDateOfWeek(12));
        System.out.println(year.getSemester1().getBeginningDateOfWeek(13));
        System.out.println(year.getSemester1().getBeginningDateOfWeek(14));

        System.out.println("___________");
        System.out.println(year.getSemester2().getBeginningDate());
        System.out.println(1+" "+year.getSemester2().getBeginningDateOfWeek(1));
        System.out.println(2+" "+year.getSemester2().getBeginningDateOfWeek(2));
        System.out.println(3+" "+year.getSemester2().getBeginningDateOfWeek(3));
        System.out.println(4+" "+year.getSemester2().getBeginningDateOfWeek(4));
        System.out.println(5+" "+year.getSemester2().getBeginningDateOfWeek(5));
        System.out.println(6+" "+year.getSemester2().getBeginningDateOfWeek(6));
        System.out.println(7+" "+year.getSemester2().getBeginningDateOfWeek(7));
        System.out.println(8+" "+year.getSemester2().getBeginningDateOfWeek(8));

        System.out.println(9+" "+year.getSemester2().getBeginningDateOfWeek(9));
        System.out.println(year.getSemester2().getBeginningDateOfWeek(10));


    }
}
