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
            " l.dt_exp<=:dtFinal and l.isPay=false ORDER BY l.dt_exp ASC")
    public List<PredictionDashboardDataModel> getAllPredictionsVencidosByUser(@Param("user") Users user, @Param("dtFinal") Calendar dtFinal);
}
