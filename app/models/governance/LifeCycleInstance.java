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

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonProperty;
import framework.services.api.commons.IApiObject;
import models.framework_models.parent.IModel;
import models.pmo.PortfolioEntry;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * A portfolio entry is associated with a life cycle instance. It is a specific
 * occurrence of a configured life cycle process.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
public class LifeCycleInstance extends Model implements IModel, IApiObject {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @JsonProperty
    public Boolean isActive;

    public Boolean isConcept;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntry portfolioEntry;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleProcess lifeCycleProcess;

    @OneToMany(mappedBy = "lifeCycleInstance")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("passedDate")
    public List<LifeCycleMilestoneInstance> lifeCycleMilestoneInstances;

    @OneToMany(mappedBy = "lifeCycleInstance")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCycleInstancePlanning> lifeCycleInstancePlannings;

    @OneToMany(mappedBy = "lifeCycleInstance")
    @Where(clause = "${ta}.deleted=0 AND ${ta}.is_frozen=0")
    public List<LifeCycleInstancePlanning> currentLifeCycleInstancePlannings;

    @OneToOne(mappedBy = "activeLifeCycleInstance")
    public PortfolioEntry portfolioEntryWithCurrentInstanceAsActive;

    /**
     * Default constructor.
     */
    public LifeCycleInstance() {
    }

    /**
     * Construct of life cycle instance for a process and a portfolio entry.
     * 
     * @param lifeCycleProcess
     *            the life cycle process
     * @param portfolioEntry
     *            the portfolio entry
     */
    public LifeCycleInstance(LifeCycleProcess lifeCycleProcess, PortfolioEntry portfolioEntry) {
        this.isActive = true;
        this.isConcept = true;
        this.lifeCycleProcess = lifeCycleProcess;
        this.portfolioEntry = portfolioEntry;
    }

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", isActive=" + isActive + "]";
    }

    @Override
    public void defaults() {
        isConcept = true;
        isActive = true;
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    /**
     * Set as inactive.
     */
    public void doInactive() {
        isActive = false;
        save();
    }

    /**
     * get the current (not frozen) life cycle instance planning.
     * 
     * note: if there isn't current planning, then we set the more recent as the
     * current and return it
     */
    public LifeCycleInstancePlanning getCurrentLifeCycleInstancePlanning() {
        if (currentLifeCycleInstancePlannings != null && currentLifeCycleInstancePlannings.size() > 0) {
            return currentLifeCycleInstancePlannings.get(0);
        } else if (lifeCycleInstancePlannings != null && lifeCycleInstancePlannings.size() > 0) {
            LifeCycleInstancePlanning planning = lifeCycleInstancePlannings.get(0);
            planning.isFrozen = false;
            return planning;
        }
        return null;
    }

    /**
     * get the first planned milestone in the current planning.
     * 
     * @return PlannedLifeCycleMilestoneInstance
     */
    public PlannedLifeCycleMilestoneInstance getFirstPlannedLifecycleMilestoneInstance() {
    	LifeCycleInstancePlanning currentLifecycleInstancePlanning = this.getCurrentLifeCycleInstancePlanning();
    	List<PlannedLifeCycleMilestoneInstance> milestones = currentLifecycleInstancePlanning.plannedLifeCycleMilestoneInstance;
        return milestones.stream().min((p1, p2) -> Integer.compare(p1.lifeCycleMilestone.order, p2.lifeCycleMilestone.order)).get();
    }

    /**
     * get the last planned milestone in the current planning
     * 
     * @return PlannedLifeCycleMilestoneInstance
     */
    public PlannedLifeCycleMilestoneInstance getLastPlannedLifecycleMilestoneInstance() {
    	LifeCycleInstancePlanning currentLifecycleInstancePlanning = this.getCurrentLifeCycleInstancePlanning();
    	List<PlannedLifeCycleMilestoneInstance> milestones = currentLifecycleInstancePlanning.plannedLifeCycleMilestoneInstance;
    	return milestones.stream().max((p1, p2) -> Integer.compare(p1.lifeCycleMilestone.order, p2.lifeCycleMilestone.order)).get();
    }

    @Override
    public String getApiName() {
        return null;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
