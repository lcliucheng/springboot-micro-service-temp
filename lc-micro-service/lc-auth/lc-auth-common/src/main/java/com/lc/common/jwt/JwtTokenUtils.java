package com.lc.common.jwt;

import com.google.common.collect.Maps;
import com.lc.common.exception.AuthException;
import com.zzjr.common.exception.ErrorCode;
import com.zzjr.common.exception.ErrorInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Jwt凭证处理
 *
 * @author liucheng
 * @since 2019-11-23
 */
@Slf4j
@Component
public class JwtTokenUtils {

    /**
     * 密钥
     */
    private String secret = "WkpVU0kwSlM4RDdTMjI4QQ==";

    /**
     * 生成凭证
     *
     * @param jwtToken JwtToken
     * @param issuer   发行人
     * @return AccessToken
     */
    public AccessToken generate(JwtToken jwtToken, String issuer) {
        String compact = Jwts.builder()
                .setClaims(Maps.newHashMap())
                .setId(jwtToken.getId())
                .setIssuer(issuer)
                .setIssuedAt(jwtToken.getCreatedAt())
                .setSubject(jwtToken.getScope())
                .setAudience(jwtToken.getUserId())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return new AccessToken().setAccessToken(compact).setExpiresIn(jwtToken.getExpiresIn());
    }

    /**
     * 解析
     *
     * @param token 凭证
     * @return Claims
     */
    public Claims parse(String token) throws AuthException {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new AuthException (ErrorInfo.error(ErrorCode.TOKEN_ERROR.getValue(), "登录已失效，请重新登录"));
        }
    }

    /**
     * 获取JWT Subject
     *
     * @param claims Claims
     * @return String
     */
    public String getClaimsSubject(Claims claims) {
        return claims.getSubject();
    }

    /**
     * 获取JWT Audience
     *
     * @param claims Claims
     * @return String
     */
    public String getClaimsAudience(Claims claims) {
        return claims.getAudience();
    }

}
