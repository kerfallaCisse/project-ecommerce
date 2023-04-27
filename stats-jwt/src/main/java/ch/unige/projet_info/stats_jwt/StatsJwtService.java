package ch.unige.projet_info.stats_jwt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;

import io.smallrye.jwt.build.Jwt;

@Singleton
public class StatsJwtService {

    /**
     * 
     * @return le JWT token nécessaire pour accéder au service backoffice
     */

    public String generateJwt() {

        Set<String> roles = new HashSet<>(
                Arrays.asList("admin"));

        return Jwt.issuer("stats-jwt")
                .subject("stats-jwt")
                .groups(roles)
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();

    }

}