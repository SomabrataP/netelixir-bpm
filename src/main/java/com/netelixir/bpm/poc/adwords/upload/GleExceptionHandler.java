/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc.adwords.upload;

import com.google.api.ads.adwords.axis.utils.v201710.SelectorBuilder;
import com.google.api.ads.adwords.axis.v201710.ch.CustomerChangeData;
import com.google.api.ads.adwords.axis.v201710.ch.CustomerSyncSelector;
import com.google.api.ads.adwords.axis.v201710.ch.CustomerSyncServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdPage;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupBidModifierOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupBidModifierReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupBidModifierServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionPage;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupPage;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.ApiError;
import com.google.api.ads.adwords.axis.v201710.cm.ApiException;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyOperation;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyPage;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.BudgetOperation;
import com.google.api.ads.adwords.axis.v201710.cm.BudgetReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.BudgetServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.Campaign;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionOperation;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionPage;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignOperation;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignPage;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.ConstantDataServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.DateTimeRange;
import com.google.api.ads.adwords.axis.v201710.cm.ListReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.Operation;
import com.google.api.ads.adwords.axis.v201710.cm.Page;
import com.google.api.ads.adwords.axis.v201710.cm.ProductBiddingCategoryData;
import com.google.api.ads.adwords.axis.v201710.cm.RateExceededError;
import com.google.api.ads.adwords.axis.v201710.cm.Selector;
import com.google.api.ads.adwords.axis.v201710.cm.SizeLimitError;
import com.google.api.ads.adwords.axis.v201710.o.TrafficEstimatorResult;
import com.google.api.ads.adwords.axis.v201710.o.TrafficEstimatorSelector;
import com.google.api.ads.adwords.axis.v201710.o.TrafficEstimatorServiceInterface;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author vishal
 */
public class GleExceptionHandler {
    private static final Log LOGGER = LogFactory.getLog(GleExceptionHandler.class);
    private int serviceId = 0;
    GleServices gleSer;

    public GleExceptionHandler(GleServices gleSer,int id) {
        this.gleSer = gleSer;
        this.serviceId = id;
    }

