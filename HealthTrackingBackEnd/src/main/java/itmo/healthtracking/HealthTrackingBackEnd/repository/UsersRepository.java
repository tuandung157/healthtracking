package itmo.healthtracking.HealthTrackingBackEnd.repository;


import itmo.healthtracking.HealthTrackingBackEnd.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

public interface UsersRepository extends JpaRepository<Users,Long> {
    //List<Users> findByUsersName(String username);
    Optional<Users> findByUsersName(String username);
    Set<Users> findUsersByUsersNameStartingWith(String username);

}
