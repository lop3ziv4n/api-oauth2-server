package ar.org.blb.security.administration.repository;

import ar.org.blb.security.administration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByLoginAndEnabledTrue(String login);
}
