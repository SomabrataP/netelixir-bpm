/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author som
 */

public class AccountDetails implements Serializable {
    public final static byte STATUS_ENABLED = 1;
    public final static byte STATUS_DISABLED = 0;
    public final static byte WEEKLY_REPORT_ENABLED = 1;
    public final static byte WEEKLY_REPORT_DISABLED = 0;

    private Long ppcId;

    private Long seAccountId;

    private Long clientId;

    private String accessToken;

    private String refreshToken;

    private Byte enabled;

    private Byte searchEngineId;
    
    private String currencyCode;
    
    private String timezoneId;
    
    private BigDecimal timediff;
    
    private String  currencySymbol;
    
    public Long getSeAccountId() {
        return seAccountId;
    }

    public void setSeAccountId(Long seAccountId) {
        this.seAccountId = seAccountId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getPpcId() {
        return ppcId;
    }

    public void setPpcId(Long ppcId) {
        this.ppcId = ppcId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    public Byte getSearchEngineId() {
        return searchEngineId;
    }

    public void setSearchEngineId(Byte searchEngineId) {
        this.searchEngineId = searchEngineId;
    }          

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    public BigDecimal getTimediff() {
        return timediff;
    }

    public void setTimediff(BigDecimal timediff) {
        this.timediff = timediff;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}
