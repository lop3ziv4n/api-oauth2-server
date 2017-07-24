package ar.org.blb.security.administration.utility;

public class AccessToken {

    private String access_token;
    private String token_type;
    private String bearer;
    private String refresh_token;
    private Long expires_in;
    private String scope;

    public AccessToken() {
    }

    public AccessToken(String access_token, String token_type, String bearer, String refresh_token, Long expires_in, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.bearer = bearer;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
