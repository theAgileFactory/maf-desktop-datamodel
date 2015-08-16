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
package models.governance;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import com.avaje.ebean.Model;

import com.avaje.ebean.annotation.Where;

import framework.services.api.commons.IApiObject;
import framework.utils.Msg;

/**
 * A life cycle process is made of multiple life cycle milestones. A milestone
 * is an optional or mandatory step in the life of the project. It is usually
 * associated with a budget, a resource plan and an approval from the process
 * board members.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
public class LifeCycleMilestone extends Model implements IModel, IApiObject {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.SMALL_STRING, nullable = false)
    public String shortName;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    public String description;

    @Column(name = "`order`", scale = 5)
    public int order;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleProcess lifeCycleProcess;

    public boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "lifeCycleMilestones")
    @Where(clause = "${ta}.deleted=0")
    public List<Actor> approvers;

    @OneToMany(mappedBy = "lifeCycleMilestone")
    public List<LifeCycleMilestoneInstance> lifeCycleMilestoneInstances;

    @OneToMany(mappedBy = "lifeCycleMilestone")
    public List<PlannedLifeCycleMilestoneInstance> plannedLifeCycleMilestoneInstances;

    public boolean isReviewRequired;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public LifeCycleMilestoneInstanceStatusType defaultLifeCycleMilestoneInstanceStatusType;

    @OneToMany(mappedBy = "startLifeCycleMilestone")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCyclePhase> startLifeCyclePhases;

    @OneToMany(mappedBy = "endLifeCycleMilestone")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCyclePhase> endLifeCyclePhases;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", shortName=" + shortName + ", name=" + name + ", description=" + description + ", order="
                + order + "]";
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
     * Get the name.
     */
    public String getName() {
        return Msg.get(name);
    }

    /**
     * Get the short name.
     */
    public String getShortName() {
        return Msg.get(shortName);
    }

    /**
     * Get the i18n key of the short name.
     */
    public String getShortNameKey() {
        return shortName;
    }

    /**
     * Get the description.
     */
    public String getDescription() {
        return Msg.get(description);
    }

    @Override
    public String getApiName() {
        return getName();
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
