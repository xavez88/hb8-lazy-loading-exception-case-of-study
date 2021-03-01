package usr.ach.lazyloading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usr.ach.lazyloading.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
