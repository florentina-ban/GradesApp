package repositories;

import domain.Entity;
import exceptions.ValidationException;
import validators.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public abstract class InFileRepository<ID,E extends Entity<ID>> extends InMemoryRepository<ID,E >{
    private String fileName;
    private String type;

    public InFileRepository(Validator<E> validator,String file) {
        super(validator);
        this.fileName = file;
        if (validator.getClass().getName().compareTo("validators.StudentValidator")==0)
            type="Student";
        loadData();
    }
    private void loadData(){
       Path path= Paths.get(fileName);
        try{
            List<String> allLines=Files.readAllLines(path);
            allLines.forEach(x->super.save(this.parseLine(x)));
        }catch (ValidationException e){
            System.out.println(e.getMessages());
        }
        catch (IOException e){
        System.out.println(e.getMessage());
            }
    }

    private void writeToFile(){
        Path path=Paths.get(fileName);
        try {
            Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        super.findAll().forEach(x-> {
             try {
                Files.write(path,writeLine(x),StandardOpenOption.APPEND);
             } catch (IOException e) {
                 e.printStackTrace();
             }
        });
    }
    @Override
    public E save(E entity) throws ValidationException {
        E e = super.save(entity);
        if (e==null) writeToFile();
        return e;
    }

    @Override
    public E delete(ID id) {
        E e  = super.delete(id);
        if (e!=null)
            writeToFile();
        return e;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if (e==null)
            writeToFile();
        return e;
    }

    abstract E parseLine(String x);
    abstract Iterable<String> writeLine(E entity);
}
