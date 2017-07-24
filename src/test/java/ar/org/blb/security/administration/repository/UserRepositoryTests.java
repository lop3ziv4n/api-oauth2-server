package ar.org.blb.security.administration.repository;

import ar.org.blb.security.administration.model.User;
import ar.org.blb.security.administration.support.AbstractRepositoryTests;
import ar.org.blb.security.administration.support.ConstantsTests;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTests extends AbstractRepositoryTests {

    @Resource
    private UserRepository repository;

    @Before
    public void init() {
        this.persistUser();
    }

    @Test
    public void findByLoginShouldReturnUser() {
        User user = this.repository.findOneByLoginAndEnabledTrue(ConstantsTests.AuthUser.LOGIN);

        validateUser(user);
    }

    private void validateUser(User user) {
        assertThat(user.getLogin()).isEqualTo(ConstantsTests.AuthUser.LOGIN);
        assertThat(user.getPassword()).isEqualTo(ConstantsTests.AuthUser.PASSWORD);
        assertThat(user.getEnabled()).isEqualTo(Boolean.TRUE);
        assertThat(user.getRoles()).doesNotContainNull();
    }
}
