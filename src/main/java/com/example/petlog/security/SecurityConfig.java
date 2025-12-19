package com.example.petlog.security;

import com.example.petlog.security.jwt.UserInfoDetailsService;
import com.example.petlog.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String ALLOWED_IP_ADDRESS = "127.0.0.1";
    public static final String SUBNET = "/32";
    public static final IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Rest api 기반의 jwt 인증에서는 csrf 토큰을 사용하지 않으므로 csrf 방어를 비활성화.
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())

                // 요청별 권한 설정
                .authorizeHttpRequests(auth -> auth

                        // Kubernetes 헬스체크 허용
                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/health/**"
                        ).permitAll()

                        // 회원가입, 로그인 허용
                        .requestMatchers("/api/users/create").permitAll()
                        .requestMatchers("/api/users/login").permitAll()

                        // Swagger 허용
                        .requestMatchers(
                                "/swagger",
                                "/swagger/",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        //그 외 모든 API 요청은 JWT 인증 필수
                        .anyRequest().authenticated())
                        //Http Basic 인증을 security 필터 체인에 추가한다.
                        //Customer.withDefaults()는 Spring security의 기본 basic 인증을 사용하겠다는 의미이다.
                        //클라이언트가 api 요청을 보낼때 authorization 헤더에
                        //[Base64인코딩된 아이디: 비밀번호] 형식으로 인증 정보를 담아 보내면
                        //Spring Security가 이를 자동으로 해석하고 인증을 시도한다.
                        //.httpBasic(Customizer.withDefaults());  // ← Basic 인증 추가



        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}
