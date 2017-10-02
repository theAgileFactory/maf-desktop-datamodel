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
    String MAF_GOVERNANCE_MODULE_ID = "governance";

    /**
     * Cache for actor hierarchy
     */
    String ACTOR_HIERARCHY_CACHE_PREFIX = IMafConstants.MAF_CACHE_PREFIX + "actorhierarchy.";

    /**
     * Unique plugin configuration ID for the governance module.<br/>
     * This is a fake plugin ID which is used to identify the governance module
     * in various screens (example: IDZone)
     */
    int MAF_GOVERNANCE_MODULE_CONFIGURATION = 0;

    /**
     * The ref ID of the purchase order that is used to engage the work orders
     * used in the budget tracking (automatically generated from a resource
     * allocation).
     */
    String PURCHASE_ORDER_REF_ID_FOR_BUDGET_TRACKING = "_PO_BUDGET_TRACKING";

    // -------------------------------------------------------------------------------------
    // DataType name constants
    // -------------------------------------------------------------------------------------

    String Actor = "Actor";
    String ApplicationBlock = "ApplicationBlock";
    String BudgetBucket = "BudgetBucket";
    String BudgetBucketLine = "BudgetBucketLine";
    String CostCenter = "CostCenter";
    String Iteration = "Iteration";
    String OrgUnit = "OrgUnit";
    String PortfolioEntryBudget = "PortfolioEntryBudget";
    String PortfolioEntryBudgetLine = "PortfolioEntryBudgetLine";
    String PortfolioEntryEvent = "PortfolioEntryEvent";
    String PortfolioEntry = "PortfolioEntry";
    String PortfolioEntryPlanningPackage = "PortfolioEntryPlanningPackage";
    String PortfolioEntryReport = "PortfolioEntryReport";
    String PortfolioEntryResourcePlanAllocatedActor = "PortfolioEntryResourcePlanAllocatedActor";
    String PortfolioEntryResourcePlanAllocatedOrgUnit = "PortfolioEntryResourcePlanAllocatedOrgUnit";
    String PortfolioEntryResourcePlanAllocatedCompetency = "PortfolioEntryResourcePlanAllocatedCompetency";
    String PortfolioEntryRisk = "PortfolioEntryRisk";
    String Portfolio = "Portfolio";
    String Stakeholder = "Stakeholder";
    String PurchaseOrderLineItem = "PurchaseOrderLineItem";
    String PurchaseOrder = "PurchaseOrder";
    String Requirement = "Requirement";
    String TimesheetActivityAllocatedActor = "TimesheetActivityAllocatedActor";
    String WorkOrder = "WorkOrder";
    String Deliverable = "Deliverable";
    String LifeCycleMilestoneInstance = "LifeCycleMilestoneInstance";
    String PlannedLifeCycleMilestoneInstance = "PlannedLifeCycleMilestoneInstance";

    // -------------------------------------------------------------------------------------
    // Dynamic permissions constants
    // -------------------------------------------------------------------------------------
    String PORTFOLIO_ENTRY_VIEW_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DYNAMIC_PERMISSION";
    String PORTFOLIO_ENTRY_DETAILS_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_DETAILS_DYNAMIC_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_EDIT_DYNAMIC_PERMISSION";
    String PORTFOLIO_ENTRY_DELETE_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_DELETE_DYNAMIC_PERMISSION";
    String PORTFOLIO_ENTRY_REVIEW_REQUEST_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_REVIEW_REQUEST_DYNAMIC_PERMISSION";
    String PORTFOLIO_ENTRY_FINANCIAL_VIEW_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_FINANCIAL_VIEW_DYNAMIC_PERMISSION";
    String PORTFOLIO_ENTRY_FINANCIAL_EDIT_DYNAMIC_PERMISSION = "PORTFOLIO_ENTRY_FINANCIAL_EDIT_DYNAMIC_PERMISSION";

    String PORTFOLIO_VIEW_DYNAMIC_PERMISSION = "PORTFOLIO_VIEW_DYNAMIC_PERMISSION";
    String PORTFOLIO_EDIT_DYNAMIC_PERMISSION = "PORTFOLIO_EDIT_DYNAMIC_PERMISSION";
    String PORTFOLIO_VIEW_FINANCIAL_DYNAMIC_PERMISSION = "PORTFOLIO_VIEW_FINANCIAL_DYNAMIC_PERMISSION";

    String BUDGET_BUCKET_VIEW_DYNAMIC_PERMISSION = "BUDGET_BUCKET_VIEW_DYNAMIC_PERMISSION";
    String BUDGET_BUCKET_EDIT_DYNAMIC_PERMISSION = "BUDGET_BUCKET_EDIT_DYNAMIC_PERMISSION";

    String REPORTING_VIEW_DYNAMIC_PERMISSION = "REPORTING_VIEW_DYNAMIC_PERMISSION";

    String TIMESHEET_APPROVAL_DYNAMIC_PERMISSION = "TIMESHEET_APPROVAL_DYNAMIC_PERMISSION";

    String ACTOR_VIEW_DYNAMIC_PERMISSION = "ACTOR_VIEW_DYNAMIC_PERMISSION";
    String ACTOR_EDIT_DYNAMIC_PERMISSION = "ACTOR_EDIT_DYNAMIC_PERMISSION";
    String ACTOR_DELETE_DYNAMIC_PERMISSION = "ACTOR_DELETE_DYNAMIC_PERMISSION";

    String ORG_UNIT_VIEW_DYNAMIC_PERMISSION = "ORG_UNIT_VIEW_DYNAMIC_PERMISSION";

    // -------------------------------------------------------------------------------------
    // Permissions constants
    // -------------------------------------------------------------------------------------
    String ADMIN_USER_ADMINISTRATION_PERMISSION = "ADMIN_USER_ADMINISTRATION_PERMISSION";
    String ADMIN_HUB_MONITORING_PERMISSION = "ADMIN_HUB_MONITORING_PERMISSION";
    String ADMIN_AUDIT_LOG_PERMISSION = "ADMIN_AUDIT_LOG_PERMISSION";
    String ADMIN_PROVISIONING_MANAGER_PERMISSION = "ADMIN_PROVISIONING_MANAGER_PERMISSION";
    String ADMIN_CONFIGURATION_PERMISSION = "ADMIN_CONFIGURATION_PERMISSION";
    String ADMIN_CUSTOM_ATTRIBUTE_PERMISSION = "ADMIN_CUSTOM_ATTRIBUTE_PERMISSION";
    String ADMIN_SYSTEM_OWNER_PERMISSION = "ADMIN_SYSTEM_OWNER_PERMISSION";
    String ADMIN_KPI_MANAGER_PERMISSION = "ADMIN_KPI_MANAGER_PERMISSION";
    String ADMIN_TRANSLATION_KEY_EDIT_PERMISSION = "ADMIN_TRANSLATION_KEY_EDIT_PERMISSION";
    String ADMIN_ATTACHMENTS_MANAGEMENT_PERMISSION = "ADMIN_ATTACHMENTS_MANAGEMENT_PERMISSION";
    String ADMIN_ATTACHMENTS_MANAGEMENT_PERMISSION_NO_CONFIDENTIAL = "ADMIN_ATTACHMENTS_MANAGEMENT_PERMISSION_NO_CONFIDENTIAL";

    String PERSONAL_SPACE_READ_PERMISSION = "PERSONAL_SPACE_READ_PERMISSION";

    String SCM_DEVELOPER_PERMISSION = "SCM_DEVELOPER_PERMISSION";
    String SCM_ADMIN_PERMISSION = "SCM_ADMIN_PERMISSION";
    String JENKINS_VIEWER_PERMISSION = "JENKINS_VIEWER_PERMISSION";
    String JENKINS_DEPLOY_PERMISSION = "JENKINS_DEPLOY_PERMISSION";

    String SAMPLE_PERMISSION_PRIVATE = "SAMPLE_PERMISSION_PRIVATE";

    String ROADMAP_DISPLAY_PERMISSION = "ROADMAP_DISPLAY_PERMISSION";
    String ROADMAP_SIMULATOR_PERMISSION = "ROADMAP_SIMULATOR_PERMISSION";

    String PORTFOLIO_ENTRY_VIEW_PUBLIC_PERMISSION = "PORTFOLIO_ENTRY_VIEW_PUBLIC_PERMISSION";
    String PORTFOLIO_ENTRY_VIEW_DETAILS_ALL_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_ALL_PERMISSION";
    String PORTFOLIO_ENTRY_VIEW_DETAILS_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_AS_MANAGER_PERMISSION";
    String PORTFOLIO_ENTRY_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION";
    String PORTFOLIO_ENTRY_VIEW_DETAILS_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_DETAILS_AS_PORTFOLIO_MANAGER_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_ALL_PERMISSION = "PORTFOLIO_ENTRY_EDIT_ALL_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_AS_MANAGER_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_AS_PORTFOLIO_STAKEHOLDER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_AS_PORTFOLIO_STAKEHOLDER_PERMISSION";

    String PORTFOLIO_ENTRY_DELETE_ALL_PERMISSION = "PORTFOLIO_ENTRY_DELETE_ALL_PERMISSION";
    String PORTFOLIO_ENTRY_SUBMISSION_PERMISSION = "PORTFOLIO_ENTRY_SUBMISSION_PERMISSION";
    String RELEASE_SUBMISSION_PERMISSION = "RELEASE_SUBMISSION_PERMISSION";
    String PORTFOLIO_ENTRY_REVIEW_REQUEST_ALL_PERMISSION = "PORTFOLIO_ENTRY_REVIEW_REQUEST_ALL_PERMISSION";
    String PORTFOLIO_ENTRY_REVIEW_REQUEST_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_REVIEW_REQUEST_AS_"
            + "PORTFOLIO_MANAGER_PERMISSION";

    String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_ALL_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_ALL_PERMISSION";
    String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION";
    String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_STAKEHOLDER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_"
            + "AS_STAKEHOLDER_PERMISSION";
    String PORTFOLIO_ENTRY_VIEW_FINANCIAL_INFO_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_VIEW_FINANCIAL"
            + "_INFO_AS_PORTFOLIO_MANAGER_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_ALL_PERMISSION = "PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_ALL_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_AS_MANAGER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_AS_MANAGER_PERMISSION";
    String PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_AS_PORTFOLIO_STAKEHOLDER_PERMISSION = "PORTFOLIO_ENTRY_EDIT_FINANCIAL_INFO_AS_PORTFOLIO_STAKEHOLDER_PERMISSION";

    String BUDGET_BUCKET_VIEW_ALL_PERMISSION = "BUDGET_BUCKET_VIEW_ALL_PERMISSION";
    String BUDGET_BUCKET_VIEW_AS_OWNER_PERMISSION = "BUDGET_BUCKET_VIEW_AS_OWNER_PERMISSION";
    String BUDGET_BUCKET_EDIT_ALL_PERMISSION = "BUDGET_BUCKET_EDIT_ALL_PERMISSION";
    String BUDGET_BUCKET_EDIT_AS_OWNER_PERMISSION = "BUDGET_BUCKET_EDIT_AS_OWNER_PERMISSION";

    String REPORTING_VIEW_ALL_PERMISSION = "REPORTING_VIEW_ALL_PERMISSION";
    String REPORTING_VIEW_AS_VIEWER_PERMISSION = "REPORTING_VIEW_AS_VIEWER_PERMISSION";
    String REPORTING_ADMINISTRATION_PERMISSION = "REPORTING_ADMINISTRATION_PERMISSION";

    String PURCHASE_ORDER_VIEW_ALL_PERMISSION = "PURCHASE_ORDER_VIEW_ALL_PERMISSION";

    String MILESTONE_APPROVAL_PERMISSION = "MILESTONE_APPROVAL_PERMISSION";
    String MILESTONE_DECIDE_PERMISSION = "MILESTONE_DECIDE_PERMISSION";
    String MILESTONE_OVERVIEW_PERMISSION = "MILESTONE_OVERVIEW_PERMISSION";

    String SEARCH_PERMISSION = "SEARCH_PERMISSION";

    String ACTOR_VIEW_ALL_PERMISSION = "ACTOR_VIEW_ALL_PERMISSION";
    String ACTOR_VIEW_AS_SUPERIOR_PERMISSION = "ACTOR_VIEW_AS_SUPERIOR_PERMISSION";
    String ACTOR_EDIT_ALL_PERMISSION = "ACTOR_EDIT_ALL_PERMISSION";

    String ORG_UNIT_VIEW_ALL_PERMISSION = "ORG_UNIT_VIEW_ALL_PERMISSION";
    String ORG_UNIT_VIEW_AS_RESPONSIBLE_PERMISSION = "ORG_UNIT_VIEW_AS_RESPONSIBLE_PERMISSION";
    String ORG_UNIT_EDIT_ALL_PERMISSION = "ORG_UNIT_EDIT_ALL_PERMISSION";

    String PORTFOLIO_VIEW_DETAILS_ALL_PERMISSION = "PORTFOLIO_VIEW_DETAILS_ALL_PERMISSION";
    String PORTFOLIO_VIEW_DETAILS_AS_MANAGER_PERMISSION = "PORTFOLIO_VIEW_DETAILS_AS_MANAGER_PERMISSION";
    String PORTFOLIO_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION = "PORTFOLIO_VIEW_DETAILS_AS_STAKEHOLDER_PERMISSION";
    String PORTFOLIO_VIEW_FINANCIAL_INFO_ALL_PERMISSION = "PORTFOLIO_VIEW_FINANCIAL_INFO_ALL_PERMISSION";
    String PORTFOLIO_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION = "PORTFOLIO_VIEW_FINANCIAL_INFO_AS_MANAGER_PERMISSION";
    String PORTFOLIO_EDIT_ALL_PERMISSION = "PORTFOLIO_EDIT_ALL_PERMISSION";
    String PORTFOLIO_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION = "PORTFOLIO_EDIT_AS_PORTFOLIO_MANAGER_PERMISSION";

    String TIMESHEET_ENTRY_PERMISSION = "TIMESHEET_ENTRY_PERMISSION";
    String TIMESHEET_APPROVAL_ALL_PERMISSION = "TIMESHEET_APPROVAL_ALL_PERMISSION";
    String TIMESHEET_APPROVAL_AS_MANAGER_PERMISSION = "TIMESHEET_APPROVAL_AS_MANAGER_PERMISSION";
    String TIMESHEET_LOCK_ALL_PERMISSION = "TIMESHEET_LOCK_ALL_PERMISSION";

    String API_MANAGER_PERMISSION = "API_MANAGER_PERMISSION";
    String API_TESTER_PERMISSION = "API_TESTER_PERMISSION";

    String ARCHITECTURE_PERMISSION = "ARCHITECTURE_PERMISSION";
    String APPLICATION_BLOCK_EDIT_ALL_PERMISSION = "APPLICATION_BLOCK_EDIT_ALL_PERMISSION";

    String PARTNER_SYNDICATION_PERMISSION = "PARTNER_SYNDICATION_PERMISSION";

    // -------------------------------------------------------------------------------------
    // Preference constants
    // -------------------------------------------------------------------------------------
    String GOVERNANCE_REVIEWED_REQUESTS_DISPLAY_DURATION = "GOVERNANCE_REVIEWED_REQUESTS_DISPLAY_DURATION";
    String FINANCIAL_USE_PURCHASE_ORDER_PREFERENCE = "FINANCIAL_USE_PURCHASE_ORDER_PREFERENCE";
    String APPLICATION_LOGO_PREFERENCE = "APPLICATION_LOGO_PREFERENCE";
    String TIMESHEET_MUST_APPROVE_PREFERENCE = "TIMESHEET_MUST_APPROVE_PREFERENCE";
    String TIMESHEET_REMINDER_LIMIT_PREFERENCE = "TIMESHEET_REMINDER_LIMIT_PREFERENCE";
    String TIMESHEET_HOURS_PER_DAY = "TIMESHEET_HOURS_PER_DAY";
    String PORTFOLIO_ENTRY_PLANNING_OVERVIEW_PREFERENCE = "PORTFOLIO_ENTRY_PLANNING_OVERVIEW_PREFERENCE";
    String PACKAGE_STATUS_ON_GOING_FULFILLMENT_PERCENTAGE_PREFERENCE = "PACKAGE_STATUS_ON_GOING_FULFILLMENT_PERCENTAGE_PREFERENCE";
    String RESOURCES_WEEK_DAYS_ALLOCATION_PREFERENCE = "RESOURCES_WEEK_DAYS_ALLOCATION_PREFERENCE";
    String CUSTOM_REPORT_TEMPLATE_FOR_STATUS_REPORT_PREFERENCE = "CUSTOM_REPORT_TEMPLATE_FOR_STATUS_REPORT_PREFERENCE";
    String ROADMAP_CAPACITY_SIMULATOR_WARNING_LIMIT_PREFERENCE = "ROADMAP_CAPACITY_SIMULATOR_WARNING_LIMIT_PREFERENCE";
    String LICENSE_CAN_MANAGE_ARCHIVED_PORTFOLIO_ENTRY_PREFERENCE = "LICENSE_CAN_MANAGE_ARCHIVED_PORTFOLIO_ENTRY_PREFERENCE";
    String LICENSE_ECHANNEL_API_SECRET_KEY_PREFERENCE = "LICENSE_ECHANNEL_API_SECRET_KEY_PREFERENCE";
    String LICENSE_INSTANCE_DOMAIN_PREFERENCE = "LICENSE_INSTANCE_DOMAIN_PREFERENCE";
    String BUDGET_TRACKING_EFFORT_BASED_PREFERENCE = "BUDGET_TRACKING_EFFORT_BASED_PREFERENCE";
    String READONLY_GOVERNANCE_ID_PREFERENCE = "READONLY_GOVERNANCE_ID_PREFERENCE";
}
