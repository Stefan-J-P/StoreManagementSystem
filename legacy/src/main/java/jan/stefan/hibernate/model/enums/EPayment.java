package jan.stefan.hibernate.model.enums;

public enum EPayment
{
    CASH("CASH"),
    CARD("CARD"),
    TRANSFER("TRANSFER");

    private String name;

    EPayment(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

}
