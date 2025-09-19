package br.ufpb.dsc.cinema_api.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import io.jsonwebtoken.security.SignatureException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private  JWTUtil jwtUtil;
    private  UsuarioDetailsService usuarioDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JWTUtil jwtUtil, UsuarioDetailsService usuarioDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                String tokenValue = token.replace("Bearer ", "");
                String user = jwtUtil.extractUsername(tokenValue);

                if (user != null) {
                    UserDetails userDetails = usuarioDetailsService.loadUserByUsername(user);
                    if (jwtUtil.validateToken(tokenValue, userDetails)) {
                        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    }
                }
            } catch (ExpiredJwtException | UnsupportedJwtException | SignatureException | MalformedJwtException | IllegalArgumentException e) {
                logger.warn("Token JWT inválido ou expirado: " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
