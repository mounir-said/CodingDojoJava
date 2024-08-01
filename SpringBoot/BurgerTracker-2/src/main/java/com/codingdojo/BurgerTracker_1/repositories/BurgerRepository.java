import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.BurgerTracker_1.models.Burger;

@Repository
public interface BurgerRepository extends JpaRepository<Burger, Long> {
}


