package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Launches;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public interface LaunchesRepository extends CrudRepository<Launches , Long> {

    //Retorna os lançamentos de um intervalo de tempo.
    @Query("SELECT la FROM Launches la WHERE la.account=:account and la.dt between :dtInicial and :dtFinal ORDER BY la.dt, la.id_launch")
    public List<Launches> findLauchesByDtInterval(@Param("account") Account account, @Param("dtInicial")
            Calendar dtInicial, @Param("dtFinal") Calendar dtFinal);

    /*
    * Retorna último lançamento baseado na data e conta.
    * */
    @Query("SELECT la FROM Launches la WHERE la.id_launch=(SELECT MAX(b.id_launch) FROM Launches b WHERE b.account=:account"+
            " and b.dt<=:date)")
    public Launches getLastLaunchByAccountAndData(@Param("account") Account account, @Param("date") Calendar date);

    //Retorna os lançamentos de um intervalo de tempo.
    @Query("SELECT MAX(la) FROM Launches la WHERE la.account=:account")
    public Launches verifyExistsLaunch(@Param("account") Account account);

    /*
     * Retorna uma list com todos os lançamentos posteriores ao passado como parâmetro.
     * */
    @Query("SELECT la FROM Launches la WHERE la.account=:account and la.dt>:date ORDER BY la.dt, la.id_launch")
    public List<Launches> getAllLaunchesAfterLaunche(@Param("account") Account account, @Param("date") Calendar date);


    /*
     * Retorna último lançamento da conta.
     * */
    @Query("SELECT la FROM Launches la WHERE la.id_launch=(SELECT MAX(la2.id_launch) FROM Launches la2 WHERE la2.dt=(SELECT MAX(b.dt) FROM Launches b WHERE b.account=:account))")
    public Launches getLastLaunchByAccount(@Param("account") Account account);

    /*
     * Retorna todos os lançamentos referentes a dada conta.
     * */
    @Query("SELECT la FROM Launches la WHERE la.account=:account ORDER BY la.dt, la.id_launch")
    public List<Launches> getAllLaunchesByAccount(@Param("account") Account account);

}
