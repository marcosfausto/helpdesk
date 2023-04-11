package com.marcosfausto.helpdesk.config;

import com.marcosfausto.helpdesk.security.JWTAuthenticationFilter;
import com.marcosfausto.helpdesk.security.JWTAuthorizationFilter;
import com.marcosfausto.helpdesk.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // ativa o controle do @PreAuthorize("hasAnyRole('ADMIN')")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    // Lista de end points sempre permitidos
    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception{

        // se o profile ativo for teste podemos acessar o bd h2
        if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        // adiciona a config do cors e desabilita o CSRF
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll() // Permite todos os métodos HTTP
                .anyRequest().authenticated(); // qualquer outro request, necessita autenticação
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtUtil,userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // assegurar que o back-end nao vai criar sessao de usuário

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    // permite o acesso a todos os end points com configurações default
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedOrigins(Arrays.asList("https://helpdesk-production-70e3.up.railway.app"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
