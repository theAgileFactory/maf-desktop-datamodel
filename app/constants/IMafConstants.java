/*! LICENSE
 *
 * Copyright (c) 2015, The Agile Factory SA and/or its affiliates. All rights
 * reserved.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package constants;

import framework.commons.IFrameworkConstants;

/**
 * An interface which gathers various constants used across the application.
 * 
 * @author Pierre-Yves Cloux
 */
public interface IMafConstants extends IFrameworkConstants {
    /**
     * Unique plugin definition ID for the governance module.<br/>
     * This is a fake plugin ID which is used to identify the governance module
     * in various screens (example: IDZone)
     */
    public static final String MAF_GOVERNANCE_MODULE_ID = "governance";

    /**
     * Cache configuration for the AdPanelUtils.
     */
    public static final String AD_CACHE_PREFIX = IMafConstants.MAF_CACHE_PREFIX + "ad_panel.";

    /**
     * Cache for actor hierarchy
     */
    public static final String ACTOR_HIERARCHY_CACHE_PREFIX = IMafConstants.MAF_CACHE_PREFIX + "actorhierarchy.";

    /**
     * Unique plugin configuration ID for the governance module.<br/>
     * This is a fake plugin ID which is used to identify the governance module
     * in various screens (example: IDZone)
     */
    public static final int MAF_GOVERNANCE_MODULE_CONFIGURATION = 0;

    // -------------------------------------------------------------------------------------
    // DataType name constants
    // -------------------------------------------------------------------------------------

    public static final String Actor = "Actor";
    public static final String ApplicationBlock = "ApplicationBlock";
    public static final String BudgetBucket = "BudgetBucket";
    public static final String BudgetBucketLine = "BudgetBucketLine";
    public static final String CostCenter = "CostCenter";
    public static final String Iteration = "Iteration";
    public static final String OrgUnit = "OrgUnit";
    public static final String PortfolioEntryBudget = "PortfolioEntryBudget";
    public static final String PortfolioEntryBudgetLine = "PortfolioEntryBudgetLine";
    public static final String PortfolioEntryEvent = "PortfolioEntryEvent";
    public static final String PortfolioEntry = "PortfolioEntry";
    public static final String PortfolioEntryPlanningPackage = "PortfolioEntryPlanningPackage";
    public static final String PortfolioEntryReport = "PortfolioEntryReport";
    public static final String PortfolioEntryResourcePlanAllocatedActor = "PortfolioEntryResourcePlanAllocatedActor";
    public static final String PortfolioEntryResourcePlanAllocatedOrgUnit = "PortfolioEntryResourcePlanAllocatedOrgUnit";
    public static final String PortfolioEntryResourcePlanAllocatedCompetency = "PortfolioEntryResourcePlanAllocatedCompetency";
    public static final String PortfolioEntryRisk = "PortfolioEntryRisk";
    public static final String Portfolio = "Portfolio";
    public static final String Stakeholder = "Stakeholder";
    public static final String PurchaseOrderLineItem = "PurchaseOrderLineItem";
    public static final String PurchaseOrder = "PurchaseOrder";
    public static final String Release = "Release";
    public static final String Requirement = "Requirement";
    public static final String TimesheetActivityAllocatedActor = "TimesheetActivityAllocatedActor";
    public static final String WorkOrder = "WorkOrder";

    // -------------------------------------------------------------------------------------
    // Dynamic permissions constants
    // -------------------------------------------------------------------------------------
    public static final String PORTFOLIO_ENTRY_VIEW_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_ENTRY_DETAILS_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_DETAILS_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_ENTRY_EDIT_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_EDIT_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_ENTRY_DELETE_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_DELETE_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_ENTRY_REVIEW_REQUEST_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_REVIEW_REQUEST_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_ENTRY_FINANCIAL_VIEW_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_FINANCIAL_VIEW_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_ENTRY_FINANCIAL_EDIT_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_FINANCIAL_EDIT_DYNAMIC_PERMISSION";

    public static final String PORTFOLIO_VIEW_DYNAMIC_PERMISSION = "PORTFOLIO_VIEW_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_EDIT_DYNAMIC_PERMISSION = "PORTFOLIO_EDIT_DYNAMIC_PERMISSION";
    public static final String PORTFOLIO_VIEW_FINANCIAL_DYNAMIC_PERMISSION = "PORTFOLIO_VIEW_FINANCIAL_DYNAMIC_PERMISSION";

    public static final String BUDGET_BUCKET_VIEW_DYNAMIC_PERMISSION = "BUDGET_BUCKET_VIEW_DYNAMIC_PERMISSION";
    public static final String BUDGET_BUCKET_EDIT_DYNAMIC_PERMISSION = "BUDGET_BUCKET_EDIT_DYNAMIC_PERMISSION";

