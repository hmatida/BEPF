package br.com.personal_financee.pf.utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    public static void enviaEmail(String destinatario) {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hernane.matida@gmail.com", "*351hcm1605*");
            }
        });
        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hernane.matida@gmail.com")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(destinatario);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Personal Financee - Confirmação de cadasto.");//Assunto
            message.setText("Confirmação de cadastro da sua conta no Personal Financeee!!!");
            /**Método para enviar a mensagem criada*/
            Transport.send(message);

            System.out.println("Feito!!!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
