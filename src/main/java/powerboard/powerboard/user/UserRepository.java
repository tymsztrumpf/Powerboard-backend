package powerboard.powerboard.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import powerboard.powerboard.user.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u")
    Set<User> getAll();
}
