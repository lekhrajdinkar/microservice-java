# Security App
## Overview
- [security - Notes 📚](../../../../../docs/02_springboot/04_security)

## Runtime Details 
- Database : no
- ApiDoc : http://localhost:8085/SecurityApp/swagger-ui/index.html#/
- okta : https://dev-16206041-admin.okta.com/admin/getting-started
- auth0 : https://manage.auth0.com/dashboard/us/dev-gpg8k3i38lkcqtkw/onboarding | signed up with Github | dev-gpg8k3i38lkcqtkw

## POC/s
### A1. Enable security in SB App
- **old**: implement **WebSecurityConfigurerAdapter**  ❌
- Add dependency : **spring-boot-starter-security**
- Add below bean/s:
```java
  @Configuration
  @EnableGlobalMethodSecurity(prePostEnabled = true)
  public class SecurityConfig 
  {
  // 1 ▶️  Security Filter
  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // 👈🏻 injecting : HttpSecurity http
        http
            .authorizeRequests(authorize -> authorize
                    .requestMatchers("/swagger-ui/**", "/actuators/**").permitAll()
                    
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

    // 3 ▶️ CORS
    @Bean // for spring MVC
    WebMvcConfigurer webMvcConfigurerForApp(){
      return new WebMvcConfigurer()
      {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
          registry
                  .addMapping("/**")
                  .allowedMethods("*");
        }
      };
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
#### traditional webApp (3) ❌
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


#### Modern webApp - Secure REST (2) ✅
- **▶️basic/digest Authentication**
- **▶️OpenID Connect**
  - SB helps to integrating with **external authentication-providers** (okta, google, facebook, etc)
  - **Identity token** generate by Okta, requested by UI or consumer.
- **▶️API Keys**
  - https://www.baeldung.com/spring-boot-api-key-secret
  - Some REST APIs use API keys for authentication.
  - An API-key is like `token`, that identifies the - `API-client to the API without referencing an actual user`.
  - API-key can be sent in the queryString or header.
  - it’s possible to hide the key using SSL.
  - Create `Custom Filter` to Check API-Check
  - eg: CCGG MuleSoft API


### B2. Authorization/OAuth2 (Modern) ✅
- [01_02_OAuth_2.0.md](../../../../../docs/02_springboot/04_security/01_02_OAuth_2.0.md)
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
- @Bean : `WebMvcConfigurer`
- check above

### D. Custom Filter/s
- @Order(1) @Bean : `SecurityFilterChain`
- **scenario-1** : manually chain with other filter
- **scenario-2**
  - filter-1 bean  @Order(1)  for url-pattern-1, do form-login
  - filter-2 bean  @Order(2)  for url-pattern-2, do Oauth-JWT-validation

### E. try Security headers
- Security headers
  - `Strict-Transport-Security`
  - `X-Content-Type-Options`
  - `X-Frame-Options`
  - `Content-Security-Policy` 