package constants;

import java.util.Map;

import framework.commons.DataType;

/**
 * A class which extends {@link DataType} with the Maf Desktop specific types
 * 
 * @author Pierre-Yves Cloux
 */
public class MafDataType extends DataType {

    public MafDataType() {
    }

    public MafDataType(String dataName, String dataTypeClassName, boolean isAuditable, boolean isCustomAttribute,
            Map<String, String> conditionalRuleAuthorizedFields) {
        super(dataName, dataTypeClassName, isAuditable, isCustomAttribute, conditionalRuleAuthorizedFields);
    }

    public static DataType getActor() {
        return DataType.getDataType(IMafConstants.Actor);
    }

    public static DataType getApplicationBlock() {
        return DataType.getDataType(IMafConstants.ApplicationBlock);
    }

    public static DataType getBudgetBucket() {
        return DataType.getDataType(IMafConstants.BudgetBucket);
    }

    public static DataType getBudgetBucketLine() {
        return DataType.getDataType(IMafConstants.BudgetBucketLine);
    }

    public static DataType getCostCenter() {
        return DataType.getDataType(IMafConstants.CostCenter);
    }

    public static DataType getIteration() {
        return DataType.getDataType(IMafConstants.Iteration);
    }

    public static DataType getOrgUnit() {
        return DataType.getDataType(IMafConstants.OrgUnit);
    }

    public static DataType getPortfolioEntryBudget() {
        return DataType.getDataType(IMafConstants.PortfolioEntryBudget);
    }

    public static DataType getPortfolioEntryBudgetLine() {
        return DataType.getDataType(IMafConstants.PortfolioEntryBudgetLine);
    }

    public static DataType getPortfolioEntryEvent() {
        return DataType.getDataType(IMafConstants.PortfolioEntryEvent);
    }

    public static DataType getPortfolioEntry() {
        return DataType.getDataType(IMafConstants.PortfolioEntry);
    }

    public static DataType getPortfolioEntryPlanningPackage() {
        return DataType.getDataType(IMafConstants.PortfolioEntryPlanningPackage);
    }

    public static DataType getPortfolioEntryReport() {
        return DataType.getDataType(IMafConstants.PortfolioEntryReport);
    }

    public static DataType getPortfolioEntryResourcePlanAllocatedActor() {
        return DataType.getDataType(IMafConstants.PortfolioEntryResourcePlanAllocatedActor);
    }

    public static DataType getPortfolioEntryResourcePlanAllocatedOrgUnit() {
        return DataType.getDataType(IMafConstants.PortfolioEntryResourcePlanAllocatedOrgUnit);
    }

    public static DataType getPortfolioEntryResourcePlanAllocatedCompetency() {
        return DataType.getDataType(IMafConstants.PortfolioEntryResourcePlanAllocatedCompetency);
    }

    public static DataType getPortfolioEntryRisk() {
        return DataType.getDataType(IMafConstants.PortfolioEntryRisk);
    }

    public static DataType getPortfolio() {
        return DataType.getDataType(IMafConstants.Portfolio);
    }

    public static DataType getStakeholder() {
        return DataType.getDataType(IMafConstants.Stakeholder);
    }

    public static DataType getPurchaseOrderLineItem() {
        return DataType.getDataType(IMafConstants.PurchaseOrderLineItem);
    }

    public static DataType getPurchaseOrder() {
        return DataType.getDataType(IMafConstants.PurchaseOrder);
    }

    public static DataType getRelease() {
        return DataType.getDataType(IMafConstants.Release);
    }

    public static DataType getRequirement() {
        return DataType.getDataType(IMafConstants.Requirement);
    }

    public static DataType getTimesheetActivityAllocatedActor() {
        return DataType.getDataType(IMafConstants.TimesheetActivityAllocatedActor);
    }

    public static DataType getWorkOrder() {
        return DataType.getDataType(IMafConstants.WorkOrder);
    }
}
