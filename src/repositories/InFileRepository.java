package repositories;

import domain.Entity;
import validators.Validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Scanner;

public class InFileRepository<ID,E extends Entity<ID>> extends InMemoryRepository<ID,E >{
    private String fileName;
    private String type;

    public InFileRepository(Validator<E> validator,String file) {
        super(validator);
        this.fileName = file;
        if (validator.getClass().getName().compareTo("validators.StudentValidator")==0)
            type="Student";
        loadData();
    }
    public <ID,E extends Entity<ID>> void loadData(){
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] parts = line.split(",");
                if (type.compareTo("Student") == 0) {

                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            }
    }
}
