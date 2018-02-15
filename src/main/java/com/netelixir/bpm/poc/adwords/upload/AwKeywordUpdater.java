/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc.adwords.upload;

import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterion;
import com.google.api.ads.adwords.axis.v201710.cm.NegativeAdGroupCriterion;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.ApiError;
import com.google.api.ads.adwords.axis.v201710.cm.Keyword;
import com.google.api.ads.adwords.axis.v201710.cm.KeywordMatchType;
import com.google.api.ads.adwords.axis.v201710.cm.Operator;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.netelixir.bpm.poc.Authenticator;
import com.netelixir.bpm.poc.model.AccountDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author som
 */
public class AwKeywordUpdater{
    protected static final Pattern operationIndexPattern 
            = Pattern.compile("^.*operations\\[(\\d+)\\].*$");
    protected static final int BATCH_SIZE = 2000;
    
    public List<KeywordStructure> addAdGroupNegativeKeywordsInAdWords(
            List<KeywordStructure> negKeywordList, AccountDetails accountDetails){
        //Data Validation
        List<KeywordStructure> validNegKeywordList  
                = validateAddNewNegativeKeywordData(negKeywordList);
        
        // Create Operations
        List<AdGroupCriterionOperation> operations 
                = createOperationForNegativeKeyword(validNegKeywordList);
        
        // Update Keywords in AdWords and get back updated keywords only
        List<KeywordStructure> updatedKeywords 
                = addNewKeywordsInBatch(operations, accountDetails, validNegKeywordList);

        return updatedKeywords;
    }
    
    private List<KeywordStructure> validateAddNewNegativeKeywordData(
        // keyword text is not blank, #character <= 80, valid match type
        List<KeywordStructure> keywords){
            return keywords.stream().filter((keyword)-> (keyword.getAdGroupId()!= null 
                && keyword.getAdGroupId() != 0
                && keyword.getKeywordText() != null && !keyword.getKeywordText().trim().equals("")
                && keyword.getKeywordText().length() <= 80 && keyword.getKeywordMatchType() != null 
                && (keyword.getKeywordMatchType().equalsIgnoreCase("broad") 
                    || keyword.getKeywordMatchType().equalsIgnoreCase("phrase")
                    || keyword.getKeywordMatchType().equalsIgnoreCase("exact")))
        ).collect(Collectors.toList());   
    }  
    
    private List<AdGroupCriterionOperation> createOperationForNegativeKeyword(
            List<KeywordStructure> keywordList){ 
        List<AdGroupCriterionOperation> operations = new ArrayList<>();
        for (KeywordStructure keywordStructure : keywordList) {
            // Create keyword
            Keyword keyword = new Keyword();
            keyword.setText(keywordStructure.getKeywordText());
            keyword.setMatchType(KeywordMatchType.fromString(keywordStructure.getKeywordMatchType().toUpperCase()));
            
            // Create NegativeAdGroupCriterion
            NegativeAdGroupCriterion negAdGrpCri = new NegativeAdGroupCriterion();
            negAdGrpCri.setAdGroupId(keywordStructure.getAdGroupId());
            negAdGrpCri.setCriterion(keyword);
            
            // Create AdGroupCriterionOperation
            AdGroupCriterionOperation operation = new AdGroupCriterionOperation();
            if(keywordStructure.getStatus() != null && 
                    keywordStructure.getStatus().equals(KeywordStructure.REMOVED)){
                operation.setOperator(Operator.REMOVE);
            }else{
                operation.setOperator(Operator.ADD);
            }
            operation.setOperand(negAdGrpCri);
            
            operations.add(operation);
        }
        return operations;
    }
    
