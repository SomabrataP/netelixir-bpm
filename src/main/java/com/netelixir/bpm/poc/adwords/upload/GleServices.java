/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc.adwords.upload;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201710.ch.CustomerSyncServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupBidModifierServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupFeedServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.BudgetServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignFeedServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.ConstantDataServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.FeedItemServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.FeedMappingServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.FeedServiceInterface;
//import com.google.api.ads.adwords.axis.v201603.cm.MutateJobServiceInterface;//MutateJobServiceInterface is Removed in API Upgradation V201603
import com.google.api.ads.adwords.axis.v201710.cm.ReportDefinitionServiceInterface;
import com.google.api.ads.adwords.axis.v201710.mcm.CustomerServiceInterface;
import com.google.api.ads.adwords.axis.v201710.mcm.ManagedCustomerServiceInterface;
import com.google.api.ads.adwords.axis.v201710.o.TargetingIdeaServiceInterface;
import com.google.api.ads.adwords.axis.v201710.o.TrafficEstimatorServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;

public class GleServices{
    
    AdWordsSession adwordsSession;
    private static AdWordsServices adWordsServices;
    public static final int MANAGED_CUSTOMER_SERVICE = 1;
    public static final int CAMPAIGN_SERVICE = 2;
    public static final int ADGROUP_SERVICE = 3;
    public static final int ADGROUP_AD_SERVICE = 4;
    public static final int ADGROUP_CRITERION_SERVICE = 5;
    public static final int ADGROUP_BID_MODIFIER_SERVICE = 6;
    public static final int TRAFFIC_ESTIMATOR_SERVICE = 7;
    public static final int TARGETING_IDEA_SERVICE = 8;
    public static final int REPORT_SERVICE = 9;
    public static final int INFO_SERVICE = 10;
    public static final int CAMPAIGN_CRITERION_SERVICE = 11;
    public static final int CAMPAIGN_TARGET_SERVICE = 12;
    public static final int CAMPAIGN_AD_EXTENSION_SERVICE = 13;
    public static final int GEO_LOCATION_SERVICE = 14;
    public static final int CUSTOMER_SERVICE = 15;
    public static final int BUDGET_SERVICE = 16;
    public static final int MUTATEJOB_SERVICE = 19;
    public static final int CAMPAIGN_FEED_SERVICE = 17;
    public static final int FEED_ITEM_SERVICE = 18;
    public static final int FEED_SERVICE = 20;
    public static final int FEED_MAPPING_SERVICE = 21;
    public static final int SHARED_BID_STRATEGIES_SERVICE = 22;
    public static final int ADGROUP_FEED_SERVICE = 23;
    public static final int CONSTANT_DATA_SERVICE = 24;
    public static final int CUSTOMER_SYNC_SERVICE = 25;

    public void setAdwordsSession(AdWordsSession adwordsSession) {
        this.adwordsSession = adwordsSession;
    }
    public static synchronized AdWordsServices createAdWordsServicesSingleton() {
        if (adWordsServices == null) {
            adWordsServices = new AdWordsServices();
        }
        return adWordsServices;
    }
    
