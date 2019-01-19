package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Launches;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import br.com.personal_financee.pf.models.LaunchesAtach;
import org.springframework.data.repository.query.Param;

public interface LauncheAtachRepository extends CrudRepository<LaunchesAtach, Long> {

    //Retorna as contas do usuário.
    @Query("SELECT la FROM LaunchesAtach la WHERE la.launches=:launche")
    public LaunchesAtach getAtachByLaunches(@Param("launche") Launches launche);
}
