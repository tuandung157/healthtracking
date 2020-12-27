package itmo.healthtracking.HealthTrackingBackEnd.repository;


import itmo.healthtracking.HealthTrackingBackEnd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<User,Long> {
    //List<Users> findByUsername(String username);
    Optional<User> findByUsername(String username);
    Set<User> findUserByUsernameStartingWith(String username);

}
