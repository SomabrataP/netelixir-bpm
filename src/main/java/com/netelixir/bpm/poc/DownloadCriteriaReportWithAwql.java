// Copyright 2017 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.netelixir.bpm.poc;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201710.cm.Operator;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.client.reporting.ReportingConfiguration;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.api.ads.adwords.lib.jaxb.v201710.DownloadFormat;
import com.google.api.ads.adwords.lib.jaxb.v201710.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201710.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201710.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.jaxb.v201710.Selector;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.v201710.ReportDownloaderInterface;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.client.auth.oauth2.Credential;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This example downloads a criteria performance report with AWQL.
 *
 * <p>
 * Credentials and properties in {@code fromFile()} are pulled from the
 * "ads.properties" file. See README for more info.
 */
public class DownloadCriteriaReportWithAwql {

    public static String REFRESH_TOKEN = "1/0LGiMd-6eIj2SJCcnJSbdsUbPYYsyRal4AUrQulf5KaFxW07vgLU1ujJfpoPvAaM";
    public static String CLIENT_ID = "774293531350-cams4j46618345pii2a87s9bf1i6ofn6.apps.googleusercontent.com";
    public static String CLIENT_SECRET = "QqaK4Y4hejvvNx3etkfkrJJH";
    public static String ADWORDS_CID = "3887595299";
    public static String DEVELOPER_TOKEN = "RvC31uLX5bAPmUjsShJA3Q";

    public static void main(String[] args) throws Exception {
        AdWordsSession session = Authenticator.getAdWordsSession();

        AdWordsServicesInterface adWordsServices = AdWordsServices.getInstance();

        // Location to download report to.
        String reportFile = System.getProperty("user.home") + File.separatorChar + "report.csv";
        String reportSqrFile = System.getProperty("user.home") + File.separatorChar + "report-sqr.csv";

//        runExample(adWordsServices, session, reportFile);
        fetchAccountReport(ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT, ReportDefinitionDateRangeType.LAST_7_DAYS, reportFile, adWordsServices, session);
        fetchKeywordLevelReport(ReportDefinitionReportType.SEARCH_QUERY_PERFORMANCE_REPORT, ReportDefinitionDateRangeType.LAST_7_DAYS, reportSqrFile, adWordsServices, session);
    }

    public static void fetchAccountReport(ReportDefinitionReportType reportType, ReportDefinitionDateRangeType dateRange, String reportFile, AdWordsServicesInterface adWordsServices, AdWordsSession session) throws Exception {
        String query = "";
        switch (reportType) {
            case CRITERIA_PERFORMANCE_REPORT:
                query = "SELECT CampaignId, AdGroupId,Id, CriteriaType, Criteria,FinalUrls,"
                        + " Impressions,Clicks,Cost  FROM CRITERIA_PERFORMANCE_REPORT"
                        + " WHERE Status IN [ENABLED]" + " DURING " + dateRange.toString();
                break;
            case KEYWORDS_PERFORMANCE_REPORT:
                query = "SELECT CampaignId, CampaignName, CampaignStatus, AdGroupId,AdGroupName, AdGroupStatus,"
                        + " Id, KeywordMatchType, Criteria,FinalUrls,Impressions,Clicks,Cost, Conversions,"
                        + " ConversionValue FROM KEYWORDS_PERFORMANCE_REPORT"
                        + " WHERE Status IN [ENABLED] AND Cost >= 100000 AND Conversions < 0.01" + " DURING " + dateRange.toString();
                break;
            case SEARCH_QUERY_PERFORMANCE_REPORT:
                query = "SELECT CampaignId, CampaignName, CampaignStatus, AdGroupId, AdGroupName,"
                        + " AdGroupStatus, FinalUrl, KeywordId, KeywordTextMatchingQuery, Query,"
                        + " QueryTargetingStatus, Impressions, Clicks, Cost FROM SEARCH_QUERY_PERFORMANCE_REPORT"
                        + " DURING " + dateRange.toString();
                break;
            default:
                break;
        }
        downloadReport(adWordsServices, session, reportFile, query);
    }
    
