package securityApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OAuth2ServiceImpl
{
    @Autowired    private OAuth2AuthorizedClientManager authorizedClientManager;

    public String getAccessToken_and_parse(String regId)
    {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(regId ) // "cc" or "af_pkce"
                .principal("client")
                .build();

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient != null) {
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            log.info("Issued: " + accessToken.getIssuedAt().toString() + ", Expires:" + accessToken.getExpiresAt().toString());
            log.info("Scopes: " + accessToken.getScopes().toString());
            log.info("Token: " + accessToken.getTokenValue());
            return accessToken.getTokenValue();
        }

        throw new IllegalStateException("Failed to obtain access token");
    }
}
