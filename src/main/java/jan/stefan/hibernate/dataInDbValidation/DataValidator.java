package jan.stefan.hibernate.dataInDbValidation;

public interface DataValidator<T, REPO>
{
    T findObjectByIdAndName(T t, REPO repo);
}
