package com.pro1.pro.config;

import com.pro1.pro.common.security.CustomAccessDeniedHandler;
import com.pro1.pro.common.security.CustomLoginSuccessHandler;
import com.pro1.pro.common.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
//시큐리티 애너테이션 활성화를 위한 설정
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //데이터 소스
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/user/register", "/user/registerSuccess").permitAll()
                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/codegroup/**").hasRole("ADMIN")
                .antMatchers("/codedetail/**").hasRole("ADMIN")
                //회원게시판 웹 경로 보안 지정
                .antMatchers("/board/list", "/board/read").permitAll()
                .antMatchers("/board/remobe").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/board/**").hasRole("MEMBER")
                //공지사항 웹 경로 보안 지정
                .antMatchers("/notice/list", "/notice/read").permitAll()
                .antMatchers("/notice/**").hasRole("ADMIN")
                //상품 관리 웹 경로 보안 지정
                .antMatchers("/item/list").permitAll()
                .antMatchers("/item/read","/item/picture","/item/display").hasAnyRole("MEMBER","ADMIN")
                .antMatchers("/item/buy","/item/success").hasRole("MEMBER")
                .antMatchers("/item/**").hasRole("ADMIN")
                .antMatchers("/coin/**").hasRole("MEMBER")
                .antMatchers("/useritem/**").hasRole("MEMBER")
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                //CustomLoginSuccessHandler를 로그인 성공 처리자로 지정한다.
                .successHandler(authenticationSuccessHandler());
        http.logout()
                .logoutUrl("/auth/logout")
                //로그아웃이 성곡적으로 이루어지면 이동하는 URL 지정
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                //로그아웃을 하면 자동로그인에 사용하는 쿠키도 삭제해주도록한다.
                .deleteCookies("remember-me", "JSESSION_ID");
        http.exceptionHandling()
                //CustomAccessDeniedHandler를 접근 거부처리자로 지정한다.
                .accessDeniedHandler(accessDeniedHandler());
        //데이터소스를 지정하고 테이블을 이용해서 기존 로그인 정보를 기록한다.
        http.rememberMe()
                .key("hdcd")
                .tokenRepository(createJDBCRepository())
                //쿠키의 유효시간을 지정
                .tokenValiditySeconds(60 * 60 * 24);
    }
    //CustomUserDetailService를 스프링 빈으로 정의한다.
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //CustomLoginSuccessHandler를 스프링 빈으로 정의한다.
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
    //CustomAccessDeniedHandler를 스프링 빈으로 정의
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    //CustomUserDetailsService 빈을 인증 제공자에 지정한다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    private PersistentTokenRepository createJDBCRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
