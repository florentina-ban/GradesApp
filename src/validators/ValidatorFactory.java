package validators;

import domain.Entity;

public class ValidatorFactory{
        private static ValidatorFactory instance = null;

        private ValidatorFactory() {
        }

        public static ValidatorFactory getInstance(){
            if (instance==null)
                instance = new ValidatorFactory();
            return instance;
        }

        public static <E> Validator createValidator(String s){
            if (s.compareTo("Student")==0)
                return new StudentValidator();
            return null;
        }
    }
