package vn.ohana.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.ohana.jwt.JwtAuthenticationFilter;
import vn.ohana.user.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private UserServiceImpl userService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return username-> userService.loadUserByUsername(username);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    protected AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers(
//                        "/",
//                        "/index",
//                        "/assets/**",
//                        "/api/auth/**",
//                        "/shop",
//                        "/login")
//                .permitAll()
//                .requestMatchers(HttpMethod.GET,"/api/users/**","/api/products/**")
//                .permitAll()
//                .requestMatchers("/api/carts/**")
//                .hasAnyAuthority("ROLE_ADMIN","ROLE_MODERATOR","ROLE_USER")
//                .requestMatchers("/management/**")
//                .hasAnyAuthority("ROLE_ADMIN","ROLE_MODERATOR")
////                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
////                .loginProcessingUrl("/auth/login")
////                .defaultSuccessUrl("/index", true)
////                .failureUrl("/login?err=true")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .deleteCookies("jwtToken")
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                .accessDeniedHandler(customAccessDeniedHandler())
//        ;
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/","/resources/**","/api/login/**").permitAll()
//                .antMatchers("/api/utilities/**").hasAuthority("ADMIN")
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/sign-in").permitAll().and()
                .logout().logoutUrl("/logout").permitAll().clearAuthentication(true)
                .deleteCookies("jwtToken").and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler())
                .authenticationEntryPoint(restServicesEntryPoint())
                .and().httpBasic();
//
        return http.build();
    }

}