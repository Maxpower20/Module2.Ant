package Implementation;

import Interfaces.Validator;


public class OddValidator implements Validator <Number> {

    @Override
    public boolean isValid(Number result) {
        return (result.longValue() % 2 == 0);
    }


}