    public static final String REPORTING_VIEW_DYNAMIC_PERMISSION = "REPORTING_VIEW_DYNAMIC_PERMISSION";

    public static final String TIMESHEET_APPROVAL_DYNAMIC_PERMISSION = "TIMESHEET_APPROVAL_DYNAMIC_PERMISSION";

    public static final String ACTOR_VIEW_DYNAMIC_PERMISSION = "ACTOR_VIEW_DYNAMIC_PERMISSION";
    public static final String ACTOR_EDIT_DYNAMIC_PERMISSION = "ACTOR_EDIT_DYNAMIC_PERMISSION";
    public static final String ACTOR_DELETE_DYNAMIC_PERMISSION = "ACTOR_DELETE_DYNAMIC_PERMISSION";

    public static final String ORG_UNIT_VIEW_DYNAMIC_PERMISSION = "ORG_UNIT_VIEW_DYNAMIC_PERMISSION";

    public static final String RELEASE_VIEW_DYNAMIC_PERMISSION = "RELEASE_VIEW_DYNAMIC_PERMISSION";
    public static final String RELEASE_EDIT_DYNAMIC_PERMISSION = "RELEASE_EDIT_DYNAMIC_PERMISSION";

    // -------------------------------------------------------------------------------------
    // Permissions constants
    // -------------------------------------------------------------------------------------
    public static final String ADMIN_USER_ADMINISTRATION_PERMISSION = "ADMIN_USER_ADMINISTRATION_PERMISSION";
    public static final String ADMIN_HUB_MONITORING_PERMISSION = "ADMIN_HUB_MONITORING_PERMISSION";
    public static final String ADMIN_AUDIT_LOG_PERMISSION = "ADMIN_AUDIT_LOG_PERMISSION";
    public static final String ADMIN_PROVISIONING_MANAGER_PERMISSION = "ADMIN_PROVISIONING_MANAGER_PERMISSION";
    public static final String ADMIN_CONFIGURATION_PERMISSION = "ADMIN_CONFIGURATION_PERMISSION";
    public static final String ADMIN_CUSTOM_ATTRIBUTE_PERMISSION = "ADMIN_CUSTOM_ATTRIBUTE_PERMISSION";
    public static final String ADMIN_SYSTEM_OWNER_PERMISSION = "ADMIN_SYSTEM_OWNER_PERMISSION";
    public static final String ADMIN_KPI_MANAGER_PERMISSION = "ADMIN_KPI_MANAGER_PERMISSION";
    public static final String ADMIN_TRANSLATION_KEY_EDIT_PERMISSION = "ADMIN_TRANSLATION_KEY_EDIT_PERMISSION";

    public static final String PERSONAL_SPACE_READ_PERMISSION = "PERSONAL_SPACE_READ_PERMISSION";

    public static final String SCM_DEVELOPER_PERMISSION = "SCM_DEVELOPER_PERMISSION";
    public static final String SCM_ADMIN_PERMISSION = "SCM_ADMIN_PERMISSION";
    public static final String JENKINS_VIEWER_PERMISSION = "JENKINS_VIEWER_PERMISSION";
    public static final String JENKINS_DEPLOY_PERMISSION = "JENKINS_DEPLOY_PERMISSION";

    public static final String SAMPLE_PERMISSION_PRIVATE = "SAMPLE_PERMISSION_PRIVATE";

    public static final String ROADMAP_DISPLAY_PERMISSION = "ROADMAP_DISPLAY_PERMISSION";
    public static final String ROADMAP_SIMULATOR_PERMISSION = "ROADMAP_SIMULATOR_PERMISSION";

    public static final String COCKPIT_DISPLAY_PERMISSION = "COCKPIT_DISPLAY_PERMISSION";

    public static final String PORTFOLIO_ENTRY_VIEW_PUBLIC_PERMISSION = "PORTFOLIO_ENTRY_VIEW_PUBLIC_PERMISSION";
    public static final String PORTFOLIO_ENTRY_VIEW_DETAILS_ALL_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_ALL_PERMISSION";
    public static final String PORTFOLIO_ENTRY_VIEW_DETAILS_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_AS_MANAGER_PERMISSION";
    public static final String PORTFOLIO_ENTRY_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION";
    public static final String PORTFOLIO_ENTRY_VIEW_DETAILS_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_AS_PORTFOLIO_MANAGER_PERMISSION";
    public static final String PORTFOLIO_ENTRY_EDIT_ALL_PERMISSION = "PORTFOLIO_ENTRY_EDIT_ALL_PERMISSION";
    public static final String PORTFOLIO_ENTRY_EDIT_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_AS_MANAGER_PERMISSION";
    public static final String PORTFOLIO_ENTRY_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION";

