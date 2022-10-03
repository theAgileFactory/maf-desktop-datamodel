package models.timesheet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimesheetSummaryForActor {

    @JsonProperty
    public String groupBy;

    @JsonProperty
    public String period;

    @JsonProperty
    public Double hours;

    public TimesheetSummaryForActor() {
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
