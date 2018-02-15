/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc.adwords.upload;

import com.google.common.collect.Lists;
import com.netelixir.bpm.poc.util.GoogleUtil;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author vishal
 */

public class KeywordStructure extends Report implements Serializable{
    public static final String ENABLED = "enabled";
    public static final String REMOVED = "removed";
    public static final String PAUSED = "paused"; 

  private Long keywordId;

  private BigDecimal qualityScore;

  private String keywordMatchType;

  private String keywordText;

  private String criteriaDestinationUrl;

  private Long adGroupId;

  private String adGroupName;
  
  private String adGroupStatus;

  private Long campaignId;

  private String campaignName;
  
  private String campaignStatus;

  private String status;

  private boolean negative;
  
  private BigDecimal maxCpc;

  private BigDecimal maxCpm;

  private String labels;

  private String firstPageCpc;

  private String topOfPageCpc;

  private String finalAppUrls;

  private String finalMobileUrls;

  private String finalUrls;

  private String trackingUrlTemplate;

  private String urlCustomParameters;

  protected BigDecimal avgPosition;

  /**
   * Hibernate needs an empty constructor
   */
  public KeywordStructure() {
  }

  public KeywordStructure(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

  public BigDecimal getQualityScoreAsBigDecimal() {
    return qualityScore;
  }

  public String getQualityScore() {
    return GoogleUtil.formatAsReadable(qualityScore);
  }

  public void setQualityScore(BigDecimal qualityScore) {
    this.qualityScore = qualityScore;
    if(qualityScore.toString().equals("--")){
    this.qualityScore = BigDecimal.ZERO;    
    }
  }

  public void setQualityScore(String qualityScore) {
      if (qualityScore != null && qualityScore.trim().equals("--")) {
          this.qualityScore = BigDecimal.ZERO;
      } else {
          this.qualityScore = GoogleUtil.parseFromNumberString(qualityScore);
      }
  }

    public String getKeywordMatchType() {
        return keywordMatchType;
    }

    public void setKeywordMatchType(String keywordMatchType) {
        this.keywordMatchType = keywordMatchType;
    }

//    public String getCriteria() {
//        return criteria;
//    }
//
//    public void setCriteria(String criteria) {
//        this.criteria = criteria;
//    }

    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }
    
    public String getCriteriaDestinationUrl() {
        return criteriaDestinationUrl;
    }

    public void setCriteriaDestinationUrl(String criteriaDestinationUrl) {
        this.criteriaDestinationUrl = criteriaDestinationUrl;
    }

    public Long getAdGroupId() {
        return adGroupId;
    }

    public void setAdGroupId(Long adGroupId) {
        this.adGroupId = adGroupId;
    }

    public String getAdGroupName() {
        return adGroupName;
    }

    public void setAdGroupName(String adGroupName) {
        this.adGroupName = adGroupName;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

     public BigDecimal getMaxCpc() {
        return maxCpc;
    }

    public void setMaxCpc(BigDecimal maxCpc) {
        this.maxCpc = maxCpc;
    }

    public BigDecimal getMaxCpm() {
        return maxCpm;
    }

    public void setMaxCpm(BigDecimal maxCpm) {
        this.maxCpm = maxCpm;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getFirstPageCpc() {
        return firstPageCpc;
    }

    public void setFirstPageCpc(String firstPageCpc) {
        this.firstPageCpc = firstPageCpc;
    }

    public String getTopOfPageCpc() {
        return topOfPageCpc;
    }

    public void setTopOfPageCpc(String topOfPageCpc) {
        this.topOfPageCpc = topOfPageCpc;
    }

    public String getFinalAppUrls() {
        return finalAppUrls;
    }

    public void setFinalAppUrls(String finalAppUrls) {
        this.finalAppUrls = finalAppUrls;
    }

    public String getFinalMobileUrls() {
        return finalMobileUrls;
    }

    public void setFinalMobileUrls(String finalMobileUrls) {
        this.finalMobileUrls = finalMobileUrls;
    }

    public String getFinalUrls() {
        return finalUrls;
    }
    
    public boolean hasFinalUrl(String finalUrl) {
    if (finalUrls != null && finalUrls.length() > 0) {
      return Lists.newArrayList(finalUrls.split(";")).contains(finalUrl);
    } else {
      return false;
    }
  }

    public void setFinalUrls(String finalUrls) {
    if (finalUrls != null && finalUrls.length() > 0 && !finalUrls.trim().equals("--")) {
      this.finalUrls = finalUrls.replace("[\"", "").replace("\"]", "");
    } else {
      this.finalUrls = finalUrls;
    }
    }

    public String getTrackingUrlTemplate() {
        return trackingUrlTemplate;
    }

    public void setTrackingUrlTemplate(String trackingUrlTemplate) {
        this.trackingUrlTemplate = trackingUrlTemplate;
    }

    public String getUrlCustomParameters() {
        return urlCustomParameters;
    }

    public void setUrlCustomParameters(String urlCustomParameters) {
        this.urlCustomParameters = urlCustomParameters;
    }
    
    public void setAvgPosition(BigDecimal avgPosition) {
        this.avgPosition = avgPosition;
    }

    public String getAvgPosition() {
        return GoogleUtil.formatAsReadable(avgPosition);
    }

    public BigDecimal getAvgPositionBigDecimal() {
        return avgPosition;
    }

    public void setAvgPosition(String avgPosition) {
        this.avgPosition = GoogleUtil.parseFromNumberString(avgPosition);
    }

    public String getAdGroupStatus() {
        return adGroupStatus;
    }

    public void setAdGroupStatus(String adGroupStatus) {
        this.adGroupStatus = adGroupStatus;
    }

    public String getCampaignStatus() {
        return campaignStatus;
    }

    public void setCampaignStatus(String campaignStatus) {
        this.campaignStatus = campaignStatus;
    }
  

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId, adGroupId and date
    if (this.getAccountId() != null && this.getCampaignId() != null && this.getAdGroupId() != null
        && this.getKeywordId() != null) {
      this.id = this.getAccountId() + "-" + this.getCampaignId() + "-" + this.getAdGroupId() + "-"
          + this.getKeywordId();    }
  }

}
