package sm.diploma.commanOption;

public enum DaoType {
    IN_MEM_Base("InMemory"), JDBC("jdbc");

    private final String value;

    DaoType(String value) {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
