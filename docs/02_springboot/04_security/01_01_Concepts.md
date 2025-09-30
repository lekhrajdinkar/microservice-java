## ✅ POCs
- [security App - readme.md](../../../src/main/java/microservice/securityApp/readme.md)

## Diff App architecture
- server-side microservice applications (SpringMVC, JSP)
- browser-based applications / SPA 
- native/mobile apps
- connected devices (M2M, lambda) 

--- 
## Security Concepts
### 🔶LDAP (old)
- [01_03_LDAP.md](01_03_LDAP.md)

### 🔶SessionID (old)

### ✔️JWT (token format)
- https://jwt.io/introduction/
- header
- body/payload
    - **claims** (statement about user and additional info)
        - `Scope`
- footer

### ✔️Authentication
- OIDC
- Token based Authentication.  `ID-Token`
- extension over OAuth2.
- **MFA** / Multi-factor Authentication : platform(Security) team configures.

### ✔️Authorization
- resource access based on role. **RBAC**
- Restricting access to microservice resources based on **URL patterns**
- **OAuth2**

### ✔️Delegated Authorization (OAuth2)
- [next page, 00_OAuth_2.0.md](../../../src/main/java/microservice/securityApp/README_OAuth2.md)
- OAuth2.1 (PKCE mandatory)
- `Access-token`
- App(api/spa) <--> **OKTA's OAuth server** <--integrated--> **LDAP**
  - authentication-rule
  - app integration (issuer, scope, etc)
  - app/api - RBAC implemented (app level, based on scope)
  - DB lib: `spring-boot-starter-oauth2-client` + `spring-security-oauth2-jose`

### ✔️Encryption 👈🏻👈🏻
- at **rest** / at **transit**
- **TLS/SSL**
- **certificate** 
- JKS (trust / identity)

### ✔️SSO
- SAML
- https://saml-doc.okta.com/SAML_Docs/How-to-Configure-SAML-2.0-for-Amazon-Web-Service
- https://help.okta.com/en-us/content/topics/deploymentguides/aws/aws-configure-aws-app.html

### ✔️AWS solutions 
- [SE - AWS notes - security 📚](https://github.com/lekhrajdinkar/solution-engineer/tree/main/docs/01_aws/06_Security)
- ACM, KMS, IAM
- WAF
- Safes: AWS-secret-manager | CyberArk (onPrem)

### ✔️CORS
- Cross-Origin Resource Sharing

### ✔️Common Attacks 👈🏻👈🏻
- XSS
- CSRF 


