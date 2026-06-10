package jan.stefan.hibernate.model.enums;

public enum EGuarantee
{
    HELP_DESK("HELP_DESK"),
    MONEY_BACK("MONEY_BACK"),
    SERVICE("SERVICE"),
    EXCHANGE("EXCHANGE");

    private String name;

    EGuarantee(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

}
