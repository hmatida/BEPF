package br.com.personal_financee.pf.utility;

import br.com.personal_financee.pf.models.LaunchPrediction;
import br.com.personal_financee.pf.repositories.LaunchesPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class GetLists {

    @Autowired
    private LaunchesPredictionRepository launchesPredictionRepository;

    private static LaunchesPredictionRepository launchesPredictionRepository1;

    @PostConstruct
    private void initRepo() {
        launchesPredictionRepository1 = this.launchesPredictionRepository;
    }

    public static List<LaunchPrediction> getAllPredicionsVctosByDataInterval(int isPay, Calendar init, Calendar end){

        List<LaunchPrediction> launchPredictions = new ArrayList<>();

        launchPredictions.addAll(launchesPredictionRepository1.getAllVctos(0, init, end));

        return launchPredictions;
    }
}