    public static void fetchKeywordLevelReport(ReportDefinitionReportType reportType, ReportDefinitionDateRangeType dateRange, String reportFile, AdWordsServicesInterface adWordsServices, AdWordsSession session) throws Exception {
        String query = "";
        switch (reportType) {
            case CRITERIA_PERFORMANCE_REPORT:
                query = "SELECT CampaignId, AdGroupId,Id, CriteriaType, Criteria,FinalUrls,"
                        + " Impressions,Clicks,Cost  FROM CRITERIA_PERFORMANCE_REPORT"
                        + " WHERE Status IN [ENABLED]" + " DURING " + dateRange.toString();
                break;
            case KEYWORDS_PERFORMANCE_REPORT:
                query = "SELECT CampaignId, CampaignName, CampaignStatus, AdGroupId,AdGroupName, AdGroupStatus,"
                        + " Id, KeywordMatchType, Criteria,FinalUrls,Impressions,Clicks,Cost, Conversions,"
                        + " ConversionValue FROM KEYWORDS_PERFORMANCE_REPORT"
                        + " WHERE Status IN [ENABLED]" + " DURING " + dateRange.toString();
                break;
            case SEARCH_QUERY_PERFORMANCE_REPORT:
                query = "SELECT CampaignId, CampaignName, CampaignStatus, AdGroupId, AdGroupName,"
                        + " AdGroupStatus, FinalUrl, KeywordId, KeywordTextMatchingQuery, Query,"
                        + " QueryTargetingStatus, Impressions, Clicks, Cost FROM SEARCH_QUERY_PERFORMANCE_REPORT"
                        + " WHERE KewordId IN [603308677392, 378139978798, 357106483127, 378139978958, 330711905002, 7192451082]"
                        + " DURING " + dateRange.toString();
                break;
            default:
                break;
        }
        downloadReport(adWordsServices, session, reportFile, query);
    }

    public static void downloadReport(
            AdWordsServicesInterface adWordsServices, AdWordsSession session, String reportFile,
            String query)
            throws Exception {
        System.out.println("Query: " + query);
        // Optional: Set the reporting configuration of the session to suppress header, column name, or
        // summary rows in the report output. You can also configure this via your ads.properties
        // configuration file. See AdWordsSession.Builder.from(Configuration) for details.
        // In addition, you can set whether you want to explicitly include or exclude zero impression
        // rows.
        ReportingConfiguration reportingConfiguration
                = new ReportingConfiguration.Builder()
                        .skipReportHeader(false)
                        .skipColumnHeader(false)
                        .skipReportSummary(false)
                        // Set to false to exclude rows with zero impressions.
                        .includeZeroImpressions(false)
                        .build();
        session.setReportingConfiguration(reportingConfiguration);

        ReportDownloaderInterface reportDownloader
                = adWordsServices.getUtility(session, ReportDownloaderInterface.class);

        try {
            // Set the property api.adwords.reportDownloadTimeout or call
            // ReportDownloader.setReportDownloadTimeout to set a timeout (in milliseconds)
            // for CONNECT and READ in report downloads.
            ReportDownloadResponse response = reportDownloader.downloadReport(query, DownloadFormat.CSV);
            response.saveToFile(reportFile);

            System.out.printf("Report successfully downloaded to: %s%n", reportFile);
        } catch (ReportDownloadResponseException e) {
            System.out.printf("Report was not downloaded due to: %s%n", e);
        }
    }

    public static void runExample(
            AdWordsServicesInterface adWordsServices, AdWordsSession session, String reportFile)
            throws Exception {
        // Create query.
        String query = "SELECT CampaignId, AdGroupId, Id, Criteria, CriteriaType, "
                + "Impressions, Clicks, Cost FROM CRITERIA_PERFORMANCE_REPORT "
                + "WHERE Status IN [ENABLED, PAUSED] "
                + "DURING YESTERDAY";

        // Optional: Set the reporting configuration of the session to suppress header, column name, or
        // summary rows in the report output. You can also configure this via your ads.properties
        // configuration file. See AdWordsSession.Builder.from(Configuration) for details.
        // In addition, you can set whether you want to explicitly include or exclude zero impression
        // rows.
        ReportingConfiguration reportingConfiguration
                = new ReportingConfiguration.Builder()
                        .skipReportHeader(false)
                        .skipColumnHeader(false)
                        .skipReportSummary(false)
                        // Set to false to exclude rows with zero impressions.
                        .includeZeroImpressions(true)
                        .build();
        session.setReportingConfiguration(reportingConfiguration);

        ReportDownloaderInterface reportDownloader
                = adWordsServices.getUtility(session, ReportDownloaderInterface.class);

        try {
            // Set the property api.adwords.reportDownloadTimeout or call
            // ReportDownloader.setReportDownloadTimeout to set a timeout (in milliseconds)
            // for CONNECT and READ in report downloads.
            ReportDownloadResponse response = reportDownloader.downloadReport(query, DownloadFormat.CSV);
            response.saveToFile(reportFile);

            System.out.printf("Report successfully downloaded to: %s%n", reportFile);
        } catch (ReportDownloadResponseException e) {
            System.out.printf("Report was not downloaded due to: %s%n", e);
        }
    }
}
