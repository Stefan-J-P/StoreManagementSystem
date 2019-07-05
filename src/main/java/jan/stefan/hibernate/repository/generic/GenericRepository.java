package jan.stefan.hibernate.repository.generic;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID>
{
    Optional<T> saveOrUpdate(T t);
    void delete(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
}
