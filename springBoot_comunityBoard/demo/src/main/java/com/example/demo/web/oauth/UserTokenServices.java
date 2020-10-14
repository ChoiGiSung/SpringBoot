//package com.example.demo.web.oauth;
//
//import com.example.demo.web.domain.enums.SocialType;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
//import java.util.List;
//import java.util.Map;
//
//public class UserTokenServices extends UserInfoTokenServices {
//    public UserTokenServices(CLientResources resources, SocialType socialType) {
//        super(resources.getResource().getUserInfoUri(),resources.getClient().getClientId());
//        setAuthoritiesExtractor(new OAuth2AuthoritiesExtractor(socialType));
//    }
//
//    public static class OAuth2AuthoritiesExtractor implements AuthoritiesExtractor{
//        private String socualType;
//
//        public OAuth2AuthoritiesExtractor(SocialType socialType){
//            this.socualType=socialType.getROleType();
//        }
//
//
//        @Override
//        public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
//            return AuthorityUtils.createAuthorityList(this.socualType);
//        }
//    }
//
//
//}