   public Page getPage(Selector selector) throws Exception{
        Page page = null;
        try {
            page = get(selector,serviceId);
        } catch (ApiException e) {
                for(ApiError er : e.getErrors()){
                    if(er instanceof RateExceededError){
                        try {
                            Thread.sleep(((RateExceededError)er).getRetryAfterSeconds());    // 30seconds
                            page = get(selector,serviceId);
                        } catch (Exception th) {
                            throw e;
                        }
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        return page;
    }
   public TrafficEstimatorResult getTrafficEstimate(TrafficEstimatorSelector selector) throws Exception{
         TrafficEstimatorResult result=null;
        try {
            result = getTrafficEstimate(selector,serviceId);
        } catch (ApiException e) {
                for(ApiError er : e.getErrors()){
                    if(er instanceof RateExceededError){
                        try {
                            Thread.sleep(((RateExceededError)er).getRetryAfterSeconds());    // 30seconds
                            result = getTrafficEstimate(selector,serviceId);
                        } catch (Exception th) {
                            throw e;
                        }
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        return result;
    }
   public ProductBiddingCategoryData[] getProductBiddingCategoryData(String country) throws Exception{
         ProductBiddingCategoryData[] result=null;
         Selector selector = new SelectorBuilder()
                        .equals("Country", country)
                        .build();
        try {
            result = getProductBiddingCategoryData(selector,serviceId);
        } catch (ApiException e) {
                for(ApiError er : e.getErrors()){
                    if(er instanceof RateExceededError){
                        try {
                            Thread.sleep(((RateExceededError)er).getRetryAfterSeconds());    // 30seconds
                            result = getProductBiddingCategoryData(selector,serviceId);
                        } catch (Exception th) {
                            throw e;
                        }
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        return result;
    }

    public Page get(Selector selector,int serviceId) throws Exception{
        Page page = null;
        try {
                Object service = gleSer.getService(serviceId);
                switch (serviceId) {
                case GleServices.ADGROUP_CRITERION_SERVICE:
                    AdGroupCriterionPage adGrpCriPage = ((AdGroupCriterionServiceInterface)service).get(selector);
                    return adGrpCriPage;
                case GleServices.ADGROUP_SERVICE:
                    AdGroupPage adGrpPage = ((AdGroupServiceInterface)service).get(selector);
                    return adGrpPage;
                case GleServices.CAMPAIGN_SERVICE:
                    CampaignPage cmpPage = ((CampaignServiceInterface)service).get(selector);
                    return cmpPage;
                case GleServices.ADGROUP_AD_SERVICE:
                    AdGroupAdPage adGroupAdPage = ((AdGroupAdServiceInterface)service).get(selector);
                    return adGroupAdPage;
                case GleServices.CAMPAIGN_CRITERION_SERVICE:
                    CampaignCriterionPage cmpCripage = ((CampaignCriterionServiceInterface)service).get(selector);
                    return cmpCripage;
                case GleServices.SHARED_BID_STRATEGIES_SERVICE:
                    BiddingStrategyPage bidStrpage = ((BiddingStrategyServiceInterface)service).get(selector);
                    return bidStrpage;               
            }
        } catch (Exception e) {
            throw e;
        }
        return page;
    }
    public TrafficEstimatorResult getTrafficEstimate(TrafficEstimatorSelector selector,int serviceId) throws Exception{
       TrafficEstimatorResult result=null;
        try {     
            
                TrafficEstimatorServiceInterface estimatorInterface = (TrafficEstimatorServiceInterface) gleSer.getService(serviceId);
                result = estimatorInterface.get(selector);       
            
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
    
    /**
     *
     * @param selector
     * @param country
     * @param serviceId
     * @return
     * @throws Exception
     */
    public ProductBiddingCategoryData[] getProductBiddingCategoryData(Selector selector,int serviceId) throws Exception{
       ProductBiddingCategoryData[] results=null;
        try {     
            
              ConstantDataServiceInterface constantDataService =
                        (ConstantDataServiceInterface) gleSer.getService(serviceId);

                results = constantDataService.getProductBiddingCategoryData(selector);    
            
        } catch (Exception e) {
            throw e;
        }
        return results;
    }

    public ListReturnValue mutateOperations(Operation[]  operations){
        ListReturnValue response = null;
        try {
            response = mutate(operations);
            return response;
        } catch (ApiException ex) {
            LOGGER.error("Exception in Mutate", ex);
        }
        return response;
    }

    private ListReturnValue mutate(Operation[]  operations) throws ApiException{
        ListReturnValue response = null;
        try {
                Object service = gleSer.getService(serviceId);
                switch (serviceId) {
                    case GleServices.CAMPAIGN_SERVICE:
                        CampaignReturnValue cmpRes = ((CampaignServiceInterface)service).mutate((CampaignOperation[])operations);
                        return cmpRes;
                    case GleServices.BUDGET_SERVICE:
                        BudgetReturnValue budRes = ((BudgetServiceInterface)service).mutate((BudgetOperation[])operations);
                        return budRes;
                    case GleServices.ADGROUP_SERVICE:
                        AdGroupReturnValue adgrpRes = ((AdGroupServiceInterface)service).mutate((AdGroupOperation[])operations);
                        return adgrpRes;
                    case GleServices.CAMPAIGN_CRITERION_SERVICE:
                        CampaignCriterionReturnValue cmpCriRes = ((CampaignCriterionServiceInterface)service).mutate((CampaignCriterionOperation[])operations);
                        return cmpCriRes;
                    case GleServices.ADGROUP_CRITERION_SERVICE:
                        AdGroupCriterionReturnValue adGrpCriRes = ((AdGroupCriterionServiceInterface)service).mutate((AdGroupCriterionOperation[])operations);
                        return adGrpCriRes;
                    case GleServices.ADGROUP_AD_SERVICE:
                        AdGroupAdReturnValue adGroupAdRes = ((AdGroupAdServiceInterface)service).mutate((AdGroupAdOperation[])operations);
                        return adGroupAdRes;
                    case GleServices.SHARED_BID_STRATEGIES_SERVICE:
                        BiddingStrategyReturnValue sharedBidStrRes = ((BiddingStrategyServiceInterface)service).mutate((BiddingStrategyOperation[])operations);
                        return sharedBidStrRes;
                    case GleServices.ADGROUP_BID_MODIFIER_SERVICE:
                        AdGroupBidModifierReturnValue adGroupBidRes = ((AdGroupBidModifierServiceInterface)service).mutate((AdGroupBidModifierOperation[])operations);
                        return adGroupBidRes;
                }
        }catch (ApiException e) {
            for(ApiError er : e.getErrors()){
                if(er instanceof RateExceededError){
                    try {
                        Thread.sleep(((RateExceededError)er).getRetryAfterSeconds());    // 30seconds
                        response = mutate(operations);
                    } catch (InterruptedException ex) {
                        LOGGER.error("Exception in retrying for failed mutation", ex);
                    }                          
                }else{
                    throw e;
                }
            }
        }catch(UndeclaredThrowableException e){
            //TODO: Handle if thre is any authentication problem.
            LOGGER.error("Exception in mutation " + e.getCause().getMessage(), e);
            LOGGER.error("Message: " + e.getMessage());
        }catch (Exception e) {
            LOGGER.error("Exception in mutation", e);
        }
        return response;
    }

    public ListReturnValue[] mutateOperationHandlingSizeLimit(Operation[]  operations) throws Exception{
        ListReturnValue response;
        ListReturnValue[] list = null;
        try {
           response = mutate(operations);
           return (new ListReturnValue[]{response});
        } catch (ApiException e) {
            for(ApiError er : e.getErrors()){
                if(er instanceof RateExceededError){
                    try {
                        Thread.sleep(((RateExceededError)er).getRetryAfterSeconds());    // 30seconds
                        mutate(operations);
                    } catch (InterruptedException | ApiException th) {
                        throw e;
                    }
                }else if(er instanceof SizeLimitError){
                    try {
                        int operLength= operations.length;
                           int count = 0;
                           int operCount = 0;
                           ArrayList<ListReturnValue> al = new ArrayList<>();
                           Operation[] operation = new Operation[500];
                           for(int k=0;k<operLength;k++){
                              operation[k] = operations[k];
                              count++;
                              operCount++;
                              if(count==500 || operCount==operLength){
                                    ListReturnValue retVal = mutate(operation);
                                    al.add(retVal);
                                   count=0;
                                   operation = new AdGroupCriterionOperation[500];
                              }
                           }
                           list = (ListReturnValue[])al.toArray(new ListReturnValue[al.size()]);
                    } catch (Exception th) {
                        throw th;
                    }
                }else{
                    throw e;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }
    
    /**
     *
     * @param selector
     * @return
     */
    public List<Long> getCampaignIds(Selector selector) {
        // Get a list of all campaign IDs.
        List<Long> campaignIds = new ArrayList<>();
        try {
            CampaignServiceInterface campaignService = (CampaignServiceInterface) gleSer.getService(serviceId);
            CampaignPage campaigns = campaignService.get(selector);
            if (campaigns.getEntries() != null) {
                for (Campaign campaign : campaigns.getEntries()) {
                    campaignIds.add(campaign.getId());
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return campaignIds;
    }

    /**
     *
     * @param selector
     * @return
     */
    public CampaignPage getSECampaigns(Selector selector) {
        CampaignPage campaigns = new CampaignPage();
        try {
            CampaignServiceInterface campaignService = (CampaignServiceInterface) gleSer.getService(serviceId);
            campaigns = campaignService.get(selector);
            
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return campaigns;
    }
    
    /**
     *
     * @param dateTimeRange
     * @param campaignIds
     * @return 
     */
    public CustomerChangeData getAccountChanges(DateTimeRange dateTimeRange,Set<Long> campaignIds){
    CustomerChangeData accountChanges=null;
    // Create selector.
    CustomerSyncSelector customerSyncSelector = new CustomerSyncSelector();
    customerSyncSelector.setDateTimeRange(dateTimeRange);
    customerSyncSelector
        .setCampaignIds(ArrayUtils.toPrimitive(campaignIds.toArray(new Long[] {})));
    CustomerSyncServiceInterface customerSyncService =(CustomerSyncServiceInterface) gleSer.getService(serviceId);
        try {
            // Get all account changes for campaign.
            accountChanges = customerSyncService.get(customerSyncSelector);
        } catch (RemoteException ex) {
            LOGGER.error(ex);
        }
        return accountChanges;
    }
}
