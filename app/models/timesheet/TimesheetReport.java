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
package models.timesheet;

import com.fasterxml.jackson.annotation.JsonProperty;
import framework.services.api.commons.JsonPropertyLink;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumMapping;
import com.avaje.ebean.annotation.Where;

import framework.utils.formats.DateType;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import models.pmo.OrgUnit;
import play.data.validation.Constraints.Required;

/**
 * A timesheet report is a set of timesheet entries (and so a set of log times)
 * and represents the physical sheet filled by an employee. Usually a report is
 * for a week or for month, but currently the system supports only a weekly
 * report.
 * 
 * @author Johann Kohler
 */
@Entity
public class TimesheetReport extends Model implements IModel {

    @Id
    @JsonProperty
    public Long id;

    public boolean deleted = false;

    @Version
    @JsonProperty
    public Timestamp lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonPropertyLink
    public Actor actor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonPropertyLink
    public OrgUnit orgUnit;

    @Required
    @Column(length = IModelConstants.SMALL_STRING)
    @JsonProperty
    public Type type = Type.WEEKLY;

    @Required
    @DateType
    @JsonProperty
    public Date startDate;

    @Required
    @Column(length = IModelConstants.SMALL_STRING)
    @JsonProperty
    public Status status = Status.OPEN;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timesheetReport")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetEntry> timesheetEntries;

    /**
     * Default constructor.
     */
    public TimesheetReport() {
    }

    @Override
    public String audit() {
        return "PortfolioEntryPlanningPackage [id=" + id + ", type=" + type.name() + ", startDate=" + startDate + ", status=" + status.name() + " ]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    /**
     * Get the end date of the report (depending of the type).
     */
    public Date getEndDate() {
        switch (this.type) {
        case WEEKLY:
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.startDate);
            cal.add(Calendar.DAY_OF_YEAR, 6);
            return cal.getTime();
        }
        return null;
    }

    /**
     * Get the start date with an increment (in day numbers).
     * 
     * @param inc
     *            the number of days after the start date
     */
    public Date getIncDate(int inc) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.startDate);
        cal.add(Calendar.DAY_OF_YEAR, inc);
        return cal.getTime();
    }

    /**
     * Get the start date of the previous report (depending of the type).
     * 
     */
    public Date getPreviousStartDate() {
        switch (this.type) {
        case WEEKLY:
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.startDate);
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            return cal.getTime();
        }
        return null;
    }

    /**
     * Get the start date of the next report (depending of the type).
     * 
     */
    public Date getNextStartDate() {
        switch (this.type) {
        case WEEKLY:
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.startDate);
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            return cal.getTime();
        }
        return null;
    }

    /**
     * Return true if the timesheet can be filled by the user.
     */
    public boolean isEditable() {
        switch (this.status) {
        case APPROVED:
        case LOCKED:
        case SUBMITTED:
            return false;
        case OPEN:
        case REJECTED:
        case UNLOCKED:
            return true;
        }

        return false;
    }

    /**
     * Return true if the timesheet could by processed.
     */
    public boolean isProcessable() {
        return !this.status.equals(Status.LOCKED) && !this.status.equals(Status.OPEN);
    }

    /**
     * Return true if the timesheet is approved.
     */
    public boolean isApproved() {
        return this.status.equals(Status.APPROVED);
    }

    /**
     * Return true if the timesheet is rejected.
     */
    public boolean isRejected() {
        return this.status.equals(Status.REJECTED);
    }

    /**
     * Get the status CSS bootstrap class.
     */
    public String getStatusCssClass() {
        switch (this.status) {
        case APPROVED:
            return "success";
        case LOCKED:
            return "default";
        case OPEN:
            return "primary";
        case REJECTED:
            return "danger";
        case SUBMITTED:
            return "warning";
        case UNLOCKED:
            return "info";
        }
        return "";
    }

    /**
     * Get the total hours of the report.
     */
    public Double getTotal() {
        Double total = 0.0;
        for (TimesheetEntry entry : timesheetEntries) {
            total += entry.getTotal();
        }
        return total;
    }

    /**
     * Define the types of the timesheet report.
     * 
     * @author Johann Kohler
     */
    @EnumMapping(nameValuePairs = "WEEKLY=WEEKLY", integerType = false)
    public enum Type {
        WEEKLY
    }

    /**
     * Define the status of the timesheet report.
     * 
     * @author Johann Kohler
     */
    @EnumMapping(nameValuePairs = "OPEN=OPEN, SUBMITTED=SUBMITTED, REJECTED=REJECTED, APPROVED=APPROVED, LOCKED=LOCKED, UNLOCKED=UNLOCKED",
            integerType = false)
    public enum Status {
        OPEN, SUBMITTED, REJECTED, APPROVED, LOCKED, UNLOCKED
    }

}
