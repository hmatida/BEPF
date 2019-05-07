package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.LaunchPrediction;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.models.report_models.PredictionDashboardDataModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

public interface LaunchesPredictionRepository extends CrudRepository<LaunchPrediction, Long> {

    /**
     * Retorna todos os lançamentos vencidos e a vencer nos próximos 15 dias
     * */
    @Query("SELECT new br.com.personal_financee.pf.models.report_models.PredictionDashboardDataModel(l.dt_exp, l.provider.name_provider, l.subCategory.subCategoryName, l.value, l.typeOfLaunch) FROM LaunchPrediction l WHERE l.user=:user and"+
            " l.dt_exp<=:dtFinal and l.isPay=0 ORDER BY l.dt_exp ASC")
    public List<PredictionDashboardDataModel> getAllPredictionsVencidosByUser(@Param("user") Users user, @Param("dtFinal") Calendar dtFinal);


    /**
     * Retorna todos os lançamentos com base no usuário e intervalo de tempo.
     * @return: <LaunchPrediction>
     * */
    @Query("SELECT lp FROM LaunchPrediction lp where lp.user=:user and lp.isPay=:pay and lp.dt_exp BETWEEN :dtInicial and :dtFinal ORDER BY lp.dt_exp ASC")
    public List<LaunchPrediction> getAllPredictionsNotPayByUserAndDataInterval(@Param("user")Users users,
                                                                         @Param("pay") int isPay,
                                                                         @Param("dtInicial") Calendar dtFinal,
                                                                         @Param("dtFinal") Calendar dtInicial);

    /**
     * Retorna todos os vencidos do dado usuário.
     * @return: <LaunchPrediction>
     * */
    @Query("SELECT lp FROM LaunchPrediction lp where lp.user=:user and lp.isPay=:pay ORDER BY lp.dt_exp ASC")
    public List<LaunchPrediction> getAllPredictionsNotPayByUser(@Param("user")Users users,
                                                                               @Param("pay") int isPay);


    /**
     * Retorna todos os lançamentos com base no usuário e de acordo com os parâmetros opcionais.
     * @return: <LaunchPrediction>
     * */
    @Query("SELECT lp FROM LaunchPrediction lp where lp.user=:user and (lp.isPay=:pay or lp.dt_exp BETWEEN :dtInicial and :dtFinal) ORDER BY lp.dt_exp ASC")
    public List<LaunchPrediction> getAllPredictionsByUserAndParams(@Param("user")Users users,
                                                                               @Param("pay") int isPay,
                                                                               @Param("dtInicial") Calendar dtFinal,
                                                                               @Param("dtFinal") Calendar dtInicial);


    /**
     * Retorna todos os lançamentos com base no usuário e de acordo com os parâmetros opcionais.
     * @return: <LaunchPrediction>
     * */
    @Query("SELECT lp FROM LaunchPrediction lp where lp.isPay=:pay and lp.dt_exp BETWEEN :dtInicial and :dtFinal ORDER BY lp.dt_exp ASC")
    public List<LaunchPrediction> getAllVctos(@Param("pay") int isPay, @Param("dtInicial") Calendar dtFinal,
                                                                   @Param("dtFinal") Calendar dtInicial);

}
