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

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import framework.utils.formats.DateType;

/**
 * A timesheet log is a time logged for a timesheet entry and a day.
 * 
 * @author Johann Kohler
 */
@Entity
public class TimesheetLog extends Model implements IModel {

    private static final long serialVersionUID = 6303479530512271365L;

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL)
    public TimesheetEntry timesheetEntry;

    @Required
    @DateType
    public Date logDate;

    @Required
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
