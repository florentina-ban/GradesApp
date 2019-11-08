package exceptions;

import java.util.ArrayList;

public class ValidationException extends RuntimeException {
    private ArrayList<String> exceptions=new ArrayList<>();

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
