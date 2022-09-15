package models.timesheet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import models.pmo.Actor;

/**
 * Summary of timesheet logs for a specific actor on a specific portfolio entry
 *
 * @author Guillaume Petit
 */
public class TimesheetSummary {

    public Actor actor;

    public List<TimesheetSummaryForActor> timesheetSummary;

    public TimesheetSummary() {
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public List<TimesheetSummaryForActor> getTimesheetSummary() {
        return timesheetSummary;
    }

    public void setTimesheetSummary(List<TimesheetSummaryForActor> timesheetSummary) {
        this.timesheetSummary = timesheetSummary;
    }

    public static void main(String args[]) {
        TimesheetSummary summary = new TimesheetSummary();
        try {
            String s = new ObjectMapper().writeValueAsString(summary);
            System.out.println(s);
        } catch (JsonProcessingException e) {

        }

    }
}
