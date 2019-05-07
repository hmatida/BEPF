package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Provider;
import br.com.personal_financee.pf.models.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Long> {

    //Buscar por login
    @Query("SELECT pro FROM Provider pro WHERE pro.name_provider like :name and pro.user=:user")
    public Provider findByName(@Param("name") String name, @Param("user") Users user);

    //Retorna todas as empresas dado o usuário
    @Query("SELECT pro FROM Provider pro WHERE pro.user=:user ORDER BY pro.name_provider")
    public List<Provider> findAllProvidersByUser(@Param("user") Users user);
}
