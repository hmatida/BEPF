package br.com.personal_financee.pf.utility;

import br.com.personal_financee.pf.models.LaunchPrediction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SendEmailBeforeVcto implements Job {



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        java.util.Calendar now = java.util.Calendar.getInstance();

        now.add(Calendar.DATE, -1);
        now.set(Calendar.HOUR, now.getMaximum(Calendar.HOUR));
        now.set(Calendar.MINUTE, now.getMaximum(Calendar.MINUTE));
        now.set(Calendar.SECOND, now.getMaximum(Calendar.SECOND));
        now.set(Calendar.MILLISECOND, now.getMaximum(Calendar.MILLISECOND));


        java.util.Calendar after = java.util.Calendar.getInstance();

        after.set(java.util.Calendar.HOUR, after.getMaximum(Calendar.HOUR));
        after.set(java.util.Calendar.MINUTE, after.getMaximum(Calendar.MINUTE));
        after.set(Calendar.SECOND, after.getMaximum(Calendar.SECOND));
        after.set(Calendar.MILLISECOND, after.getMaximum(Calendar.MILLISECOND));


        List<LaunchPrediction> predictions = new ArrayList<>();


        predictions.addAll(GetLists.getAllPredicionsVctosByDataInterval(0, now, after));

        String subject = "";

        String mensagem = "Você possui um título vencido ainda não baixado no sistema com o vencimento para hoje." +
                " Acesse o sistema Personal financee e verifique. Não deixe de pagar o título no vencimento para não pagar multas e juros." +
                " Dê valor ao seu dinehiro!!!";

        if (predictions.size() > 0){
            for (int i = 0; predictions.size() > i; i++){
                subject = "Título vencido em -> ";
                subject = subject + predictions.get(i).getDt_exp().get(java.util.Calendar.DATE) + "/" + predictions.get(i).getDt_exp().get(java.util.Calendar.MONTH) + "/" + predictions.get(i).getDt_exp().get(java.util.Calendar.YEAR);
                SendMail.enviaEmail(predictions.get(i).getUser().getEmail(), mensagem, subject);
            }
        }

    }
}
