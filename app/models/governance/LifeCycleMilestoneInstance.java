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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import models.finance.PortfolioEntryBudget;
import models.finance.PortfolioEntryResourcePlan;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import models.pmo.PortfolioEntry;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.Where;

import framework.services.api.commons.IApiObject;
import framework.utils.formats.DateType;

/**
 * A life cycle milestone instance is simply an instance of a milestone used for
 * a portfolio entry.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
public class LifeCycleMilestoneInstance extends Model implements IModel, IApiObject {

    private static final long serialVersionUID = -2940989268480068464L;

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    public Boolean hasAttachments;

    public Boolean isPassed;

    @DateType
    public Date passedDate;

    @Column(length = IModelConstants.XLARGE_STRING)
    public String gateComments;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleMilestone lifeCycleMilestone;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleInstance lifeCycleInstance;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleMilestoneInstanceStatusType lifeCycleMilestoneInstanceStatusType;

    @OneToMany(mappedBy = "lifeCycleMilestoneInstance")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCycleMilestoneInstanceApprover> lifeCycleMilestoneInstanceApprovers;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryBudget portfolioEntryBudget;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryResourcePlan portfolioEntryResourcePlan;

    @OneToOne(mappedBy = "lastApprovedLifeCycleMilestoneInstance")
    public PortfolioEntry portfolioEntryWithCurrentInstanceAsLastApproved;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public Actor approver;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", gateComments=" + gateComments + "]";
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
     * Get the status of the milestone instance.
     */
    public Status getStatus() {
        if (this.isPassed) {
            if (this.lifeCycleMilestoneInstanceStatusType != null) {
                if (this.lifeCycleMilestoneInstanceStatusType.isApproved) {
                    return Status.APPROVED;
                } else {
                    return Status.REJECTED;
                }
            } else {
                return Status.UNKNOWN;
            }
        } else {
            return Status.PENDING;
        }
    }

    /**
     * Get the planning.
     */
    public LifeCycleInstancePlanning getPlanning() {
        if (this.portfolioEntryResourcePlan != null) {
            return this.portfolioEntryResourcePlan.lifeCycleInstancePlannings.get(0);
        }
        return null;
    }

    /**
     * Possible status.
     * 
     * @author Johann Kohler
     */
    public enum Status {
        APPROVED, REJECTED, UNKNOWN, PENDING
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
