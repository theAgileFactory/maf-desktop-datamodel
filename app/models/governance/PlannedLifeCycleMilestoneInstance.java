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

import framework.services.api.commons.IApiObject;
import framework.utils.formats.DateType;
import models.common.BizDockModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * A planned life cycle milestone instance (shortly called planned date) is
 * attached to life cycle instance planning (shortly called planning) for a
 * specific milestone.
 * 
 * @author Johann Kohler
 */
@Entity
public class PlannedLifeCycleMilestoneInstance extends BizDockModel implements IApiObject {

    @Id
    public Long id;

    @DateType
    public Date plannedDate;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleInstancePlanning lifeCycleInstancePlanning;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleMilestone lifeCycleMilestone;

    /**
     * Default constructor.
     */
    public PlannedLifeCycleMilestoneInstance() {
    }

    /**
     * Construct a planned life cycle milestone instance for a planning and a
     * milestone.
     * 
     * @param lifeCycleInstancePlanning
     *            the life cycle instance planning
     * @param milestone
     *            the milestone
     */
    public PlannedLifeCycleMilestoneInstance(LifeCycleInstancePlanning lifeCycleInstancePlanning, LifeCycleMilestone milestone) {
        this.lifeCycleInstancePlanning = lifeCycleInstancePlanning;
        this.lifeCycleMilestone = milestone;
    }

    @Override
    public String audit() {
        return "PlannedLifeCycleMilestoneInstance [deleted=" + deleted + ", lastUpdate=" + lastUpdate + ", id=" + id + ", plannedDate=" + plannedDate + "]";
    }

    /**
     * Get the status of the planned milestone instance.
     */
    public Status getStatus() {
        if (this.plannedDate != null) {
            return Status.AVAILABLE;
        } else {
            return Status.NOT_PLANNED;
        }
    }

    /**
     * Possible status.
     * 
     * @author Johann Kohler
     */
    public enum Status {
        AVAILABLE, NOT_PLANNED
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
