package service.Exceptions;


/**
 * InvalidDataException is an exception that is thrown when an user tries to insert invalid data into a table of the database.
 *
 * @author Denisa Filip
 * @version $Id: $Id
 */
public class InvalidDataException extends Exception{

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
