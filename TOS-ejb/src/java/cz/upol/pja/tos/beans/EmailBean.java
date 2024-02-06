package cz.upol.pja.tos.beans;

import cz.upol.pja.tos.api.IUserManager;
import cz.upol.pja.tos.models.Topic;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for sending email notification about newly created topic.
 *
 * @author marti
 */
@MessageDriven(mappedName = "newTopicTopic")
public class EmailBean implements MessageListener {

    @Resource(lookup = "mail/tos")
    private Session session;

    @EJB
    private IUserManager userManager;

    /**
     * Method that is called when new topic is created.
     *
     * @param msg should be instance of Topic.
     */
    @Override
    public void onMessage(Message msg) {
        try {
            Topic mess = msg.getBody(Topic.class);
            sendEmail(mess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(Topic data) throws Exception {
        session.setDebugOut(System.out);
        session.setDebug(true);

        List<String> programmes = new ArrayList<>();
        for (var pr : data.getProgrammes()) {
            programmes.add(pr.getName());
        }

        for (var user : userManager.getUsers()) {
            if (programmes.contains(user.getProgramme().getName())) {
                System.out.println("EMAIL SENT TO " + user.getEmail() + "Name of new topic: " + data.getName());

                var message = new MimeMessage(session);
                message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
                message.setSubject("New topic for your programme");
                message.setSender(new InternetAddress("noreply@example.com"));
                message.setText("New topic was created with a name " + data.getName() + " by: " + data.getAuthor().getFirstname() + " " + data.getAuthor().getLastname());
                Transport.send(message);
            }
        }
    }

}
