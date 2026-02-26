package se.iths.yunus.javatools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.yunus.javatools.model.Pizza;

// interface som ärver från JPA
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {


}
