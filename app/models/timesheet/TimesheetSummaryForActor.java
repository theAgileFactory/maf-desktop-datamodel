package models.timesheet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimesheetSummaryForActor {

    @JsonProperty
    public String month;

    @JsonProperty
    public String week;

    @JsonProperty
    public Double hours;

    public TimesheetSummaryForActor() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
