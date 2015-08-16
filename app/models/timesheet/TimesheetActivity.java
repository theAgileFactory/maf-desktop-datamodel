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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;

import com.avaje.ebean.annotation.Where;

import framework.utils.Msg;

/**
 * A timesheet activity is an object that is not a portfolio entry and for which
 * an actor can log time.
 * 
 * @author Johann Kohler
 */
@Entity
public class TimesheetActivity extends Model implements IModel {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Required
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING)
    public String description;

    @ManyToOne(cascade = CascadeType.ALL)
    public TimesheetActivityType timesheetActivityType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timesheetActivity")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetEntry> timesheetEntries;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timesheetActivity")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetActivityAllocatedActor> timesheetActivityAllocatedActors;

    /**
     * Default constructor.
     */
    public TimesheetActivity() {
    }

    /**
     * Get the description.
     */
    public String getDescription() {
        return Msg.get(this.description);
    }

    /**
     * Get the name.
     */
    public String getName() {
        return Msg.get(this.name);
    }

    @Override
    public String audit() {
        return "PortfolioEntryPlanningPackage [id=" + id + ", name=" + name + ", description=" + description + " ]";
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
