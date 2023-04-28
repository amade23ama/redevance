package sn.dscom.backend.service.util;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sn.dscom.backend.BackendApplication;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.common.exception.CommonMetierException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TokenUtils {
    static Logger log= LogManager.getLogger(TokenUtils.class);
    private static final String SECRET = "mysecretkey";
    private static final long EXPIRATION_TIME = 100000000;




    public static String generateToken(UtilisateurConnectedDTO utilisateurConnectedDTO) {

        String token= Jwts.builder()
                .setId(utilisateurConnectedDTO.getId().toString())
                .claim("login",utilisateurConnectedDTO.getLogin())
                .claim("prenom",utilisateurConnectedDTO.getPrenom())
                .claim("nom",utilisateurConnectedDTO.getNom())
                .setSubject(utilisateurConnectedDTO.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .claim("authorities", utilisateurConnectedDTO.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .compact();
        return token;

    }
    public static UtilisateurConnectedDTO getUtilisateurConnectedByToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        List<?> listAuthorities= (List)claims.get("authorities");
        Collection<? extends GrantedAuthority> authorities = (Collection)listAuthorities.stream().map((item) -> {
            return new SimpleGrantedAuthority(item.toString());
        }).collect(Collectors.toList());

      return   UtilisateurConnectedDTO.builder()
                .login((String)claims.get("login"))
                .email(claims.getSubject())
                .nom((String)claims.get("nom"))
                .prenom((String)claims.get("prenom"))
                .authorities(new ArrayList<>(authorities))
                .build();

    }
    public static void isvalidToken(String authToken){
        try {
            Jws test =Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        } catch (MalformedJwtException e) {
             log.error("Invalid JWT token: {}", e.getMessage());
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        }
        catch (Exception e) {
            // Return a 401 error if there is an issue with the token
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        }
    }

    public static String extractTokenRequest(HttpServletRequest request){
        //todo
        String header = request.getHeader("Authorization");
        return header;
        /*if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        */
        // return null;
    }
}
