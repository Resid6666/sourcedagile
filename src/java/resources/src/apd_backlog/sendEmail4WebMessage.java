package resources.src.apd_backlog;

import utility.Carrier;

import utility.MailSender;public class sendEmail4WebMessage {

public static Carrier sendEmail4WebMessage(Carrier carrier) throws Exception {

//MailSender.sendMailWithAttach(carrier.get("to"), carrier.get("subject"), carrier.get("messageBody"), carrier.get("attachment"),    carrier.get("cc"),carrier.get("bb"));
carrier.set("almar","armud");

return carrier;

}

}

