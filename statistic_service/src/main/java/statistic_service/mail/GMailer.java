package statistic_service.mail;

// import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
// import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.common.io.CharSource;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import java.nio.charset.StandardCharsets;
// import java.nio.file.Path;
// import java.io.FileInputStream;

@ApplicationScoped
public class GMailer {

    Gmail service;

    // @ConfigProperty(name = "invia")
    // String INVIA_EMAIL;

    private static final String APPLICATION_NAME = "Projet info";

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    // Directory to store authorization tokens for this application
    private static final String TOKENS_DIRECTORY_PATH = "/src/main/resources/tokens";

    // private static final Path path = Paths.get(
    //         "src/main/resources/client_secret_1071445554883-33dj6kj0ps9dme1g22qntoaau2v8a12p.apps.googleusercontent.com.json");

    // private static final String CREDENTIALS_FILE_PATH = path.toAbsolutePath().toString();

    public GMailer() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void sendEmail(String subject, String message, String recipient)
            throws IOException, AddressException, MessagingException, GeneralSecurityException {
        // Création du message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        String INVIA_EMAIL = ConfigProvider.getConfig().getValue("invia.email", String.class);
        email.setFrom(new InternetAddress(INVIA_EMAIL));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipient));
        email.setSubject(subject);
        email.setText(message);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodeEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodeEmail);

        try {
            // Interaction avec l'API de GMAIL
            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());

        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }

        }

    }

    // Authorize credential object
    private static Credential getCredentials(final NetHttpTransport httpTransport)
            throws IOException {

        // Convert to string to input stream
        // FileInputStream fis = new FileInputStream(CREDENTIALS_FILE_PATH);

        String GMAIL_API_CREDENTIALS = ConfigProvider.getConfig().getValue("gmail.api.credentials", String.class);

        InputStream in = CharSource.wrap(GMAIL_API_CREDENTIALS).asByteSource(StandardCharsets.UTF_8).openStream();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                clientSecrets, Set.of(GmailScopes.GMAIL_SEND)) // This application is only able to send email
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        // Authorisatin de l'utilisateur
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    }
}