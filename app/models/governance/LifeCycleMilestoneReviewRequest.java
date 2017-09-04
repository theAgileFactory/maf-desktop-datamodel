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
import framework.services.api.commons.IApiObject;
import framework.utils.Utilities;
import framework.utils.formats.DateType;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import models.pmo.PortfolioEntry;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Makes the link between a portfolio entry, a life cycle milestone and a pending process transition request
 * 
 * @author Guillaume Petit
 */
@Entity
public class LifeCycleMilestoneReviewRequest extends Model implements IModel {

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntry portfolioEntry;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleMilestone lifeCycleMilestone;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public ProcessTransitionRequest processTransitionRequest;

    @DateType
    public Date approvalDate;

    /**
     * Default constructor.
     */
    public LifeCycleMilestoneReviewRequest() {
    }

    public LifeCycleMilestoneReviewRequest(PortfolioEntry portfolioEntry, LifeCycleMilestone lifeCycleMilestone, ProcessTransitionRequest processTransitionRequest, Date approvalDate) {
        this.portfolioEntry = portfolioEntry;
        this.lifeCycleMilestone = lifeCycleMilestone;
        this.processTransitionRequest = processTransitionRequest;
        this.approvalDate = approvalDate;
    }

    @Override
    public String audit() {
        return "LifeCycleMilestoneReviewRequest [portfolioEntry=" + portfolioEntry.id + ", lifeCycleMilestone=" +
                lifeCycleMilestone.id + ", processTransitionRequest=" + processTransitionRequest.id + ", approvalDate=" + approvalDate + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        this.delete();
    }
}
