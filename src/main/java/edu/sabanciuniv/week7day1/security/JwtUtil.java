package edu.sabanciuniv.week7day1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private final String SECRET_KEY = "e064ff21702b51f6f30c36c289efa86315d5a4b9578c6c699944e6c6f5e71a520008e9122f92bb709326cab5ce5413ae0cd3d056ad85634c62fd6b08bd17aabb3eaeecca641180040eb6bdaa2116ad6cfa6180dc33af22fd7f3b2636eda8a2fa3fe8d0e3e9e11fc6f83b5526e96e93ed3ef64f5825a24285635da15f503ca2889136e06626365aa49bafceb599b6974477b1ec95863ab6a2f828ba52a5ed726c9a20d415fc5fd8df2fe08bb85a634714fb84bcb3291b11f8fdcd896c05807bbedafdd05d9694773141bd0edc4058bd39b8c9e0f17f892cd1f12cb72c441874af7a66b5c5e83442d583f0984987d1ff99dfcb24895aea200251a4675cce8a4c57"; // Replace with a secure key

    // Generate JWT Token
    public String generateToken(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate JWT Token
    public boolean validateToken(String token, String id) {
        final String tokenid = extractid(token);
        return tokenid.equals(id) && !isTokenExpired(token);
    }

    // Extract id from JWT Token
    public String extractid(String token) {
        return extractClaims(token).getSubject();
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Extract claims from token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


}
