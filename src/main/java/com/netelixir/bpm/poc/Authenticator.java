/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc;

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.client.auth.oauth2.Credential;

/**
 *
 * @author som
 */
public class Authenticator {
    public static String REFRESH_TOKEN = "ADD_HERE";
    public static String CLIENT_ID = "ADD HERE";
    public static String CLIENT_SECRET = "ADD HERE";
    public static String ADWORDS_CID = "ADD HERE";
    public static String DEVELOPER_TOKEN = "ADD HERE";

    public static AdWordsSession getAdWordsSession() throws Exception {
        // Generate a refreshable OAuth2 credential.
        Credential oAuth2Credential = new OfflineCredentials.Builder()
                .forApi(OfflineCredentials.Api.ADWORDS)
                .withClientSecrets(CLIENT_ID, CLIENT_SECRET)
                .withRefreshToken(REFRESH_TOKEN)
                .build()
                .generateCredential();

        AdWordsSession session = new AdWordsSession.Builder()
                .fromFile()
                .withOAuth2Credential(oAuth2Credential)
                .withClientCustomerId(ADWORDS_CID)
                .withDeveloperToken(DEVELOPER_TOKEN).build();
        return session;
    }
    
    public static AdWordsSession getAdWordsSession(String adWordsCID, String refreshToken) throws Exception {
        // Generate a refreshable OAuth2 credential.
        Credential oAuth2Credential = new OfflineCredentials.Builder()
                .forApi(OfflineCredentials.Api.ADWORDS)
                .withClientSecrets(CLIENT_ID, CLIENT_SECRET)
                .withRefreshToken(refreshToken)
                .build()
                .generateCredential();

        AdWordsSession session = new AdWordsSession.Builder()
                .fromFile()
                .withOAuth2Credential(oAuth2Credential)
                .withClientCustomerId(adWordsCID)
                .withDeveloperToken(DEVELOPER_TOKEN).build();
        return session;
    }
}
