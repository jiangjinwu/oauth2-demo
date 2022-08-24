package com.monkeyk.os.oauth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.monkeyk.os.domain.users.UserDetailInfo;
import com.monkeyk.os.domain.users.Users;
import org.apache.commons.collections.MapUtils;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.ValueGenerator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


import com.auth0.jwt.JWTCreator.Builder;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CustomeOAuthIssuer implements OAuthIssuer {
    private Algorithm algorithm = Algorithm.HMAC256("tokenProperties.getJwtSecret()");

    @Autowired
    private ValueGenerator vg;
    public String accessToken() throws OAuthSystemException{

        String userName = (String)SecurityUtils.getSubject().getPrincipal();

            Builder builder = JWT.create();
//            if (MapUtils.isNotEmpty(claims)) {
//                claims.forEach((name, value) -> {
//                    if (Objects.nonNull(value)) {
//                        builder.withClaim(name, claims.getString(name));
//                    }
//
//                });
//            }
//
//            if (claims.containsKey("exp")) {
//                builder.withExpiresAt(claims.getDate("exp"));
//            } else {
//                LocalDateTime expireAt = LocalDateTime.now().plusSeconds((long)this.tokenProperties.getExpireSeconds());
//                builder.withExpiresAt(asDate(expireAt));
//            }

            builder.withIssuedAt(new Date());
            return builder.sign(this.algorithm);

       // SecurityUtils.getSubject().getPrincipal();

    }

    public String authorizationCode() throws OAuthSystemException{
        return vg.generateValue();
    }

    public String refreshToken() throws OAuthSystemException{
        return vg.generateValue();
    }

//    CustomeOAuthIssuer(ValueGenerator valueGenerator){
//        this.vg = valueGenerator;
//    }
}
