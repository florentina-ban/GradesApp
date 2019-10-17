package exceptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ValidationException extends Exception {
    private ArrayList<String> exceptions;
/*
    public ValidationException(String message) {
        super(message);
        exceptions.add(message);
    }
*/
    public ValidationException() {
        exceptions = new ArrayList<>();
    }

    public void addMessage(String message){
        exceptions.add(message);
    }

    public ArrayList<String> getMessages() {
        return exceptions;
    }
}
