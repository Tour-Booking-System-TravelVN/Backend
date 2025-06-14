package com.travelvn.tourbookingsytem.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        InputStream svc = new ClassPathResource(firebaseConfigPath).getInputStream();
        FirebaseOptions opts = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(svc))
                .build(); // Không cần setDatabaseUrl cho Firestore
        return FirebaseApp.getApps().isEmpty()
                ? FirebaseApp.initializeApp(opts)
                : FirebaseApp.getInstance();
    }

    @Bean
    public Firestore firestore(FirebaseApp app) {
        return FirestoreClient.getFirestore(app);
    }
}