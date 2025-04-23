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
import org.springframework.security.core.context.SecurityContextHolder;
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

//    public JwtAuthenticationFilter(
//            JwtProvider jwtProvider,
//            UserService userService
//    ) {
//        this.jwtProvider = jwtProvider;
//        this.userDetailsService = userDetailsService;
//    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        log.info("🔥 JWT 필터 진입: {}", request.getRequestURI());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("🍪 Cookie: {} = {}", cookie.getName(), cookie.getValue());
            }
        }

        // 🍪 access_token 쿠키에서 JWT 추출
        String token = Arrays
                .stream(request.getCookies() !=
                                null ? request.getCookies() : new Cookie[0])
                .filter(c -> c
                        .getName()
                        .equals("access_token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        log.info(token);

        if (token != null) {
            try {
                Jws<Claims> claims = jwtProvider.validateToken(token);
                String publicId = claims
                        .getBody()
                        .getSubject();
                log.info("🎟️ 인증된 사용자: " + publicId); // 여기에 로그 찍히는지!

                // 👉 UserDetails 기반 인증객체 생성
                var userDetails =
                        userDetailsService.loadUserByUsername(publicId);
                var authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                                                             null,
                                                                             userDetails.getAuthorities());
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            } catch (Exception e) {
                log.warn("JWT 필터: 유효하지 않은 토큰입니다.");
            }
        }

        chain.doFilter(request,
                       response);
    }
}
