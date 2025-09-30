package microservice.securityApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2Config
{
    //@Bean
    // ▶️  no need, properties already added, good enough
    ClientRegistration oktaClientRegistration_cc(
            @Value("${spring.security.oauth2.client.provider.cc.token-uri}") String token_uri,
            @Value("${spring.security.oauth2.client.registration.cc.client-id}") String client_id,
            @Value("${spring.security.oauth2.client.registration.cc.client-secret}") String client_secret, // get from aws-secret-manager 👈🏻
            @Value("${spring.security.oauth2.client.registration.cc.scope}") String scope,
            @Value("${spring.security.oauth2.client.registration.cc.authorization-grant-type}") String authorizationGrantType
    ) {
        return ClientRegistration
                .withRegistrationId("cc")
                .tokenUri(token_uri)
                .clientId(client_id)
                .clientSecret(client_secret)
                .scope(scope)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .build();
    }

}

