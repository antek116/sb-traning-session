package com.sbbetting.training.database.model;

import java.util.Date;

public class RefreshToken {

    private String token;
    private final String userId;
    private Date expirationDate;

    private RefreshToken(String userId,
                         String refreshToken,
                         Date expirationDate) {
        this.userId = userId;
        this.token = refreshToken;
        this.expirationDate = expirationDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static class Builder {

        private String userId;
        private String refreshToken;
        private Date expirationDate;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder withExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(userId, refreshToken, expirationDate);
        }
    }
}