    public static final String PORTFOLIO_ENTRY_DELETE_ALL_PERMISSION = "PORTFOLIO_ENTRY_DELETE_ALL_PERMISSION";
    public static final String PORTFOLIO_ENTRY_SUBMISSION_PERMISSION = "PORTFOLIO_ENTRY_SUBMISSION_PERMISSION";
    public static final String PORTFOLIO_ENTRY_REVIEW_REQUEST_ALL_PERMISSION = "PORTFOLIO_ENTRY_REVIEW_REQUEST_ALL_PERMISSION";
    public static final String PORTFOLIO_ENTRY_REVIEW_REQUEST_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_REVIEW_REQUEST_AS_"
            + "PORTFOLIO_MANAGER_PERMISSION";

    public static final String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_ALL_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_ALL_PERMISSION";
    public static final String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION";
    public static final String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_STAKEHOLDER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_"
            + "AS_STAKEHOLDER_PERMISSION";
    public static final String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL"
            + "_INFO_AS_PORTFOLIO_MANAGER_PERMISSION";
    public static final String PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_ALL_PERMISSION = "PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_ALL_PERMISSION";
    public static final String PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_AS_MANAGER_PERMISSION";

    public static final String BUDGET_BUCKET_VIEW_ALL_PERMISSION = "BUDGET_BUCKET_VIEW_ALL_PERMISSION";
    public static final String BUDGET_BUCKET_VIEW_AS_OWNER_PERMISSION = "BUDGET_BUCKET_VIEW_AS_OWNER_PERMISSION";
    public static final String BUDGET_BUCKET_EDIT_ALL_PERMISSION = "BUDGET_BUCKET_EDIT_ALL_PERMISSION";
    public static final String BUDGET_BUCKET_EDIT_AS_OWNER_PERMISSION = "BUDGET_BUCKET_EDIT_AS_OWNER_PERMISSION";

    public static final String REPORTING_VIEW_ALL_PERMISSION = "REPORTING_VIEW_ALL_PERMISSION";
    public static final String REPORTING_VIEW_AS_VIEWER_PERMISSION = "REPORTING_VIEW_AS_VIEWER_PERMISSION";
    public static final String REPORTING_ADMINISTRATION_PERMISSION = "REPORTING_ADMINISTRATION_PERMISSION";

    public static final String PURCHASE_ORDER_VIEW_ALL_PERMISSION = "PURCHASE_ORDER_VIEW_ALL_PERMISSION";

    public static final String MILESTONE_APPROVAL_PERMISSION = "MILESTONE_APPROVAL_PERMISSION";
    public static final String MILESTONE_DECIDE_PERMISSION = "MILESTONE_DECIDE_PERMISSION";
    public static final String MILESTONE_OVERVIEW_PERMISSION = "MILESTONE_OVERVIEW_PERMISSION";

    public static final String SEARCH_PERMISSION = "SEARCH_PERMISSION";

    public static final String ACTOR_VIEW_ALL_PERMISSION = "ACTOR_VIEW_ALL_PERMISSION";
    public static final String ACTOR_VIEW_AS_SUPERIOR_PERMISSION = "ACTOR_VIEW_AS_SUPERIOR_PERMISSION";
    public static final String ACTOR_EDIT_ALL_PERMISSION = "ACTOR_EDIT_ALL_PERMISSION";

    public static final String ORG_UNIT_VIEW_ALL_PERMISSION = "ORG_UNIT_VIEW_ALL_PERMISSION";
    public static final String ORG_UNIT_VIEW_AS_RESPONSIBLE_PERMISSION = "ORG_UNIT_VIEW_AS_RESPONSIBLE_PERMISSION";
    public static final String ORG_UNIT_EDIT_ALL_PERMISSION = "ORG_UNIT_EDIT_ALL_PERMISSION";

    public static final String PORTFOLIO_VIEW_DETAILS_ALL_PERMISSION = "PORTFOLIO_VIEW_DETAILS_ALL_PERMISSION";
    public static final String PORTFOLIO_VIEW_DETAILS_AS_MANAGER_PERMISSION = "PORTFOLIO_VIEW_DETAILS_AS_MANAGER_PERMISSION";
    public static final String PORTFOLIO_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION = "PORTFOLIO_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION";
    public static final String PORTFOLIO_VIEW_FINANCIAL_INFO_ALL_PERMISSION = "PORTFOLIO_VIEW_FINANCIAL_INFO_ALL_PERMISSION";
    public static final String PORTFOLIO_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION = "PORTFOLIO_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION";
    public static final String PORTFOLIO_EDIT_ALL_PERMISSION = "PORTFOLIO_EDIT_ALL_PERMISSION";
    public static final String PORTFOLIO_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION";

