package com.herusantoso.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class FirebaseConfig {

    private final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @ConfigProperty(name = "firebase.database-url")
    String databaseUrl;

    @ConfigProperty(name = "firebase.location-path")
    String locationPath;

    public void initializeApp(){
        try{
            if(!FirebaseApp.getApps().isEmpty()) {
                logger.info("Firebase : {} has been initialized!", FirebaseApp.getInstance().getName());
                return;
            }
            InputStream serviceAccount = FirebaseConfig.class.getResourceAsStream(locationPath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(databaseUrl)
                    .build();

            FirebaseApp.initializeApp(options);
            logger.info("Firebase : {} initialized!", FirebaseApp.getInstance().getName());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
