package com.netflix_clone.gateway.config.token;

import com.netflix_clone.gateway.config.route.Config;
import com.netflix_clone.gateway.constant.Constant;
import com.netflix_clone.gateway.exceptions.BecauseOf;
import com.netflix_clone.gateway.exceptions.CommonException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

/**
 * Created on 2023-05-18
 * Project gateway
 */

@Component(value = "token_control")
@DependsOn(value = "constant")
public final class TokenControl {
    private final String prefix = "Bearer ";
    private final String saltPath;


    public TokenControl(String saltPath) {
        this.saltPath = saltPath;
    }

    private String getSecretKey() {
        Path path = Path.of(saltPath);
        if(!Files.exists(path))
        String privateKey = ;

        return Base64.encodeBase64String(Constant.SALT.getBytes(StandardCharsets.UTF_8));
    }

    public String encrypt(Map<String, Object> map) {
        return String.format("%s%s", prefix, Jwts.builder()
                                              .setHeaderParam(Header.TYPE, Constant.TOKEN_NAME)
                                              .setClaims(map)
                                              .setIssuer(Constant.PROJECT_NAME)
                                              .setIssuedAt(new Date())
                                              .signWith(SignatureAlgorithm.HS512, this.getSecretKey()).compact()
        );
    }

    public Map<String,Object> decrypt(String token) throws CommonException {
        Map<String, Object> claims = null;
        if(!token.startsWith(prefix)) throw new CommonException(BecauseOf.INVALID_TOKEN);
        String withoutBearer = token.replace(prefix, "");
        claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJwt(withoutBearer).getBody();
        return claims;
    }
}
