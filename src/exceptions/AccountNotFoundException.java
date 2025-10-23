package exceptions;

//Why made custom exception :-
/*
* I created a custom exception for clarity and specific error handling.

*  While a generic RuntimeException works, AccountNotFoundException immediately tells any
*  developer exactly what went wrong just by its name.
*  This makes the code self-documenting and much easier to debug.
* (future proof, easy to understand/read and easy to modify
 */
public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
