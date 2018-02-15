/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc;

import com.netelixir.bpm.poc.adwords.upload.AwKeywordUpdater;
import com.netelixir.bpm.poc.adwords.upload.KeywordStructure;
import com.netelixir.bpm.poc.model.AccountDetails;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author som
 */
public class BpmPoc {
    private static final Long seAccountId = 3749030311l;
    private static final Long campaignId = 112450888l;
    private static final Long adGroupId = 5288171488l;
    private static final String refreshToken = "1/D8_WcFRC_RMk1QEHcmjdp1ZG4AN8zfypoMMJHMUgSNg";
    
    public static void main(String args []){
        AwKeywordUpdater awKeywordUpdater = new AwKeywordUpdater();
        List<KeywordStructure> negKeywordList = createDummyKeywordList();
        AccountDetails accountDetails = createDummyAccountDetails();
        awKeywordUpdater.addAdGroupNegativeKeywordsInAdWords(negKeywordList, accountDetails);
    }
    
    public static List<KeywordStructure> createDummyKeywordList(){
        List<KeywordStructure> negKeywordList = new ArrayList();
        KeywordStructure k1 = new KeywordStructure();
        k1.setAdGroupId(adGroupId);
        k1.setCampaignId(campaignId);
        k1.setKeywordText("lenovo laptop");
        k1.setKeywordMatchType("exact");
        
        negKeywordList.add(k1);
        return negKeywordList;
    }
    
    public static AccountDetails createDummyAccountDetails(){
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setSeAccountId(seAccountId);
        accountDetails.setRefreshToken(refreshToken);
        return accountDetails;
    } 
}
