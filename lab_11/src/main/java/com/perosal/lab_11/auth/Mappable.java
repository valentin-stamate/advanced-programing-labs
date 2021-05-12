package com.perosal.lab_11.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Map;

public interface Mappable {
    Map<String, Object> toMap();
    void setObjectFromClaims(DecodedJWT decodedJWT);
}
