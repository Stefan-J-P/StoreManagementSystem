package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface CategoryRepository extends GenericRepository<Category, Long>
{
    Optional<Category> findOneByName(String name);
}
