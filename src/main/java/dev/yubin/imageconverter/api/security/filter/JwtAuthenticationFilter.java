package dev.yubin.imageconverter.api.security.filter;

import dev.yubin.imageconverter.api.security.jwt.JwtProvider;
import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 🍪 access_token 쿠키 읽기
        String token = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])).filter(c -> c.getName().equals("access_token")).findFirst().map(Cookie::getValue).orElse(null);

        if (token != null) {
            try {
                Jws<Claims> claims = jwtProvider.validateToken(token);
                String publicId = claims.getBody().getSubject();
                log.info("🎟️ 인증된 사용자: " + publicId); // 여기에 로그 찍히는지!

                // 사용자 정보로 인증객체 생성
                User user = userService.findByPublicId(publicId);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.warn("JWT 필터: 유효하지 않은 토큰입니다.");
            }
        }

        chain.doFilter(request, response);
    }
}
