package java_pack;

import domain.Assignment;
import domain.Student;
import domain.UniversityYear;
import exceptions.ValidationException;
import repositories.InMemoryRepository;
import ui.Console;
import validators.Validator;
import validators.ValidatorFactory;

public class Main {

    public static void main(String[] args) {
        Student s1=new Student(1234,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student(5242,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s3=new Student(1323,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");

        Validator validator = ValidatorFactory.createValidator(Student.class);

        InMemoryRepository<Integer,Student> inMemoryRepository = new InMemoryRepository<>(validator);
        inMemoryRepository.save(s1);
        inMemoryRepository.save(s2);
        inMemoryRepository.save(s3);

        UniversityYear year=new UniversityYear("ir212",2019);
        Validator asVal= ValidatorFactory.createValidator(Assignment.class);

        InMemoryRepository<String, Assignment> asrepo = new InMemoryRepository<>(asVal);

        Assignment a1=new Assignment("as1","some stuff",6,year);
        Assignment a2=new Assignment("as2","some stuff",5,year);
        Assignment a3=new Assignment("as3","some stuff",7,year);

        asrepo.save(a1);
        asrepo.save(a2);
        asrepo.save(a3);

        Console console=new Console(inMemoryRepository,asrepo);
        console.execute();

    }
}
