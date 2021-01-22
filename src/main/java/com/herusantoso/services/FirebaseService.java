package com.herusantoso.services;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface FirebaseService {

    UserRecord getUserFromTokenId(String tokenId);

    UserRecord createUserWithEmail(UserRecord.CreateRequest request) throws FirebaseAuthException;

    UserRecord updateUserWithEmail(UserRecord.UpdateRequest request) throws FirebaseAuthException;

    void deleteUserByEmail(String email);

}
