package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.passclasses.SimpleUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRepository extends CrudRepository<Users, Long> {

    //Buscar por login
    @Query("SELECT usr FROM Users usr WHERE usr.login like :login")
    public Users findByLogin(@Param("login") String login);

    @Query(value = "SELECT new br.com.personal_financee.pf.passclasses.SimpleUser(u.id_usuario, u.name, u.login, u.sex, "
                    +"u.isActive) FROM Users u")
    public Collection<SimpleUser> listSimpleUsers();
}
