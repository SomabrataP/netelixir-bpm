/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc.adwords.upload;


import java.io.Serializable;


/**
 *
 * @author vishal
 */
public class NegativeKeywordsStructure extends Report implements Serializable{

  private Long keywordId;

  private String keywordMatchType;

  private String criteria;

  private Long campaignId;

  private boolean negative;

  private String campaignName;

  private String campaignStatus;

  /**
   * Hibernate needs an empty constructor
   */
  public NegativeKeywordsStructure() {}

  public NegativeKeywordsStructure(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  public boolean isNegative() {
    return negative;
  }

  public void setNegative(boolean negative) {
    this.negative = negative;
  }

  public void setNegative(String negative) {
    this.negative = Boolean.parseBoolean(negative);
  }

  @Override
  public void setId() {
    // Generating unique id after having campaignId, adGroupId and date

    if (this.getAccountId() != null) {
      this.id = String.valueOf(this.getAccountId());
    }
    if (this.getCampaignId() != null) {
      this.id += "-" + this.getCampaignId();
    }
    if (this.getKeywordId() != null) {
      this.id += "-" + this.getKeywordId();
    }
    if (this.getKeywordMatchType() != null) {
      this.id += "-" + this.getKeywordMatchType();
    }

  }

  // keywordId
  public Long getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(Long keywordId) {
    this.keywordId = keywordId;
  }

  // keywordMatchType
  public String getKeywordMatchType() {
    return keywordMatchType;
  }

  public void setKeywordMatchType(String keywordMatchType) {
    this.keywordMatchType = keywordMatchType;
  }

  // keywordText is named as criteria in V201506

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }


  // campaignId
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

    public String getCampaignStatus() {
        return campaignStatus;
    }

    public void setCampaignStatus(String campaignStatus) {
        this.campaignStatus = campaignStatus;
    }
  
  
}

