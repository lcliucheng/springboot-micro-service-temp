package com.lc.common.jwt.store;

import com.lc.common.config.AuthProperties;
import com.lc.common.consts.Scope;
import com.lc.common.jwt.JwtToken;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Redis凭证存储
 *
 * @author liucheng
 * @since 2019-11-23
 */
@Component
public class RedisTokenStore implements TokenStore {

    private static final String PREFIX = ":JwtToken:";
    private final RedissonClient redissonClient;
    private final AuthProperties authProperties;

    public RedisTokenStore(@Nullable @Lazy final RedissonClient redissonClient,
                           @Nullable final AuthProperties authProperties) {
        this.redissonClient = redissonClient;
        this.authProperties = authProperties;
    }

    @Override
    public boolean saveToken(JwtToken jwtToken) {
        Assert.notNull (redissonClient, "Redisson config non exist");
        Assert.notNull (authProperties, "Auth config non exist");
        redissonClient.getBucket (authProperties.getIssuer () + PREFIX + jwtToken.getUserId () + ":" + jwtToken.getScope ())
                .set (jwtToken, jwtToken.getExpiresIn (), TimeUnit.SECONDS);
        return true;
    }

    @Override
    public boolean reletToken(JwtToken jwtToken) {
        Assert.notNull (redissonClient, "Redisson config non exist");
        Assert.notNull (authProperties, "Auth config non exist");
        redissonClient.getBucket (authProperties.getIssuer () + PREFIX + jwtToken.getUserId () + ":" + jwtToken.getScope ())
                .expire (jwtToken.getExpiresIn (), TimeUnit.SECONDS);
        return true;
    }

    @Override
    public JwtToken getToken(String userId, String scope) {
        Assert.notNull (redissonClient, "Redisson config non exist");
        Assert.notNull (authProperties, "Auth config non exist");
        RBucket<JwtToken> bucket = redissonClient.getBucket (authProperties.getIssuer () + PREFIX + userId + ":" + scope);
        return bucket.get ();
    }

    @Override
    public boolean deleteToken(String userId, String scope) {
        Assert.notNull (redissonClient, "Redisson config non exist");
        Assert.notNull (authProperties, "Auth config non exist");
        redissonClient.getBucket (authProperties.getIssuer () + PREFIX + userId + ":" + scope).delete ();
        return true;
    }

    @Override
    public boolean deleteToken(String userId) {
        Assert.notNull (redissonClient, "Redisson config non exist");
        Assert.notNull (authProperties, "Auth config non exist");
        for (Scope scope : Scope.values ()) {
            redissonClient.getBucket (authProperties.getIssuer () + PREFIX + userId + ":" + scope).delete ();
        }
        return true;
    }

    @Override
    public boolean saveTokenFst(JwtToken jwtToken) {
        Assert.notNull(redissonClient, "Redisson config non exist");
        Assert.notNull(authProperties, "Auth config non exist");
        redissonClient.getBucket(authProperties.getIssuer() + PREFIX + jwtToken.getUserId() + ":" + jwtToken.getScope(),new FstCodec ())
                .set(jwtToken, jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        return true;
    }

    @Override
    public JwtToken getTokenFst(String userId, String scope) {
        Assert.notNull(redissonClient, "Redisson config non exist");
        Assert.notNull(authProperties, "Auth config non exist");
        RBucket<JwtToken> bucket = redissonClient.getBucket(authProperties.getIssuer() + PREFIX + userId + ":" + scope,new FstCodec());
        return bucket.get();
    }

}
