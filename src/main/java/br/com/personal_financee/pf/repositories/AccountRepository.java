package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    //Buscar por nome da conta
    @Query("SELECT ac FROM Account ac WHERE ac.name_account like :name")
    public Account findByName(@Param("name") String name);

    //Retorna a conta principal do usuário.
    @Query("SELECT ac FROM Account ac WHERE ac.user=:user and ac.principal = true")
    public Account principalAccountByUser(@Param("user") Users user);

    //Retorna as contas do usuário.
    @Query("SELECT ac FROM Account ac WHERE ac.user=:user")
    public List<Account> geAllAccountsByUser(@Param("user") Users user);
}
