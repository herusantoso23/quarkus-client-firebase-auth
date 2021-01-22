package com.herusantoso.services.impl;

import com.herusantoso.handlers.exception.TokenInvalidException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.herusantoso.services.FirebaseService;
import io.quarkus.security.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class FirebaseServiceImpl implements FirebaseService {

    private final Logger logger = LoggerFactory.getLogger(FirebaseServiceImpl.class);

    @Override
    public UserRecord getUserFromTokenId(String tokenId) {
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId, true);
            String uuid = firebaseToken.getUid();
            return FirebaseAuth.getInstance().getUser(uuid);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            logger.error("ERROR TOKEN : {}", e.getErrorCode());
            if(e.getErrorCode().equalsIgnoreCase("ERROR_INVALID_CREDENTIAL")){
                throw new TokenInvalidException(e.getMessage());
            }
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @Override
    public UserRecord createUserWithEmail(UserRecord.CreateRequest request) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createUser(request);
    }

    @Override
    public UserRecord updateUserWithEmail(UserRecord.UpdateRequest request) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().updateUser(request);
    }

    @Override
    public void deleteUserByEmail(String email){
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            FirebaseAuth.getInstance().deleteUser(userRecord.getUid());
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            logger.error("ERROR DELETE USER : {}", e.getErrorCode());
            throw new BadRequestException(e.getMessage());
        }
    }
}
