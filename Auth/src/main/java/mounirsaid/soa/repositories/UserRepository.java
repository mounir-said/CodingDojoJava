package mounirsaid.soa.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mounirsaid.soa.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
	User findByEmail(String email);
}