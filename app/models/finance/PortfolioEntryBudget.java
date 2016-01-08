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
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Where;

import framework.services.api.commons.IApiObject;
import framework.utils.formats.DateType;
import models.framework_models.parent.IModel;
import models.governance.LifeCycleInstancePlanning;
import models.governance.LifeCycleMilestoneInstance;

/**
 * The portfolioEntry budget defines how much money is allocated to the
 * portfolioEntry.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
public class PortfolioEntryBudget extends Model implements IModel, IApiObject {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @DateType
    public Date allocationDate;

    @OneToMany(mappedBy = "portfolioEntryBudget")
    public List<LifeCycleInstancePlanning> lifeCycleInstancePlannings;

    @OneToMany(mappedBy = "portfolioEntryBudget")
    public List<LifeCycleMilestoneInstance> lifeCycleMilestoneInstances;

    @OneToMany(mappedBy = "portfolioEntryBudget")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryBudgetLine> portfolioEntryBudgetLines;

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
     * Clone a portfolio entry budget (with its lines) in the DB and return the
     * clone.
     */
    public PortfolioEntryBudget cloneInDB(Map<String, Map<Long, Long>> allocatedResourcesMapOldToNew) {
        PortfolioEntryBudget newBudget = new PortfolioEntryBudget();
        newBudget.allocationDate = this.allocationDate;
        newBudget.save();
        for (PortfolioEntryBudgetLine budgetLine : this.portfolioEntryBudgetLines) {
            PortfolioEntryBudgetLine newBudgetLine = new PortfolioEntryBudgetLine();
            newBudgetLine.amount = budgetLine.amount;
            newBudgetLine.budgetBucket = budgetLine.budgetBucket;
            newBudgetLine.currency = budgetLine.currency;
            newBudgetLine.glAccount = budgetLine.glAccount;
            newBudgetLine.isOpex = budgetLine.isOpex;
            newBudgetLine.name = budgetLine.name;
            newBudgetLine.refId = budgetLine.refId;
            newBudgetLine.portfolioEntryBudget = newBudget;
            if (budgetLine.resourceObjectType != null) {
                newBudgetLine.resourceObjectId = allocatedResourcesMapOldToNew.get(budgetLine.resourceObjectType).get(budgetLine.resourceObjectId);
                newBudgetLine.resourceObjectType = budgetLine.resourceObjectType;
            }
            newBudgetLine.save();
            newBudget.portfolioEntryBudgetLines.add(newBudgetLine);
        }
        return newBudget;
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
