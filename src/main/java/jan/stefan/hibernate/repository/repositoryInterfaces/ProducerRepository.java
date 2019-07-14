package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Producer;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface ProducerRepository extends GenericRepository<Producer, Long>
{
    Optional<Producer> findOneByName(String name);
}