    public static final String TIMESHEET_ENTRY_PERMISSION = "TIMESHEET_ENTRY_PERMISSION";
    public static final String TIMESHEET_APPROVAL_ALL_PERMISSION = "TIMESHEET_APPROVAL_ALL_PERMISSION";
    public static final String TIMESHEET_APPROVAL_AS_MANAGER_PERMISSION = "TIMESHEET_APPROVAL_AS_MANAGER_PERMISSION";
    public static final String TIMESHEET_LOCK_ALL_PERMISSION = "TIMESHEET_LOCK_ALL_PERMISSION";

    public static final String RELEASE_VIEW_ALL_PERMISSION = "RELEASE_VIEW_ALL_PERMISSION";
    public static final String RELEASE_VIEW_AS_MANAGER_PERMISSION = "RELEASE_VIEW_AS_MANAGER_PERMISSION";
    public static final String RELEASE_EDIT_ALL_PERMISSION = "RELEASE_EDIT_ALL_PERMISSION";
    public static final String RELEASE_EDIT_AS_MANAGER_PERMISSION = "RELEASE_EDIT_AS_MANAGER_PERMISSION";

    public static final String API_MANAGER_PERMISSION = "API_MANAGER_PERMISSION";
    public static final String API_TESTER_PERMISSION = "API_TESTER_PERMISSION";

    public static final String ARCHITECTURE_PERMISSION = "ARCHITECTURE_PERMISSION";
    public static final String APPLICATION_BLOCK_EDIT_ALL_PERMISSION = "APPLICATION_BLOCK_EDIT_ALL_PERMISSION";

    public static final String PARTNER_SYNDICATION_PERMISSION = "PARTNER_SYNDICATION_PERMISSION";

    // -------------------------------------------------------------------------------------
    // Preference constants
    // -------------------------------------------------------------------------------------
    public static final String GOVERNANCE_REVIEWED_REQUESTS_DISPLAY_DURATION = "GOVERNANCE_REVIEWED_REQUESTS_DISPLAY_DURATION";
    public static final String FINANCIAL_USE_PURCHASE_ORDER_PREFERENCE = "FINANCIAL_USE_PURCHASE_ORDER_PREFERENCE";
    public static final String APPLICATION_LOGO_PREFERENCE = "APPLICATION_LOGO_PREFERENCE";
    public static final String TIMESHEET_MUST_APPROVE_PREFERENCE = "TIMESHEET_MUST_APPROVE_PREFERENCE";
    public static final String TIMESHEET_REMINDER_LIMIT_PREFERENCE = "TIMESHEET_REMINDER_LIMIT_PREFERENCE";
    public static final String TIMESHEET_HOURS_PER_DAY = "TIMESHEET_HOURS_PER_DAY";
    public static final String PORTFOLIO_ENTRY_PLANNING_OVERVIEW_PREFERENCE = "PORTFOLIO_ENTRY_PLANNING_OVERVIEW_PREFERENCE";
    public static final String PACKAGE_STATUS_ON_GOING_FULFILLMENT_PERCENTAGE_PREFERENCE = "PACKAGE_STATUS_ON_GOING_FULFILLMENT_PERCENTAGE_PREFERENCE";
    public static final String CUSTOM_REPORT_TEMPLATE_FOR_STATUS_REPORT_PREFERENCE = "CUSTOM_REPORT_TEMPLATE_FOR_STATUS_REPORT_PREFERENCE";
    public static final String ROADMAP_CAPACITY_SIMULATOR_WARNING_LIMIT_PREFERENCE = "ROADMAP_CAPACITY_SIMULATOR_WARNING_LIMIT_PREFERENCE";
    public static final String LICENSE_CAN_MANAGE_ARCHIVED_PORTFOLIO_ENTRY_PREFERENCE = "LICENSE_CAN_MANAGE_ARCHIVED_PORTFOLIO_ENTRY_PREFERENCE";
    public static final String LICENSE_ECHANNEL_API_SECRET_KEY_PREFERENCE = "LICENSE_ECHANNEL_API_SECRET_KEY_PREFERENCE";
    public static final String LICENSE_INSTANCE_DOMAIN_PREFERENCE = "LICENSE_INSTANCE_DOMAIN_PREFERENCE";
}
