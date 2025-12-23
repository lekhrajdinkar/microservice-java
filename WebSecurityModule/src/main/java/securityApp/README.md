# Security App
## Overview
- [04_security](../../../../../docs/04_security)

## Runtime Details 
- Database : no | Observability: no
- ApiDoc : http://localhost:8087/securityApp/swagger-ui/index.html#/
- okta : https://dev-16206041-admin.okta.com/admin/getting-started
- auth0 : https://manage.auth0.com/dashboard/us/dev-gpg8k3i38lkcqtkw/onboarding | signed up with Github | dev-gpg8k3i38lkcqtkw
- [Security config](config)
- properties:  [securityApp.properties](../../../../src/main/resources/securityApp/securityApp.properties)
  - OKTA_CLIENT_SECRET_CC=<set_value> 👈🏻👈🏻


## POC/s
### A1. Enable security in SB App
- **old**: implement **WebSecurityConfigurerAdapter**  ❌
- **New**:
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
#### legacy webApp (3) ❌
- **▶️Form-based Authentication** 
    - http.loginForm()...
- **▶️Basic/Digest Authentication** 
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
- **▶️OpenID Connect**
  - SB helps to integrating with **external authentication-providers** (okta, google, facebook, etc)
  - **Identity token** generate by Okta, requested by UI or consumer.
- **▶️API Keys** ✔️
  - https://www.baeldung.com/spring-boot-api-key-secret
  - Some REST APIs use API keys for authentication.
  - An API-key is like `token`, that identifies the - `API-client to the API without referencing an actual user`.
  - API-key can be sent in the queryString or header.
  - it’s possible to hide the key using SSL.
  - Create `Custom Filter` to Check API-Check
  - eg: CCGG MuleSoft API


### B2. Authorization/OAuth2 (Modern) ✅
#### ▶️ OAuth2 grant (3)
- [OAuth2 grant types](README_OAuth2.md)
- client_credential - get access token  ✔️
- Auth Flow (PKCE) - get both token + refresh token
  - usually done on ng-app...

#### ▶️validate access tokens
- Add dep: `spring-boot-starter-oauth2-resource-server`
- **option1** / Simple :
  - http.oauth2ResourceServer(OAuthRSConfigurer -> OAuthRSConfigurer.jwt(Customizer.withDefaults()));
  - inject method argumnet - `@AuthenticationPrincipal Jwt jwt`
- **Option2** :
  - **Converter**: `Jwt` >> `AbstractAuthenticationToken` | [CustomJwtAuthenticationConverter.java](config/CustomJwtAuthenticationConverter.java)
```java
        http.oauth2ResourceServer(OAuthRSConfigurer -> OAuthRSConfigurer.jwt(jwtConfigurer -> jwtConfigurer
           .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter(scope_cc))
        ));
```
- call api [/secured/resource-1](https://lekhrajdinkar-postman-team.postman.co/workspace/microservice-java~734a0225-95ea-4e29-b76b-970c95475790/request/5083106-5539bfbb-9c72-4042-aafb-0d9beb5d92dc?action=share&creator=5083106)

#### ▶️Method-level Security 
- RBAC (claim > role)
- attribute-based checks (claim > any-attribute)
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
### C. ✔️CORS & Browser Security
- CORS config with DB: add @Bean : `WebMvcConfigurer` | check above

### D. Custom Filter/s
- @Order(1) @Bean : `SecurityFilterChain`
- **scenario-1** : manually chain with other filter
- **scenario-2**
  - filter-1 bean  @Order(1)  for url-pattern-1, do form-login
  - filter-2 bean  @Order(2)  for url-pattern-2, do Oauth-JWT-validation

### E. Rate limiting per IP/client
- Inputs validated (JSR-303), request size limits, rate limiting per IP/client
- `bucket4j` or `resilience4j`
- **headers**
  ```
  - `X-Content-Type-Options`
  - `X-Frame-Options`
  - `Content-Security-Policy` 
  ```

### F. TLS / SSL 
- HTTPS, mTLS option, cert rotation
- header: `Strict-Transport-Security`
- k8s | ngInx 👈🏻
- **openssl** s_client

### G. ✔️Secrets Management & Config
- AWS Secrets Manager
- AWS SSM paramStore

### H. Resiliency
- ensure security layers do not cause unacceptable latency; 
- demonstrate circuit breaker for **Okta auth server downtime**
