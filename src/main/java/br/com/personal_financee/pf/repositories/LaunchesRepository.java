package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Launches;
import br.com.personal_financee.pf.models.TypeOfLaunch;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.models.report_models.PieDataModel;
import br.com.personal_financee.pf.models.report_models.PrepareRecipeAndExpenses;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public interface LaunchesRepository extends CrudRepository<Launches , Long> {

    //Retorna os lançamentos de um intervalo de tempo.
    @Query("SELECT la FROM Launches la WHERE la.user=:user and la.account=:account and la.dt between :dtInicial and :dtFinal ORDER BY la.dt, la.id_launch")
    public List<Launches> findLauchesByDtInterval(@Param("user") Users user, @Param("account") Account account, @Param("dtInicial")
            Calendar dtInicial, @Param("dtFinal") Calendar dtFinal);

    /*
    * Retorna último lançamento baseado na data e conta.
    * */
    @Query("SELECT la FROM Launches la WHERE la.id_launch=(SELECT MAX(b.id_launch) FROM Launches b WHERE b.user=:user and b.account=:account"+
            " and b.dt<=:date)")
    public Launches getLastLaunchByAccountAndData(@Param("user") Users user, @Param("account") Account account, @Param("date") Calendar date);

    //Retorna os lançamentos de um intervalo de tempo.
    @Query("SELECT MAX(la) FROM Launches la WHERE la.user=:user and la.account=:account")
    public Launches verifyExistsLaunch(@Param("user") Users user, @Param("account") Account account);

    /*
     * Retorna uma list com todos os lançamentos posteriores ao passado como parâmetro.
     * */
    @Query("SELECT la FROM Launches la WHERE la.user=:user and la.account=:account and la.dt>:date ORDER BY la.dt, la.id_launch")
    public List<Launches> getAllLaunchesAfterLaunche(@Param("user") Users user, @Param("account") Account account, @Param("date") Calendar date);


    /*
     * Retorna último lançamento da conta.
     * */
    @Query("SELECT la FROM Launches la WHERE la.id_launch=(SELECT MAX(la2.id_launch) FROM Launches la2 WHERE la2.dt=(SELECT MAX(b.dt) FROM Launches b WHERE b.user=:user and b.account=:account))")
    public Launches getLastLaunchByAccount(@Param("user") Users user, @Param("account") Account account);

    /*
     * Retorna todos os lançamentos referentes a dada conta.
     * */
    @Query("SELECT la FROM Launches la WHERE la.user=:user and la.account=:account ORDER BY la.dt, la.id_launch")
    public List<Launches> getAllLaunchesByAccount(@Param("user") Users user, @Param("account") Account account);

    /**
     * Retorna dados para alimentação do dashboard, coluna
     * */
    @Query("SELECT new br.com.personal_financee.pf.models.report_models.PrepareRecipeAndExpenses(l.typeOfLaunch, SUM(l.pay_value)) FROM Launches l WHERE l.user=:user and"+
            " l.dt >:dtInicial and l.dt<=:dtFinal and l.chart=1 GROUP BY l.typeOfLaunch")
    public List<PrepareRecipeAndExpenses> getLaunchesByAccountForSumRecipeAndExpenses(@Param("user") Users user, @Param("dtInicial") Calendar dtInicial,
                                                                                      @Param("dtFinal") Calendar dtFinal);

    /**
     * Retorna dados para alimentação do dashboard, Pie Chart expenses
     * */
    @Query("SELECT new br.com.personal_financee.pf.models.report_models.PieDataModel(l.subCategory.subCategoryName, SUM(l.pay_value)) FROM Launches l WHERE l.user=:user and"+
            " l.dt >:dtInicial and l.dt<=:dtFinal and l.typeOfLaunch=br.com.personal_financee.pf.models.TypeOfLaunch.S and l.chart=1 GROUP BY l.subCategory.subCategoryName")
    public List<PieDataModel> getLaunchesByAccountForSumExpenses(@Param("user") Users user, @Param("dtInicial") Calendar dtInicial,
                                                                 @Param("dtFinal") Calendar dtFinal);

    /**
     * Retorna dados para alimentação do dashboard, Pie Chart recepts
     * */
    @Query("SELECT new br.com.personal_financee.pf.models.report_models.PieDataModel(l.subCategory.subCategoryName, SUM(l.pay_value)) FROM Launches l WHERE l.user=:user and"+
            " l.dt >:dtInicial and l.dt<=:dtFinal and l.typeOfLaunch=br.com.personal_financee.pf.models.TypeOfLaunch.C and l.chart=1 GROUP BY l.subCategory.subCategoryName")
    public List<PieDataModel> getLaunchesByAccountForSumRecepts(@Param("user") Users user, @Param("dtInicial") Calendar dtInicial,
                                                                 @Param("dtFinal") Calendar dtFinal);

    @Query("SELECT new br.com.personal_financee.pf.models.report_models.PieDataModel(l.subCategory.category.name_category, SUM(l.pay_value)) FROM Launches l WHERE l.user=:user and"+
            " l.dt >:dtInicial and l.dt<=:dtFinal and l.typeOfLaunch=br.com.personal_financee.pf.models.TypeOfLaunch.S and l.chart=1 GROUP BY l.subCategory.category.name_category")
    public List<PieDataModel> getLaunchesByAccountForSumExpensesByCategory(@Param("user") Users user, @Param("dtInicial") Calendar dtInicial,
                                                                @Param("dtFinal") Calendar dtFinal);

    @Query("SELECT new br.com.personal_financee.pf.models.report_models.PieDataModel(l.subCategory.category.name_category, SUM(l.pay_value)) FROM Launches l WHERE l.user=:user and"+
            " l.dt >:dtInicial and l.dt<=:dtFinal and l.typeOfLaunch=br.com.personal_financee.pf.models.TypeOfLaunch.C and l.chart=1 GROUP BY l.subCategory.category.name_category")
    public List<PieDataModel> getLaunchesByAccountForSumReceByCategory(@Param("user") Users user, @Param("dtInicial") Calendar dtInicial,
                                                                           @Param("dtFinal") Calendar dtFinal);


}
