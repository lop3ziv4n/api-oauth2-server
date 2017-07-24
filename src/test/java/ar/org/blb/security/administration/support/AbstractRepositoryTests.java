package ar.org.blb.security.administration.support;

import ar.org.blb.security.administration.model.Role;
import ar.org.blb.security.administration.model.User;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = {ar.org.blb.security.administration.Application.class, ar.org.blb.security.administration.configuration.DataSourceConfig.class})
public abstract class AbstractRepositoryTests {

    @Resource
    private TestEntityManager entityManager;

    private User user;
    private List<Role> roles = new ArrayList<>();

    public void persistUser() {
        if (this.user == null) {
            this.persistRole();

            this.user = new User(ConstantsTests.AuthUser.LOGIN, ConstantsTests.AuthUser.PASSWORD, Boolean.TRUE, this.roles);
            this.entityManager.persist(this.user);
        }
    }

    public void persistRole() {
        Role role = new Role(ConstantsTests.AuthRole.CODE);
        this.roles.add(this.entityManager.persist(role));
    }

    public Long getUser() {
        return this.user.getId();
    }
}
