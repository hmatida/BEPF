package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProviderRepository extends CrudRepository<Provider, Long> {

    //Buscar por login
    @Query("SELECT pro FROM Provider pro WHERE pro.name_provider like :name")
    public Provider findByName(@Param("name") String name);
}
