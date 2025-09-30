# Security App
## Overview
- Database : no
- ApiDoc : http://localhost:8085/SecurityApp/swagger-ui/index.html#/
- okta : https://dev-16206041-admin.okta.com/admin/getting-started

## POC/s
### A1. Enable security in SB App
- **old**: implement **WebSecurityConfigurerAdapter**  ❌
- Add dependency : **spring-boot-starter-security**
- Add bean/s:
```java
  @Configuration
  @EnableGlobalMethodSecurity(prePostEnabled = true)
  public class SecurityConfig 
  {
  // 1 ▶️  
  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // 👈🏻 injecting : HttpSecurity http
        http
            .authorizeRequests(authorize -> authorize
                .antMatchers("/path-read").hasAuthority("SCOPE_ScopeRead")    
                .antMatchers("/path-write").hasAuthority("SCOPE_ScopeWrite") //.hasRole("").hasAnyRole("","")
                .anyRequest().authenticated()                         
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults()) // Validate JWT tokens
            );
        return http.build();
    }
  
    // 2 ▶️ 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (microservice) -> microservice.ignoring().requestMatchers("/ignore1", "/ignore2");
    }
   }
```

### A1. Disable security in SB App
```java
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class}) 
class Config{}
```
```properties
spring.autoconfigure.exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
```

###  B1. Authentication (4)
#### traditional web application ❌
- **▶️Form-based Authentication** 
    - http.loginForm()...
- **▶️Basic Authentication** 
    - Authorization header :: Base64-encoded string username:password.
    - https | it’s possible to hide the key using SSL.
-  **▶️LDAP Authentication** 
  - springs helps to integrating with LDAP and perform authentication.

```java
/*
- UserDetail
- UserDetailsService
- Authentication
- AuthenticationManager 
- AuthenticationManagerBuilder
- AuthenticationProviders
- InMemoryUserDetailsManager 
- Custom beans :  UserDetailsService, AuthenticationProvider, Filters
*/
```
#### Modern web app 
- **▶️OpenID Connect**
  - SB helps to integrating with **external authentication-providers** (okta, google, facebook, etc)
  - **Identity token** generate by Okta, requested by UI or consumer.

### B2. Authorization/OAuth2
#### validating JWT tokens.

#### Method-level Security
- https://www.baeldung.com/spring-security-method-security

```java
//1
@EnableGlobalMethodSecurity(prePostEnabled = true) 
class config1{}

//2
@RestController
public class LocationBasedAccessController 
{
    @GetMapping("/api-1")
    //@PostAuthorize
    //@Secured
    //@RolesAllowed
    @PreAuthorize("hasAuthority('ROLE_scope1') and #jwt.claims['location'] == 'Irvine'") // 👈🏻
    public String restrictedAccess() {
        return "Access granted for users in Irvine!";
    }
}

/* claims in JWT
{
  "sub": "1234567890",
  "name": "Lekhraj Dinkar",
  "roles": ["USER_ADMIN"], // 👈🏻
  "location": "Irvine",  // Custom claim
  "iat": 1689704000,
  "exp": 1689707600,
  "scp": ["ScopeRead", "ScopeWrite"],    // 👈🏻
}
- in SpEL, refer them like // 👈🏻
  - **SCOPE_** ScopeRead
  - **ROLE_** USER_ADMIN
 */

```
### C. CORS
- @Bean `WebMvcConfigurer` 
- [WebConfig.java](config/WebConfig.java) ✔

### D. Custom Filter/s
- Add bean `SecurityFilterChain`
- @Order(1)
- manually chain with other filter
- **scenario-1**
  - filter-1 bean  @Order(1)  for url-pattern-1, do form-login
  - filter-2 bean  @Order(2)  for url-pattern-2, do Oauth-JWT-validation
