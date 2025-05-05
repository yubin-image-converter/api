package dev.yubin.imageconverter.api.security.filter;

import dev.yubin.imageconverter.api.security.jwt.JwtProvider;
import dev.yubin.imageconverter.api.security.userdetails.CustomUserDetailsService;
import dev.yubin.imageconverter.api.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/") ||
                path.startsWith("/api/swagger-ui") ||
                path.startsWith("/api/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        log.info("🔥 JWT 필터 진입: {}", request.getRequestURI());
        String authHeader = request.getHeader("Authorization");
        log.info("🔍 Authorization header: {}", authHeader);

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("🍪 Cookie: {} = {}", cookie.getName(), cookie.getValue());
            }
        }

        String token = resolveToken(request);
        if (token != null) {
            try {
                // 🔐 토큰 파싱
                Jws<Claims> claims = jwtProvider.validateToken(token); // 유효성 검사 + 파싱
                String userId = jwtProvider.getPublicIdFromToken(token);
                log.info("🔥 userId (sub): {}", userId);

                // 🔑 인증 객체 생성
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                log.warn("🚨 유효하지 않은 토큰입니다: {}", e.getMessage());
            }
        }


        chain.doFilter(request,
                       response);
    }

    private String resolveToken(HttpServletRequest request) {
        // 1. Authorization 헤더에서 Bearer 토큰 추출
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 2. 또는 쿠키에서 accessToken 찾기
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                         .filter(cookie -> "accessToken".equals(cookie.getName()))
                         .findFirst()
                         .map(Cookie::getValue)
                         .orElse(null);
        }

        return null;
    }

}
