package sn.dscom.backend.securite;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.service.util.TokenUtils;

public class JwtLogoutHandler  implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = TokenUtils.extractTokenRequest(request);
        if (token != null ){
                TokenUtils.isvalidToken(token);
                Authentication auth = new JwtAuthentication(token);
                auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);
            if(auth!=null ) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                request.getSession().invalidate();
            }
        }
    }
}
