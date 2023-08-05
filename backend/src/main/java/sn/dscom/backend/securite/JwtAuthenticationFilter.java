package sn.dscom.backend.securite;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.service.util.TokenUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String token = TokenUtils.extractTokenRequest(request);
            if(token!=null) {
                try {
                    HttpSession session = request.getSession();
                    TokenUtils.isvalidToken(token);
                    Authentication auth = new JwtAuthentication(token);
                    auth.setAuthenticated(true);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    filterChain.doFilter(request, response);
                } catch (CommonMetierException e) {
                    response.setStatus(e.getHttpStatus());
                }
            }else{
                filterChain.doFilter(request, response);
            }

    }
    private String extractTokenRequest(HttpServletRequest request){
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
