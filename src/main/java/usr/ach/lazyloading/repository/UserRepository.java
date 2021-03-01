package usr.ach.lazyloading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import usr.ach.lazyloading.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :id")
    Optional<User> findById(@Param("id") final Long id);


    @Override
    @Query(value = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.orders")
    List<User> findAll();
}
