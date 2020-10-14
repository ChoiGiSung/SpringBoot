//package com.example.demo.web.oauth;
//
//import org.apache.tomcat.util.http.parser.Authorization;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.boot.context.properties.NestedConfigurationProperty;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
//
//public class CLientResources {
//    @NestedConfigurationProperty
//    private AuthorizationCodeResourceDetails client=
//            new AuthorizationCodeResourceDetails();
//
//    @NestedConfigurationProperty
//    private ResourceServerProperties resource = new ResourceServerProperties();
//
//    public AuthorizationCodeResourceDetails getClient(){
//        return client;
//    }
//
//    public ResourceServerProperties getResource(){
//        return resource;
//    }
//}
