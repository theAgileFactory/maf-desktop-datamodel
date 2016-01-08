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
package models.finance;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Where;

import framework.services.api.commons.IApiObject;
import framework.utils.formats.DateType;
import models.framework_models.parent.IModel;
import models.governance.LifeCycleInstancePlanning;
import models.governance.LifeCycleMilestoneInstance;

/**
 * The portfolioEntry resource plan is the “human budget” of the project.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
public class PortfolioEntryResourcePlan extends Model implements IModel, IApiObject {

    @Id
    public long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @DateType
    public Date allocationDate;

    @OneToMany(mappedBy = "portfolioEntryResourcePlan")
    public List<LifeCycleInstancePlanning> lifeCycleInstancePlannings;

    @OneToMany(mappedBy = "portfolioEntryResourcePlan")
    public List<LifeCycleMilestoneInstance> lifeCycleMilestoneInstances;

    @OneToMany(mappedBy = "portfolioEntryResourcePlan")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("endDate")
    public List<PortfolioEntryResourcePlanAllocatedActor> portfolioEntryResourcePlanAllocatedActors;

    @OneToMany(mappedBy = "portfolioEntryResourcePlan")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("endDate")
    public List<PortfolioEntryResourcePlanAllocatedOrgUnit> portfolioEntryResourcePlanAllocatedOrgUnits;

    @OneToMany(mappedBy = "portfolioEntryResourcePlan")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("endDate")
    public List<PortfolioEntryResourcePlanAllocatedCompetency> portfolioEntryResourcePlanAllocatedCompetencies;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", allocationDate=" + allocationDate + "]";
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
     * Clone a portfolio entry resource plan (with its allocated actors and org
     * units) in the DB and return the clone.
     * 
     */
    public PortfolioEntryResourcePlan cloneInDB(Map<String, Map<Long, Long>> allocatedResourcesMapOldToNew) {

        allocatedResourcesMapOldToNew.put(PortfolioEntryResourcePlanAllocatedActor.class.getName(), new HashMap<>());
        allocatedResourcesMapOldToNew.put(PortfolioEntryResourcePlanAllocatedOrgUnit.class.getName(), new HashMap<>());
        allocatedResourcesMapOldToNew.put(PortfolioEntryResourcePlanAllocatedCompetency.class.getName(), new HashMap<>());

        PortfolioEntryResourcePlan newResourcePlan = new PortfolioEntryResourcePlan();
        newResourcePlan.allocationDate = this.allocationDate;
        newResourcePlan.save();
        for (PortfolioEntryResourcePlanAllocatedActor allocatedActor : this.portfolioEntryResourcePlanAllocatedActors) {
            PortfolioEntryResourcePlanAllocatedActor newAllocatedActor = new PortfolioEntryResourcePlanAllocatedActor();
            newAllocatedActor.actor = allocatedActor.actor;
            newAllocatedActor.endDate = allocatedActor.endDate;
            newAllocatedActor.days = allocatedActor.days;
            newAllocatedActor.startDate = allocatedActor.startDate;
            newAllocatedActor.portfolioEntryPlanningPackage = allocatedActor.portfolioEntryPlanningPackage;
            newAllocatedActor.isConfirmed = allocatedActor.isConfirmed;
            newAllocatedActor.followPackageDates = allocatedActor.followPackageDates;
            newAllocatedActor.dailyRate = allocatedActor.dailyRate;
            newAllocatedActor.forecastDays = allocatedActor.forecastDays;
            newAllocatedActor.portfolioEntryResourcePlan = newResourcePlan;
            newAllocatedActor.save();
            newResourcePlan.portfolioEntryResourcePlanAllocatedActors.add(newAllocatedActor);
            allocatedResourcesMapOldToNew.get(PortfolioEntryResourcePlanAllocatedActor.class.getName()).put(allocatedActor.id, newAllocatedActor.id);
        }
        for (PortfolioEntryResourcePlanAllocatedOrgUnit allocatedOrgUnit : this.portfolioEntryResourcePlanAllocatedOrgUnits) {
            PortfolioEntryResourcePlanAllocatedOrgUnit newAllocatedOrgUnit = new PortfolioEntryResourcePlanAllocatedOrgUnit();
            newAllocatedOrgUnit.orgUnit = allocatedOrgUnit.orgUnit;
            newAllocatedOrgUnit.endDate = allocatedOrgUnit.endDate;
            newAllocatedOrgUnit.days = allocatedOrgUnit.days;
            newAllocatedOrgUnit.startDate = allocatedOrgUnit.startDate;
            newAllocatedOrgUnit.portfolioEntryPlanningPackage = allocatedOrgUnit.portfolioEntryPlanningPackage;
            newAllocatedOrgUnit.isConfirmed = allocatedOrgUnit.isConfirmed;
            newAllocatedOrgUnit.followPackageDates = allocatedOrgUnit.followPackageDates;
            newAllocatedOrgUnit.dailyRate = allocatedOrgUnit.dailyRate;
            newAllocatedOrgUnit.forecastDays = allocatedOrgUnit.forecastDays;
            newAllocatedOrgUnit.portfolioEntryResourcePlan = newResourcePlan;
            newAllocatedOrgUnit.save();
            newResourcePlan.portfolioEntryResourcePlanAllocatedOrgUnits.add(newAllocatedOrgUnit);
            allocatedResourcesMapOldToNew.get(PortfolioEntryResourcePlanAllocatedOrgUnit.class.getName()).put(allocatedOrgUnit.id, newAllocatedOrgUnit.id);
        }
        for (PortfolioEntryResourcePlanAllocatedCompetency allocatedCompetency : this.portfolioEntryResourcePlanAllocatedCompetencies) {
            PortfolioEntryResourcePlanAllocatedCompetency newAllocatedCompetency = new PortfolioEntryResourcePlanAllocatedCompetency();
            newAllocatedCompetency.competency = allocatedCompetency.competency;
            newAllocatedCompetency.endDate = allocatedCompetency.endDate;
            newAllocatedCompetency.days = allocatedCompetency.days;
            newAllocatedCompetency.startDate = allocatedCompetency.startDate;
            newAllocatedCompetency.portfolioEntryPlanningPackage = allocatedCompetency.portfolioEntryPlanningPackage;
            newAllocatedCompetency.isConfirmed = allocatedCompetency.isConfirmed;
            newAllocatedCompetency.followPackageDates = allocatedCompetency.followPackageDates;
            newAllocatedCompetency.dailyRate = allocatedCompetency.dailyRate;
            newAllocatedCompetency.forecastDays = allocatedCompetency.forecastDays;
            newAllocatedCompetency.portfolioEntryResourcePlan = newResourcePlan;
            newAllocatedCompetency.save();
            newResourcePlan.portfolioEntryResourcePlanAllocatedCompetencies.add(newAllocatedCompetency);
            allocatedResourcesMapOldToNew.get(PortfolioEntryResourcePlanAllocatedCompetency.class.getName()).put(allocatedCompetency.id,
                    newAllocatedCompetency.id);
        }

        return newResourcePlan;
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
