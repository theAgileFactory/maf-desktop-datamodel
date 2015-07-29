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
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.Where;

import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;

/**
 * A type of a timesheet activity.
 * 
 * @author Johann Kohler
 */
@Entity
public class TimesheetActivityType extends Model implements IModel, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = -3098525590598336560L;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timesheetActivityType")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetActivity> timesheetActivities;

    /**
     * Default constructor.
     */
    public TimesheetActivityType() {
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
        return "PortfolioEntryPlanningPackage [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Long getValue() {
        return id;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public void setUrl(String arg0) {
    }

}