    public Object getService(int serviceId) {
        try {
       
            switch (serviceId) {    
                case MANAGED_CUSTOMER_SERVICE:
                    ManagedCustomerServiceInterface accountService = adWordsServices.get(adwordsSession, ManagedCustomerServiceInterface.class);
                    return accountService;
                case CAMPAIGN_SERVICE:
                    CampaignServiceInterface campaignService = adWordsServices.get(adwordsSession, CampaignServiceInterface.class);
                    return campaignService;
                case CAMPAIGN_TARGET_SERVICE:
                    return null;
                case ADGROUP_SERVICE:
                    AdGroupServiceInterface adgroupService = adWordsServices.get(adwordsSession, AdGroupServiceInterface.class);
                    return adgroupService;
                case ADGROUP_AD_SERVICE:
                    AdGroupAdServiceInterface creativeService = adWordsServices.get(adwordsSession, AdGroupAdServiceInterface.class);
                    return creativeService;
                case ADGROUP_CRITERION_SERVICE:
                    AdGroupCriterionServiceInterface criterionService = adWordsServices.get(adwordsSession, AdGroupCriterionServiceInterface.class);
                    return criterionService;
                case ADGROUP_BID_MODIFIER_SERVICE:
                    AdGroupBidModifierServiceInterface adGroupBidService = adWordsServices.get(adwordsSession, AdGroupBidModifierServiceInterface.class);
                    return adGroupBidService;
                case CAMPAIGN_CRITERION_SERVICE:
                    CampaignCriterionServiceInterface cmpCriterionService = adWordsServices.get(adwordsSession, CampaignCriterionServiceInterface.class);
                    return cmpCriterionService;
                case TARGETING_IDEA_SERVICE:
                    TargetingIdeaServiceInterface keyToolSer = adWordsServices.get(adwordsSession, TargetingIdeaServiceInterface.class);
                    return keyToolSer;
                case REPORT_SERVICE:
                    ReportDefinitionServiceInterface repSer = adWordsServices.get(adwordsSession, ReportDefinitionServiceInterface.class);
                    return repSer;
                case INFO_SERVICE:
                    return null;
                case TRAFFIC_ESTIMATOR_SERVICE:
                    TrafficEstimatorServiceInterface estimatorInterface = adWordsServices.get(adwordsSession, TrafficEstimatorServiceInterface.class);
                    return estimatorInterface;
                case CAMPAIGN_AD_EXTENSION_SERVICE:
                    FeedServiceInterface campaignAdExtensionService = adWordsServices.get(adwordsSession, FeedServiceInterface.class);
                    return campaignAdExtensionService;
                case CUSTOMER_SERVICE:
                    CustomerServiceInterface customerService = adWordsServices.get(adwordsSession, CustomerServiceInterface.class);
                    return customerService;
                case BUDGET_SERVICE:
                    BudgetServiceInterface budgetService = adWordsServices.get(adwordsSession, BudgetServiceInterface.class);
                    return budgetService;
                    //MUTATEJOB_SERVICE is Removed in API Upgradation V201603 version
//                case MUTATEJOB_SERVICE:
//                    MutateJobServiceInterface mutateJobService = adWordsServices.get(adwordsSession, MutateJobServiceInterface.class);
//                    return mutateJobService;
                case CAMPAIGN_FEED_SERVICE:
                    CampaignFeedServiceInterface campaignFeedService = adWordsServices.get(adwordsSession, CampaignFeedServiceInterface.class);
                    return campaignFeedService;
                case FEED_ITEM_SERVICE:
                    FeedItemServiceInterface feedItemService = adWordsServices.get(adwordsSession, FeedItemServiceInterface.class);
                    return feedItemService;
                case FEED_SERVICE:
                    FeedServiceInterface feedService = adWordsServices.get(adwordsSession, FeedServiceInterface.class);
                    return feedService;
                case FEED_MAPPING_SERVICE:
                    FeedMappingServiceInterface feedMappingService = adWordsServices.get(adwordsSession, FeedMappingServiceInterface.class);
                    return feedMappingService;
                case SHARED_BID_STRATEGIES_SERVICE:
                    BiddingStrategyServiceInterface sharedBidStService = adWordsServices.get(adwordsSession, BiddingStrategyServiceInterface.class);
                    return sharedBidStService;
                case ADGROUP_FEED_SERVICE:
                    AdGroupFeedServiceInterface adGroupFeedService = adWordsServices.get(adwordsSession, AdGroupFeedServiceInterface.class);
                    return adGroupFeedService;
                case CONSTANT_DATA_SERVICE:
                    ConstantDataServiceInterface constantDataService = adWordsServices.get(adwordsSession, ConstantDataServiceInterface.class);
                    return constantDataService;
                case CUSTOMER_SYNC_SERVICE:
                    CustomerSyncServiceInterface customerSyncService = adWordsServices.get(adwordsSession, CustomerSyncServiceInterface.class);
                    return customerSyncService;
            }
        } catch (Exception ex) {
            System.out.println("Exception in creating AdWords Service"+ ex);
        }
        return null;
    }
}
