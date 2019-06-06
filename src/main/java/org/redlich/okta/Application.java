package org.redlich.okta;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.ClientBuilder;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.ExtensibleResource;
import com.okta.sdk.resource.application.ApplicationList;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.group.GroupBuilder;
import com.okta.sdk.resource.group.GroupList;
import com.okta.sdk.resource.log.LogEventList;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.sdk.resource.user.UserList;
import com.okta.sdk.resource.user.UserCredentials;
import com.okta.sdk.resource.user.ChangePasswordRequest;
import com.okta.sdk.resource.user.PasswordCredential;
import com.okta.sdk.resource.user.ForgotPasswordResponse;
import com.okta.sdk.resource.ResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.MalformedURLException;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        try {
            Application application = new Application();

            String issuer = "";
            boolean newUser = false;
            boolean newPassword = false;
            if (args.length == 3) {
                issuer = args[0];
                newUser = Boolean.parseBoolean(args[1]);
                newPassword = Boolean.parseBoolean(args[2]);
                }
            else {
                log.error("\n\n\tUsage: Application issuerUrl true|false true|false\n");
                System.exit(1);
                }

            log.info("\n\n\t-- Okta API Demo Application --\n");
            log.info("\n\n\tIssuer: " + issuer + "\n");
            String orgUrl = application.getOrgURL(issuer);
            log.info("\n\n\tOrg URL: " + orgUrl + "\n");

            Client client = application.createClient();

            log.info("\n\n\t-- List All Users--\n");
            UserList users = application.listAllUsers(client);
            client.listUsers().stream()
                    .forEach(user -> log.info("\n\n\t\t * " + user.getProfile() + "\n"));
            log.info("\n");

            log.info("\n\n\t-- Get Users By ID and Email --\n");
            String clientId = application.getUserByClientID(client, "00ukyiae4Ezj8lMUx356");
            String clientEmail = application.getUserByEmail(client, "mike@redlich.net");
            log.info("\n\n\t\t * using clientID as parameter, user: " + clientId + "\n");
            log.info("\n\n\t\t * using email as parameter, user: " + clientEmail + "\n");

            log.info("\n\n\t-- Get User ID --\n");
            User user = application.getUser(client, "00ukyiae4Ezj8lMUx356");
            String userId = application.getUserProfile(user);
            log.info("\n\n\t\t * userId: " + userId + "\n");

            log.info("\n\n\t-- Create new user status: --\n");
            if (newUser) {
                log.info("\n\n\t\t * Creating a new user ...\n");
                application.createUser(client);
                }
            else {
                log.info("\n\n\t\t * Not creating a new user at this time...\n");
                }

            if(newPassword) {
                application.changePassword(client,user);
                }
            else {
                log.info("\n\n\t\t * Not changing the password at this time...\n");
                }

            log.info("\n\n\t-- Get Password Reset URL --\n");
            String resetUrl = application.forgotPassword(user);
            log.info("\n\n\t\t * URL: " + resetUrl + "\n");

            log.info("\n\n\t-- List Applications --\n");
            application.listApplications(client);

            }

        catch(MalformedURLException exception) {
            log.info(exception.getMessage());
            }

        catch(ResourceException exception) {
            exception.getCauses().forEach(cause -> log.info("\t" + cause.getSummary()));
            throw exception;
            }
        }

    private Client createClient() {
        Client client = Clients.builder()
                .setOrgUrl("https://dev-287826.okta.com")
                .setClientCredentials(new TokenClientCredentials("004_DdWx-2RmX0N1CZ8OXxCkKeNVUQCrySgcizp7G1"))
                .build();
        return client;
        }

    private User createUser(Client client) {
        String email = "rowena@redlich.net";

        char[] password = {'O','c','t','o','b','e','r','2','4','*'};
        User user = UserBuilder.instance()
                .setEmail(email)
                .setFirstName("Barry")
                .setLastName("Burd")
                .setPassword(password)
                .setSecurityQuestion("Where are the ACGNJ Java Users Group meetigns held?")
                .setSecurityQuestionAnswer("Madison")
                .setActive(true)
                .buildAndCreate(client);
        return user;
        }

    private String getOrgURL(String issuer) throws MalformedURLException {
        return new URL(new URL(issuer), "/").toString();
        }

    private User getUser(Client client, String userId) {
        return client.getUser(userId);
        }
        
    private String getUserByClientID(Client client, String userId) {
        return client.getUser(userId).getProfile().getLogin();
        }

    private String getUserByEmail(Client client, String email) {
        return client.getUser(email).getProfile().getLogin();
        }

    private String getUserProfile(User user) {
        return user.getId();
        }

    private UserList listAllUsers(Client client) {
        UserList users = client.listUsers();
        return users;
       }

    private void listApplications(Client client) {
        client.listApplications().stream()
                .forEach(item -> System.out.println("\n\n\t\t * " + item + "\n"));
        }

    private void changePassword(Client client,User user) {
        UserCredentials credentials = user.changePassword(client.instantiate(ChangePasswordRequest.class)
                .setOldPassword(client.instantiate(PasswordCredential.class).setValue("October24*".toCharArray()))
                .setNewPassword(client.instantiate(PasswordCredential.class).setValue("Okt0b3r24@".toCharArray())));
        }

    private String forgotPassword(User user) {
        ForgotPasswordResponse response = user.forgotPassword(false, null);
        return response.getResetPasswordUrl();
        }
    }
