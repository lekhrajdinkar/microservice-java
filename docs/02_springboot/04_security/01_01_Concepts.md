## Security Concepts
### 🔶LDAP (old)
- https://chatgpt.com/c/5865254e-a777-416f-ad16-8e40df050c04

### 🔶SessionID (old)

### ✔️JWT (token format)
- https://jwt.io/introduction/
- header
- body/payload
    - **claims** (statement about user and additional info)
        - `Scope`
- footer

### ✔️Diff App architecture
- server-side microservice applications (SpringMVC, JSP) :
- browser-based applications / SPA :
- native/mobile apps :
- connected devices (M2M, lambda) :

### ✔️Authentication
- OIDC
- Token based Authentication.  `ID-Token`
- extension over OAuth2.

### ✔️Delegated Authorization (OAuth2)
- [next page, 00_OAuth_2.0.md](00_OAuth_2.0.md)
- OAuth2.1 (PKCE mandatory)
- `Access-token`
- App(api/spa) <--> **OKTA's OAuth server** <--integrated--> **LDAP**
  - authentication-rule
  - app integration (issuer, scope, etc)
  - app/api - RBAC implemented (app level, based on scope)
  - DB lib: `spring-boot-starter-oauth2-client` + `spring-security-oauth2-jose`

### ✔️Encryption
- at rest / at transit
- TLS/SSL
- certificate
- JKS (trust / identity)

### ✔️SSO
- SAML
- https://saml-doc.okta.com/SAML_Docs/How-to-Configure-SAML-2.0-for-Amazon-Web-Service
- https://help.okta.com/en-us/content/topics/deploymentguides/aws/aws-configure-aws-app.html

### AWS solutions 
- [SE - AWS notes - security 📚](https://github.com/lekhrajdinkar/solution-engineer/tree/main/docs/01_aws/06_Security)
- ACM, KMS, IAM
- WAF
- etc
