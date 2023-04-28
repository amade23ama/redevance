package sn.dscom.backend.securite;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.service.util.TokenUtils;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthentication implements Authentication {
    private UtilisateurConnectedDTO utilisateurConnectedDTO;
    private boolean isAuthenticated;
    public JwtAuthentication(String token) {
        this.utilisateurConnectedDTO = TokenUtils.getUtilisateurConnectedByToken(token);
        this.isAuthenticated=false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.utilisateurConnectedDTO.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.utilisateurConnectedDTO;
    }

    @Override
    public Object getPrincipal() {
        return this.utilisateurConnectedDTO;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated=isAuthenticated;
    }

    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Returns true if the specified subject is implied by this principal.
     *
     * @param subject the {@code Subject}
     * @return true if {@code subject} is non-null and is
     * implied by this principal, or false otherwise.
     * @implSpec The default implementation of this method returns true if
     * {@code subject} is non-null and contains at least one principal that
     * is equal to this principal.
     *
     * <p>Subclasses may override this with a different implementation, if
     * necessary.
     * @since 1.8
     */
    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
