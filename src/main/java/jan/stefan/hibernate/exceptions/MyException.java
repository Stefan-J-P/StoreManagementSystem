package jan.stefan.hibernate.exceptions;

public class MyException extends RuntimeException
{
    private final String message;


    public MyException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
