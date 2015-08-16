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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.pmo.PortfolioEntry;
import models.pmo.PortfolioEntryPlanningPackage;
import com.avaje.ebean.Model;

import com.avaje.ebean.annotation.Where;

/**
 * A timesheet entry is a set of timesheet logs for a timesheet activity or a
 * portfolio entry.
 * 
 * @author Johann Kohler
 */
@Entity
public class TimesheetEntry extends Model implements IModel {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL)
    public TimesheetReport timesheetReport;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntry portfolioEntry;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryPlanningPackage portfolioEntryPlanningPackage;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public TimesheetActivity timesheetActivity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timesheetEntry")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("logDate")
    public List<TimesheetLog> timesheetLogs;

    /**
     * Default constructor.
     */
    public TimesheetEntry() {
    }

    @Override
    public String audit() {
        return "PortfolioEntryPlanningPackage [id=" + id + " ]";
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
     * Get the total hours of the entry.
     */
    public Double getTotal() {
        Double total = 0.0;
        for (TimesheetLog log : timesheetLogs) {
            if (log.hours != null) {
                total += log.hours;
            }
        }
        return total;
    }

}
