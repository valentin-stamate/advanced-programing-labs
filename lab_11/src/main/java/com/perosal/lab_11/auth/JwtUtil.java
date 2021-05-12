package com.perosal.lab_11.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.perosal.lab_11.secrets.Secrets;

public abstract class JwtUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(Secrets.JWT_SECRET_KEY);

    public static String encode(Mappable mappable) {
        return JWT.create().withPayload(mappable.toMap()).sign(ALGORITHM);
    }

    public static DecodedJWT decode(String jwtToken) {
        DecodedJWT decodedJWT = JWT.decode(jwtToken);

        if (!verifyToken(decodedJWT)) {
            return null;
        }

        return decodedJWT;
    }

    private static boolean verifyToken(DecodedJWT decodedJWT) {
        try {
            ALGORITHM.verify(decodedJWT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