    /**
     * Adds new keywords to AdWords
     * @param operations
     * @param accountDetails
     * @param keywordList
     * @return List of successfully updated keywords
     */
    private  List<KeywordStructure> addNewKeywordsInBatch(
            List<AdGroupCriterionOperation> operations, AccountDetails accountDetails,
            List<KeywordStructure> keywordList){
        List<KeywordStructure> addedKeywordList = new ArrayList<>();
        if(operations != null && !operations.isEmpty()){  
            // Authenticate and get handler
            GleExceptionHandler handler = createHandler(accountDetails, 
                    GleServices.ADGROUP_CRITERION_SERVICE);  
            
            // Create batch of operation and mutate
            int firstIndex = 0;
            int lastIndex = 0;
            while(lastIndex != operations.size()){
                lastIndex = (firstIndex + BATCH_SIZE) > operations.size()? 
                        operations.size() : firstIndex + BATCH_SIZE;
                AdGroupCriterionOperation[] operationArray = operations.subList(
                        firstIndex, lastIndex).toArray(new AdGroupCriterionOperation[lastIndex - firstIndex]);

                try {
                    AdGroupCriterionReturnValue adGrpCriRetVal 
                            = (AdGroupCriterionReturnValue) handler.mutateOperations(operationArray);
                    
                    if(adGrpCriRetVal.getValue() != null){
                        // Get Updated Keywords from result, add Id provided by AdWords
                        for (AdGroupCriterion adGroupCriterionResult :  adGrpCriRetVal.getValue()) {
                            if (adGroupCriterionResult.getCriterion() != null) {
                                Keyword resultKeyword = (Keyword) adGroupCriterionResult.getCriterion();
                                KeywordStructure keyword = getObjectByKeywordText(keywordList, 
                                        adGroupCriterionResult.getAdGroupId(), resultKeyword.getText(), resultKeyword.getMatchType());
                                keyword.setKeywordId(adGroupCriterionResult.getCriterion().getId());
                                addedKeywordList.add(keyword);
                            }
                        }
                        if(adGrpCriRetVal.getValue().length > 0){
                            System.out.println("Added " + adGrpCriRetVal.getValue().length 
                                + " keywords in account " + accountDetails.getSeAccountId());
                        }
                    }
                    // Log details about failed Keywords
                    if(adGrpCriRetVal.getPartialFailureErrors() != null){
                        logFailedKeywordsWhileAdding(adGrpCriRetVal, operationArray, keywordList);
                    }
                } catch (Exception ex) {
                    System.out.println("Exception in adding new Keywords in account " 
                            + accountDetails.getSeAccountId() + " firstIndex: "
                            +firstIndex+" lastIndex: "+lastIndex +  ex);
                }

                firstIndex = lastIndex;
            }
        }
        return addedKeywordList;
    }
    
    protected GleExceptionHandler createHandler(AccountDetails accountDetails, int adwordsServiceId){
        GleExceptionHandler handler = null;
        // Construct an AdWordsSession.
        AdWordsSession session;
        try {
            session = Authenticator.getAdWordsSession(accountDetails.getSeAccountId().toString(), accountDetails.getRefreshToken());
        
            // Enable partial failure.
            session.setPartialFailure(true);

            //  Set GleService
            GleServices gleServices = new GleServices();
            gleServices.setAdwordsSession(session);
            GleServices.createAdWordsServicesSingleton();

            handler = new GleExceptionHandler(gleServices, adwordsServiceId);
        } catch (OAuthException | ValidationException ex) {
            System.out.println("Exception in creating adwords mutation handler");
            ex.printStackTrace();
        }catch (Exception ex) {
            System.out.println("Exception in creating adwords mutation handler");
            ex.printStackTrace();
        }
        return handler;
    }
    
    private KeywordStructure getObjectByKeywordText(List<KeywordStructure> keywordList, 
            long seAdGrpId, String key, KeywordMatchType type) {
        KeywordStructure keyword = null;
        Iterator it;
        if(keywordList!=null && !keywordList.isEmpty()){
            it = keywordList.iterator();
            boolean found = false;
            while (it.hasNext() && !found) {
                KeywordStructure gleKeyInf = (KeywordStructure) it.next();
                
                // Check AdGroup Id, Keyword Text and Match Type
                if (gleKeyInf.getAdGroupId() == seAdGrpId && gleKeyInf.getKeywordText()
                        .equalsIgnoreCase(key) && gleKeyInf.getKeywordMatchType().equalsIgnoreCase(type.getValue())) {
                    keyword = gleKeyInf;
                    found = true;
                }
            }
        }
        return keyword;
    }
    private void logFailedKeywordsWhileAdding(AdGroupCriterionReturnValue adGrpCriRetVal, 
            AdGroupCriterionOperation[] operationArray, List<KeywordStructure> keywordList){
        for (ApiError apiError : adGrpCriRetVal.getPartialFailureErrors()) {
            Matcher matcher = operationIndexPattern.matcher(apiError.getFieldPath());
            if (matcher.matches()) {
                int operationIndex = Integer.parseInt(matcher.group(1));
                AdGroupCriterion adGroupCriterion = operationArray[operationIndex].getOperand();
                Keyword resultKeyword = (Keyword)adGroupCriterion.getCriterion();
                KeywordStructure failedKeyword = getObjectByKeywordText(
                        keywordList, adGroupCriterion.getAdGroupId(), resultKeyword.getText(), resultKeyword.getMatchType());

                System.out.println("New Keyword / Neg Keyword Addition failed in " + failedKeyword.getAccountId() 
                        + " CamId: " + failedKeyword.getCampaignId() + " AdGroupId: " + failedKeyword.getAdGroupId()
                        + " Keyword: " + failedKeyword.getKeywordText() + "\nReason: " + apiError.getErrorString());
                //TODO: Check Possibilities of retry by modifying the keyword
            } else {
                System.out.println("New Keyword / Neg Keyword Addition failed " + "\nReason: "
                        + apiError.getErrorString());
            }
        }
    }

}
