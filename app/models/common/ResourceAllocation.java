package models.common;

import com.avaje.ebean.Model;
import framework.utils.Msg;
import framework.utils.Utilities;
import models.finance.PortfolioEntryResourcePlanAllocationStatusType;
import models.pmo.PortfolioEntry;
import org.apache.commons.lang3.tuple.Pair;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.*;

@MappedSuperclass
public abstract class ResourceAllocation extends BizDockModel {

    public static Map<Pair<Integer, Integer>, Double> getAllocationDistribution(Date startDate, Date endDate, BigDecimal daysToDistribute, boolean workingDaysOnly) {
        Map<Pair<Integer, Integer>, Double> daysMap = new HashMap<>();
        if (startDate != null && endDate != null && daysToDistribute != null) {

            // Distribute allocations monthly from start date to end date
            int numberOfDays = Utilities.getDuration(startDate, endDate);
            Double dayRate;

            if (workingDaysOnly) {
                int numberOfWorkingDays = Utilities.getWorkingDaysCount(startDate, endDate);
                if (numberOfWorkingDays == 0) {
                    return null;
                }
                dayRate = daysToDistribute.doubleValue() / numberOfWorkingDays;
            } else {
                dayRate = daysToDistribute.doubleValue() / numberOfDays;
            }

            Calendar start = removeTime(startDate);
            for (int i = 0; i < numberOfDays; i++) {
                Pair<Integer, Integer> month = Pair.of(start.get(Calendar.YEAR), start.get(Calendar.MONTH));
                Double d = daysMap.get(month) == null ? 0.0 : daysMap.get(month);
                if (!workingDaysOnly || (start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
                    daysMap.put(month, d + dayRate);
                }
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return daysMap;
    }

    private static Calendar removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public ResourceAllocationDetail createOrUpdateAllocationDetail(Integer year, Integer month, Double days) {
        if (year == null || month == null || days == null) {
            return null;
        }
        Optional<? extends ResourceAllocationDetail> optionalDetail = this.getDetails().stream().filter(detail -> detail.getYear().equals(year) && detail.getMonth().equals(month)).findFirst();
        ResourceAllocationDetail detail;
        if (optionalDetail.isPresent()) {
            // Update
            detail = optionalDetail.get();
            detail.setDays(days);
            detail.update();
        } else {
            // Create
            detail = createDetail(this, year, month, days);
            detail.save();
        }
        return detail;
    }

    /**
     * Clear all allocations details from allocation
     */
    public void clearAllocations() {
        List<? extends ResourceAllocationDetail> details = getDetails();
        for (ResourceAllocationDetail detail : details) {
            detail.doDelete();
            detail.save();
        }
        details.clear();
    }

    /**
     * Get an allocation detail by year and month.
     *
     * @param year the year of the allocation
     * @param month the month of the allocation
     *
     * @return the allocation detail or null if none are found
     */
    public ResourceAllocationDetail getDetail(int year, int month) {
        Optional<? extends ResourceAllocationDetail> optionalDetail = this.getDetails().stream().filter(detail -> detail.getMonth().equals(month) && detail.getYear().equals(year)).findFirst();
        return optionalDetail.orElse(null);
    }

    /**
     * Compute the allocation details based on allocation start date and end date. The allocated days will be distributed evenly over the given period and allocation details created accordingly.
     *
     * @param isForecast true if the distribution must be done over the forecast days. Budget days will be taken instead.
     * @param workingDaysOnly if true, only working days will be taken into account for the distribution.
     */
    public void computeAllocationDetails(boolean isForecast, boolean workingDaysOnly) {
        if (this.getStartDate() != null && this.getEndDate() != null) {
            // Clear current allocation details
            this.clearAllocations();

            Map<Pair<Integer, Integer>, Double> daysMap = getAllocationDistribution(this.getStartDate(), this.getEndDate(), isForecast ? this.getForecastDays() : this.getDays(), workingDaysOnly);
            if (daysMap != null) {
                for (Pair<Integer, Integer> month : daysMap.keySet()) {
                    createOrUpdateAllocationDetail(month.getLeft(), month.getRight(), daysMap.get(month));
                }
            }
        }
    }

    /**
     * Get the date for a display.
     */
    public String getDisplayDate() {
        if (this.getStartDate() != null && this.getEndDate() != null) {
            return Msg.get("object.allocated_resource.date.period", Utilities.getDateFormat(null).format(this.getStartDate()),
                    Utilities.getDateFormat(null).format(this.getEndDate()));
        } else if (this.getEndDate() != null) {
            return Utilities.getDateFormat(null).format(this.getEndDate());
        } else {
            return null;
        }
    }

    public boolean isConfirmed() {
        return getPortfolioEntryResourcePlanAllocationStatusType() != null && getPortfolioEntryResourcePlanAllocationStatusType().status.equals(PortfolioEntryResourcePlanAllocationStatusType.AllocationStatus.CONFIRMED);
    }

    public abstract PortfolioEntryResourcePlanAllocationStatusType getPortfolioEntryResourcePlanAllocationStatusType();

    public abstract BigDecimal getDays();

    public abstract BigDecimal getForecastDays();

    public abstract Date getEndDate();

    public abstract Date getStartDate();

    protected abstract ResourceAllocationDetail createDetail(ResourceAllocation resourceAllocation, Integer year, Integer month, Double days);

    public abstract List<? extends ResourceAllocationDetail> getDetails();

    public abstract PortfolioEntry getAssociatedPortfolioEntry();

}
