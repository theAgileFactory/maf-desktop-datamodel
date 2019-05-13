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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * get the previous (current version - 1) lifecycle instance planning.
     */
    public LifeCycleInstancePlanning getPreviousLifeCycleInstancePlanning() {
        LifeCycleInstancePlanning currentPlanning = getCurrentLifeCycleInstancePlanning();
        Optional<LifeCycleInstancePlanning> previousPlanning = lifeCycleInstancePlannings.stream()
                .filter(planning -> !planning.deleted && planning.version < currentPlanning.version)
                .min((p1, p2) -> Integer.compare(p2.version, p1.version));
        return previousPlanning.orElse(currentPlanning);
    }

    /**
     * get the first planned milestone in the current planning.
     *
     * @return PlannedLifeCycleMilestoneInstance
     */
    public Date getStartDate() {

        // Get the global minimum value of the order for the milestones in all plannings
        LifeCycleMilestone lcmMinOrder = this.lifeCycleInstancePlannings
                .stream()
                // Get local minimum for the milestone order in each planning
                .map(planning -> planning.plannedLifeCycleMilestoneInstance
                        .stream()
                        .min(Comparator.comparingInt(a -> a.lifeCycleMilestone.order * 10 + a.lifeCycleMilestone.subOrder))
                        .orElse(new PlannedLifeCycleMilestoneInstance()).lifeCycleMilestone
                )
                // Get global minimum
                .min(Comparator.comparingInt(value -> value.order * 10 + value.subOrder))
                .orElse(new LifeCycleMilestone());

        final int minOrder = lcmMinOrder.order * 10 + lcmMinOrder.subOrder;

        // Select the highest version of the planning containing the milestone with the lowest order
        LifeCycleInstancePlanning planning = this.lifeCycleInstancePlannings
            .stream()
            // Filter out plannings that don't contain the milestone with the order min
            .filter(p -> p.plannedLifeCycleMilestoneInstance
                .stream()
                .anyMatch(plannedMilestone -> plannedMilestone.lifeCycleMilestone.order * 10 + plannedMilestone.lifeCycleMilestone.subOrder == minOrder))
            // Get the planning with the highest version
            .max(Comparator.comparingInt(p -> p.version))
            .orElse(null);

        // Return the planned date of the milestone with the lowest order
        return planning != null ? planning.plannedLifeCycleMilestoneInstance
                .stream()
                .min(Comparator.comparingInt(m -> m.lifeCycleMilestone.order * 10 + m.lifeCycleMilestone.subOrder))
                .orElse(new PlannedLifeCycleMilestoneInstance())
                .plannedDate : null;
    }

    /**
     * get the last planned milestone in the current planning
     *
     * @return PlannedLifeCycleMilestoneInstance
     */
    public Date getEndDate() {
        // Get the global maximum value of the order for the milestones in all plannings
        LifeCycleMilestone lcmMaxOrder = this.lifeCycleInstancePlannings
                .stream()
                // Get local maximum for the milestone order in each planning
                .map(planning -> planning.plannedLifeCycleMilestoneInstance
                        .stream()
                        .max(Comparator.comparingInt(a -> a.lifeCycleMilestone.order * 10 + a.lifeCycleMilestone.subOrder))
                        .orElse(new PlannedLifeCycleMilestoneInstance()).lifeCycleMilestone
                )
                // Get global maximum
                .max(Comparator.comparingInt(value -> value.order * 10 + value.subOrder))
                .orElse(new LifeCycleMilestone());

        final int maxOrder =lcmMaxOrder.order * 10 + lcmMaxOrder.subOrder;

        // Select the highest version of the planning containing the milestone with the highest order
        LifeCycleInstancePlanning planning = this.lifeCycleInstancePlannings
                .stream()
                // Filter out plannings that don't contain the milestone with the order max
                .filter(p -> p.plannedLifeCycleMilestoneInstance
                        .stream()
                        .anyMatch(plannedMilestone -> plannedMilestone.lifeCycleMilestone.order *10 + plannedMilestone.lifeCycleMilestone.subOrder == maxOrder))
                // Get the planning with the highest version
                .max(Comparator.comparingInt(p -> p.version))
                .orElse(new LifeCycleInstancePlanning());

        // Return the planned date of the milestone with the highest order
        return planning.plannedLifeCycleMilestoneInstance
                .stream()
                .max(Comparator.comparingInt(m -> m.lifeCycleMilestone.order * 10 + m.lifeCycleMilestone.subOrder))
                .orElse(new PlannedLifeCycleMilestoneInstance())
                .plannedDate;
    }

    @Override
    public String getApiName() {
        return null;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    /**
     * Get approved life cycle milestone instances
     */
    public List<LifeCycleMilestoneInstance> getApprovedLifecycleMilestoneInstances() {
        return lifeCycleMilestoneInstances.stream()
                .filter(milestone -> milestone.lifeCycleMilestoneInstanceStatusType != null && milestone.lifeCycleMilestoneInstanceStatusType.isApproved)
                .collect(Collectors.toList());
    }
}
