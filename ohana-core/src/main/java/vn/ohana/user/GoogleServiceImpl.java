package vn.ohana.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.ohana.google.dto.GooglePojo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleServiceImpl {
    @Value("${google.app.id}")
    private String googleAppId;

    public GooglePojo verifyToken(String token) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleAppId))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        System.out.println("idToken: " + idToken);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            System.out.println("payload: " + payload);

            String userId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            boolean emailVerified = payload.getEmailVerified();
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            GooglePojo googlePojo = new GooglePojo();
//            googlePojo.setId(userId);
            googlePojo.setEmail(email);
            googlePojo.setFullName(name);
            googlePojo.setThumbnailId(pictureUrl);
            return googlePojo;
        } else {
            return new GooglePojo();
        }
    }

}
