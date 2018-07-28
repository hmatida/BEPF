package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends CrudRepository<Account, Long> {

    //Buscar por nome da conta
    @Query("SELECT ac FROM Account ac WHERE ac.name_account like :name")
    public Account findByName(@Param("name") String name);

    //Retorna a conta principal do usuário.
    //*Nota: não implementado a busca do usuário.
    @Query("SELECT ac FROM Account ac WHERE ac.principal = true")
    public Account principalAccountByUser();
}
