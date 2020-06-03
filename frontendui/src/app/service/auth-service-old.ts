import { Injectable, Output } from '@angular/core';
import { OAuthService, JwksValidationHandler, AuthConfig } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
    // issuer: 'http://localhost:8801/zuul-auth',
    // loginUrl: 'http://localhost:8801/zuul-auth/oauth/authorize',
    loginUrl: 'http://localhost:8201/oauth/authorize',
    redirectUri: window.location.origin + '/home',
    requireHttps: false,
    // logoutUrl: 'http://127.0.0.1:3000/index',
    clientId: 'fsd-ng',
    responseType: 'token',
    scope: 'all',
    showDebugInformation: true,
    oidc: false
    // silentRefreshRedirectUri: 'http://127.0.0.1:3000/assets/html/silent-refresh.html'
};

// @Injectable({
//     providedIn: 'root'
// })
export class Auth2Service {

    constructor(private oauthService: OAuthService) { 
        this.oauthService.configure(authConfig);
        this.oauthService.setStorage(localStorage);
        this.oauthService.tokenValidationHandler = new JwksValidationHandler();
        this.oauthService.loadDiscoveryDocumentAndTryLogin({
            onTokenReceived: url => {
                //url: http://localhost:4200/callback#access_token=****&token_type=bearer&state=****&expires_in=863999
                console.log("****** AuthService onTokenReceived, url: ", url);
                window.parent.location.href = url.state;
            }
        });
        // this.oauthService.setupAutomaticSilentRefresh();
        console.log("****** AuthService constructor run, window.location.origin: ", window.location.origin);
    }

    identityValid() {
        console.log("****** AuthService identityValid run");
        if (this.oauthService.hasValidAccessToken()) {
          return true;
        }
        this.login();
        return false;
    }

    login() {
        this.oauthService.initImplicitFlow();
        // const href = window.location.href;
        // this.oauthService.initLoginFlow(href);
    }
    
    logout() {
        this.oauthService.logOut();
    }
    
    get givenName() {
        const claims = this.oauthService.getIdentityClaims();
        console.log("****** AuthService.givenName, claims: ", claims);
        if (!claims) {
          return null;
        }
        return claims['name'];
    }

    get accessToken() {
        console.log("****** AuthService.accessToken, accessToken: ", this.oauthService.getAccessToken());
        return this.oauthService.getAccessToken();
    }
}