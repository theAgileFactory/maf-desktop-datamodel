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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import com.avaje.ebean.Model;
import framework.services.api.commons.IApiObject;
import framework.utils.formats.DateType;
import models.pmo.OrgUnit;

/**
 * A life cycle milestone is associated with one or more life cycle milestone
 * approvers. These ones usually vote to approve a milestone instance. NB: this
 * list is the default list. For a named milestone instance it is possible to
 * modify the list of approvers (ex: holidays).
 * 
 * @author Johann Kohler
 */
@Entity
public class LifeCycleMilestoneInstanceApprover extends Model implements IModel, IApiObject {

    @Id
    public long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    public Boolean hasApproved;

    @Column(length = IModelConstants.XLARGE_STRING)
    public String comments;

    @DateType
    public Date approvalDate;

    @ManyToOne(cascade = CascadeType.ALL)
    public Actor actor;

    @ManyToOne(cascade = CascadeType.ALL)
    public OrgUnit orgUnit;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleMilestoneInstance lifeCycleMilestoneInstance;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [" + " hasApproved=" + hasApproved + ", comments=" + comments + ", approvalDate=" + approvalDate + " ]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
    }

    /**
     * Construct a life cycle milestone instance approver.
     * 
     * @param approver
     *            the approver (that is an actor)
     * @param lifeCycleMilestoneInstance
     *            the life cycle milestone instance
     */
    public LifeCycleMilestoneInstanceApprover(Actor approver, LifeCycleMilestoneInstance lifeCycleMilestoneInstance) {
        this.actor = approver;
        this.lifeCycleMilestoneInstance = lifeCycleMilestoneInstance;
    }

    /**
     * Construct a life cycle milestone instance org unit approver
     * @param approver
     * @param lifeCycleMilestoneInstance
     */
    public LifeCycleMilestoneInstanceApprover(OrgUnit approver, LifeCycleMilestoneInstance lifeCycleMilestoneInstance) {
        this.orgUnit = approver;
        this.lifeCycleMilestoneInstance = lifeCycleMilestoneInstance;
    }

    /**
     * Get the status.
     */
    public Status getStatus() {
        if (this.approvalDate != null) {
            if (this.hasApproved) {
                return Status.APPROVED;
            } else {
                return Status.REJECTED;
            }
        } else {
            if (lifeCycleMilestoneInstance.isPassed) {
                return Status.NOT_VOTED;
            } else {
                return Status.PENDING;
            }
        }
    }

    /**
     * Possible status.
     * 
     * @author Johann Kohler
     */
    public enum Status {
        PENDING, APPROVED, REJECTED, NOT_VOTED
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
