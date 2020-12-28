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

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import framework.utils.formats.DateType;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import models.framework_models.parent.IModel;
import play.data.validation.Constraints.Required;

/**
 * A timesheet log is a time logged for a timesheet entry and a day.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimesheetLog extends Model implements IModel {

    @Id
    @JsonProperty
    public Long id;

    @JsonProperty
    public boolean deleted = false;

    @Version
    @JsonProperty
    public Timestamp lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL)
    public TimesheetEntry timesheetEntry;

    @Required
    @DateType
    @JsonProperty
    public Date logDate;

    @Required
    @JsonProperty
    public Double hours;

    /**
     * Default constructor.
     */
    public TimesheetLog() {
    }

    @Override
    public String audit() {
        return "PortfolioEntryPlanningPackage [id=" + id + ", logDate=" + logDate + ", hours=" + hours + " ]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }
}
