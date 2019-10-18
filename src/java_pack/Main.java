package java_pack;

import domain.Student;
import repositories.InMemoryRepository;
import validators.Validator;
import validators.ValidatorFactory;

public class Main {

    public static void main(String[] args) {
        Student s1=new Student(1234,"ion","bacu",221);
        Student s2=new Student(5242,"ion","bacu",221);
        Student s3=new Student(1323,"ion","bacu",221);

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        Validator validator = validatorFactory.createValidator("Student");

        InMemoryRepository<Integer,Student> inMemoryRepository = new InMemoryRepository<>(validator);
        inMemoryRepository.save(s1);
        inMemoryRepository.save(s2);
        inMemoryRepository.save(s3);

        for (Student st:inMemoryRepository.findAll()) {
            System.out.println(st);
        }


    }
}
