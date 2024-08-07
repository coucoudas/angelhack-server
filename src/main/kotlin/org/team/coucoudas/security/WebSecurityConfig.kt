import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableMethodSecurity
class WebSecurityConfig(
    private val authenticationFilter: AuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .headers { it.frameOptions { frameOptions -> frameOptions.sameOrigin() } }
        .authorizeHttpRequests {
//            it.requestMatchers("/api/user/sign-in", "/api/user/sign-up").permitAll()
//                .requestMatchers("/api/admin/**").hasRole(UserRole.ROLE_ADMIN.toString())
//                .requestMatchers("/api/**").hasRole(UserRole.ROLE_USER.toString())
            it.requestMatchers("/api/**").permitAll()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .addFilterBefore(authenticationFilter, BasicAuthenticationFilter::class.java)
        .build()
}