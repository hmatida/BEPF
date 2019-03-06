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

            String text = " <div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\"><br /></span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\"><br /></span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\">Teste</span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\"><br /></span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\">n njkljasjfklj sajfklsaf skfj asflsaj sjlf saf</span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\"><br /></span></div>\n" +
                    "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                    "<a href=\"https://2.bp.blogspot.com/-HDrIcW8ZVtc/XHlYHv8H7CI/AAAAAAAAAUA/a39y2BdAHd412dBGTOLS295lOMvZbKLSACLcBGAs/s1600/4585japao.jpg\" imageanchor=\"1\" style=\"margin-left: 1em; margin-right: 1em;\"><img border=\"0\" data-original-height=\"768\" data-original-width=\"1024\" height=\"240\" src=\"https://2.bp.blogspot.com/-HDrIcW8ZVtc/XHlYHv8H7CI/AAAAAAAAAUA/a39y2BdAHd412dBGTOLS295lOMvZbKLSACLcBGAs/s320/4585japao.jpg\" width=\"320\" /></a></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\"><br /></span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\"><br /></span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"color: #20124d; font-family: Georgia, Times New Roman, serif; font-size: large;\">kjksahfhsah sa[&nbsp;</span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"color: #20124d; font-family: Georgia, Times New Roman, serif; font-size: large;\">&nbsp;njasfsa</span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"color: #20124d; font-family: Georgia, Times New Roman, serif; font-size: large;\">&nbsp;jhaj a</span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"color: #20124d; font-family: Georgia, Times New Roman, serif; font-size: large;\">k lkajdf</span></div>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "<span style=\"font-family: Georgia, Times New Roman, serif; font-size: large;\"><br /></span></div>";

            message.setContent(text, "text/html");
            /**Método para enviar a mensagem criada*/
            Transport.send(message);

            System.out.println("Feito!!!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
