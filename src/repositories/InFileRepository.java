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

    /** constructor
     * @param validator - Validator<E>
     * @param file - string - fisierul unde sunt stocate datele
     */
    public InFileRepository(Validator<E> validator,String file) {
        super(validator);
        this.fileName = file;
        if (validator.getClass().getName().compareTo("validators.StudentValidator")==0)
            type="Student";
        loadData();
    }

    /**
     * citeste datele din fisier
     */
    private void loadData(){
       Path path= Paths.get(fileName);
        try{
            List<String> allLines=Files.readAllLines(path);
            allLines.forEach(x->{
                if (x.length()>2)
                    super.save(this.parseLine(x));
            });
        }catch (ValidationException e){
            System.out.println(e.getMessages());
        }
        catch (IOException e){
        System.out.println(e.getMessage());
            }
    }

    /**
     * scrie in fisier cate o entitate
     */
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
    /**
     *  @param entity  (entity must be not null)
     *  @return null- if the given entity is saved otherwise returns the entity (id already exists)
     *  @throws ValidationException  - if the entity is not valid
     *  @throws IllegalArgumentException  - if the given entity is null.
     */
    @Override
    public E save(E entity) throws ValidationException {
        E e = super.save(entity);
        if (e==null) writeToFile();
        return e;
    }

    /**
     *  removes the entity with the specified id
     *  @param id - id must be not null
     *  @return the removed entity or null if there is no entity with the given id
     *  @throws IllegalArgumentException - if the given id is null.
     */
    @Override
    public E delete(ID id) {
        E e  = super.delete(id);
        if (e!=null)
            writeToFile();
        return e;
    }

    /**
     * @param entity - entity must not be null      ]
     * @return null - if the entity is updated, otherwise  returns the entity  - (e.g id does not exist).
     * @throws IllegalArgumentException - if the given entity is null.
     * @throws ValidationException  -  if the entity is not valid.
     */
    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if (e==null)
            writeToFile();
        return e;
    }

    /**
     * constructs the entity read from a file
     * @param x -  string
     * @return - an element of type E
     */
    abstract E parseLine(String x);

    /**
     * @param entity the entity that needs to be saved in file
     * @return onject of type Iterable<String> that will be printed in file
     */
    abstract Iterable<String> writeLine(E entity);
}
