package vn.ohana.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.ohana.google.dto.GooglePojo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleService {
    @Value("${google.app.id}")
    private String googleAppId;

    public GooglePojo verifyToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleAppId))
                .build();
        GoogleIdToken idToken = verifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();
//        String userId = payload.getSubject();
//        String email = payload.getEmail();
//        String name = (String) payload.get("name");
//        String pictureUrl = (String) payload.get("picture");
////            boolean emailVerified = payload.getEmailVerified();
//        String locale = (String) payload.get("locale");
//        String familyName = (String) payload.get("family_name");
//        String givenName = (String) payload.get("given_name");
        return new GooglePojo().setEmail(payload.getEmail())
                .setFullName((String) payload.get("name"))
                .setThumbnailId((String) payload.get("picture"));

    }

}
