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

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Where;
import framework.services.api.commons.IApiObject;
import framework.utils.formats.DateType;
import models.finance.PortfolioEntryBudget;
import models.finance.PortfolioEntryResourcePlan;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A life cycle instance planning (shortly called planning) is a set of planned
 * dates, one for each milestone.
 * 
 * @author Johann Kohler
 */
@Entity
public class LifeCycleInstancePlanning extends Model implements IModel, IApiObject {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @DateType
    public Date creationDate;

    @Column(scale = IModelConstants.VERSION_SCALE, nullable = false)
    public int version;

    public Boolean isFrozen;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = false)
    public String comments;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleInstance lifeCycleInstance;

    @OneToMany(mappedBy = "lifeCycleInstancePlanning")
    @Where(clause = "${ta}.deleted=0")
    public List<PlannedLifeCycleMilestoneInstance> plannedLifeCycleMilestoneInstance;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryResourcePlan portfolioEntryResourcePlan;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryBudget portfolioEntryBudget;

    /**
     * Default constructor.
     */
    public LifeCycleInstancePlanning() {
    }

    /**
     * Construct a life cycle instance planning for a life cycle instance.
     * 
     * @param lifeCycleInstance
     *            the life cycle instance
     */
    public LifeCycleInstancePlanning(LifeCycleInstance lifeCycleInstance) {

        /**
         * Compute the version.
         */
        int version = 0;
        List<LifeCycleInstancePlanning> plannings =
                Ebean.find(LifeCycleInstancePlanning.class).orderBy("version DESC").where().eq("deleted", false)
                        .eq("lifeCycleInstance.id", lifeCycleInstance.id).setMaxRows(1).findList();
        if (plannings.size() > 0) {
            version = plannings.get(0).version;
        }

        this.creationDate = new Date();
        this.version = version + 1;
        this.isFrozen = false;
        this.name = "";
        this.lifeCycleInstance = lifeCycleInstance;
        this.portfolioEntryBudget = new PortfolioEntryBudget();
        this.portfolioEntryResourcePlan = new PortfolioEntryResourcePlan();

    }

    @Override
    public String audit() {
        return "LifeCycleInstancePlanning [deleted=" + deleted + ", lastUpdate=" + lastUpdate + ", id=" + id + ", creationDate=" + creationDate
                + ", version=" + version + ", isFrozen=" + isFrozen + ", name=" + name + ", comments=" + comments + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        update();
    }

    /**
     * Set as frozen.
     */
    public void doFrozen() {
        isFrozen = true;
        save();
    }

    @Override
    public String getApiName() {
        return name;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    public boolean isActive() {
        return !this.deleted && !this.isFrozen;
    }

    public PlannedLifeCycleMilestoneInstance getLastUpdatedMilestoneInstance() {
        if (this.plannedLifeCycleMilestoneInstance != null && !plannedLifeCycleMilestoneInstance.isEmpty()) {
            return this.plannedLifeCycleMilestoneInstance.stream().max(Comparator.comparing(c -> c.lastUpdate)).orElse(null);
        }
        return null;
    }
}
