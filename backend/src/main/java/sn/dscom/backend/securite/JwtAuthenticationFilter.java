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
        //extractTokenRequest(request);
        HttpSession session =request.getSession();
        if (token != null ){
            try {
                TokenUtils.isvalidToken(token);
                Authentication auth = new JwtAuthentication(token);
                auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);

            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //PrintWriter writer = response.getWriter();
                //  writer.write("{\"error\":\"Unauthorized\", \"message\":\"Invalid username or password\"}");
            }
        }
        filterChain.doFilter(request, response);
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
