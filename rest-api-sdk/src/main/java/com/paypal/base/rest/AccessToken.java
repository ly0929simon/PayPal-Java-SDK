package com.paypal.base.rest;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class AccessToken {

    private String accessToken;
    private long expires = 0;

    /**
     * Specifies how long this token can be used for placing API calls. The
     * remaining lifetime is given in seconds.
     *
     * @return remaining lifetime of this access token in seconds
     */
    public long expiresIn() {
        return expires - new java.util.Date().getTime() / 1000;
    }

}
