package com.example.demo.web.config;

import com.example.demo.web.oauth2.CustomOauth2Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.web.domain.enums.SocialType.GOOGLE;
import static com.example.demo.web.domain.enums.SocialType.KAKAO;

//설정에서 값 읽어오기 접두사로
@Configuration
//시큐리티를 사용하겠다!
@EnableWebSecurity
////OAuth2를 사용하겠다!
//@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*주석 2개는 1.x버전의 spring*/
//    //oauth2를 사용하기위한 컨텍스트
//    @Autowired
//    private OAuth2ClientContext oAuth2ClientContext;

//    //소셜 미디어의 프로퍼티 값을 호출하는 빈을 생성
//    @Bean
//    @ConfigurationProperties("google")
//    public CLientResources google(){
//        return new CLientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("kakao")
//    public CLientResources kakao(){
//        return new CLientResources();
//    }

    Logger logger= LoggerFactory.getLogger(SecurityConfig.class);


    //자동설정 말고 여러 권한을 최적화하기위해 WebSecurityConfigurerAdapter을 상속

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("으악");
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        http
                .authorizeRequests()
                .antMatchers("/", "/oauth2/**", "/login/**",  "/css/**", "/images/**", "/js/**", "/console/**").permitAll()
                .antMatchers("/google").hasAuthority(GOOGLE.getROleType())
                .antMatchers("/kakao").hasAuthority(KAKAO.getROleType())
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                .formLogin()
                .successForwardUrl("/board/list")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .addFilterBefore(filter, CsrfFilter.class)
                .csrf().disable();
    }

    //카카오 연결
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties, @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId) {
        logger.info("으악카카오");
        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOauth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .jwkSetUri("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties,String client){
        if("google".equals(client)){
            OAuth2ClientProperties.Registration registration=clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email","profile")
                    .build();
        }
        //페이스북은 내가 아이디가 없어서 못함
        return null;
    }

//    //OAuth2 설정
//    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter){
//        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
//        registrationBean.setFilter(filter);
//        registrationBean.setOrder(-100);
//        return registrationBean;
//    }
//
//    private Filter oauth2Filter(){
//        CompositeFilter filter=new CompositeFilter();
//        List<Filter> filters=new ArrayList<>(); //필터셍성
//        filters.add(oauth2Filter(google(),"/login/google", GOOGLE));
//        filters.add(oauth2Filter(kakao(),"/login/kakao",SocialType.KAKAO));
//        filter.setFilters(filters);//필터 등록
//        return filter;
//    }
//
//    private Filter oauth2Filter(CLientResources client, String path, SocialType socialType){
//        OAuth2ClientAuthenticationProcessingFilter filter=new OAuth2ClientAuthenticationProcessingFilter(path);
//        OAuth2RestTemplate template=new OAuth2RestTemplate(client.getClient(),oAuth2ClientContext);
//        filter.setRestTemplate(template);
//        filter.setTokenServices(new UserTokenServices(client,socialType));
//        filter.setAuthenticationSuccessHandler(((request, response, authentication) -> response.sendRedirect("/"+socialType.getValue()+"/complete")));
//        filter.setAuthenticationSuccessHandler((request, response, authentication) -> response.sendRedirect("/error"));
//        return filter;
//    }
}
