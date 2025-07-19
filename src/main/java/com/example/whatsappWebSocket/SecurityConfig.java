
    package com.example.whatsappWebSocket;


//import com.example.whatsappWebSocket.Jwt2.AuthenticationFilter;
import com.example.whatsappWebSocket.Jwt2.AuthenticationFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
//        @Autowired
//        private AuthenticationFilter authenticationFilter;
//        @Bean
//        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//            return authenticationConfiguration.getAuthenticationManager();
//        }
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//            http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").authenticated()
//                            .anyRequest().permitAll())
//                    .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
//                    .csrf(csrf -> csrf.disable())
//                    .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
//                        @SuppressWarnings("null")
//                        @Override
//                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                            CorsConfiguration cfg = new CorsConfiguration();
//
//                            cfg.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
//                            cfg.setAllowedOriginPatterns(Arrays.asList("http://localhost:8080"));
//
//                            cfg.setAllowedMethods(Collections.singletonList("*"));
//                            cfg.setAllowedHeaders(Collections.singletonList("*"));
//                            cfg.setExposedHeaders(Arrays.asList("Authorization"));
//                            cfg.setMaxAge(3600L);
//
//                            return cfg;
//                        }
//                    })).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
//
//            return http.build();
//        }
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırak
//                    .authorizeHttpRequests(authorize -> authorize
//                            .anyRequest().permitAll() // Tüm isteklere erişimi aç
//                    );
//            return http.build();
//        }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırak
              .authorizeHttpRequests(authorizeRequests ->
                      authorizeRequests
                              .requestMatchers("/register.html", "/api/register","/login.html","/login","deneme3.html","/deneme2.html"
                                      ,"/token","login.css").permitAll()
                              .anyRequest().permitAll()

              );

     // http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
  }


        @Bean
        public PasswordEncoder passwordEncoder() {

            return new BCryptPasswordEncoder(); // Şifreleme için BCrypt kullan
        }



}
