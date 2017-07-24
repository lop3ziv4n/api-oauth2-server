package ar.org.blb.security.administration.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "blb_rest_api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.requestMatchers()
                .antMatchers("/api/v1/foo/**")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/foo/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/foo/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.PATCH, "/api/v1/foo/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.POST, "/api/v1/foo/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/api/v1/foo/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/api/v1/foo/**").access("#oauth2.hasScope('write')")
                .and()
                .requestMatchers()
                .antMatchers("/api/v1/oauth/user/**")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/oauth/user/**").access("#oauth2.hasScope('read')");
    }

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers()
//                .antMatchers("/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/**").hasRole("USER")
//                .antMatchers(HttpMethod.PATCH, "/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN");
//    }
}
