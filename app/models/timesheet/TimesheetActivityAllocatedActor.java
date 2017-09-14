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
package models.timesheet;

import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonProperty;
import framework.utils.formats.DateType;
import models.common.ResourceAllocation;
import models.common.ResourceAllocationDetail;
import models.finance.PortfolioEntryResourcePlanAllocationStatusType;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Allocation of an actor to a TimesheetActivity.
 * 
 * @author Johann Kohler
 */
@Entity
public class TimesheetActivityAllocatedActor extends ResourceAllocation implements IModel {

    @Id
    public long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    public BigDecimal days;

    @DateType
    public Date startDate;

    @DateType
    public Date endDate;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Actor actor;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public TimesheetActivity timesheetActivity;

    @OneToMany(mappedBy = "timesheetActivityAllocatedActor")
    @Where(clause = "${ta}.deleted=0")
    @JsonProperty
    public List<TimesheetActivityAllocatedActorDetail> timesheetActivityAllocatedActorDetails;

    public boolean monthlyAllocated;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [" + ", days=" + days + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    @Override
    public PortfolioEntryResourcePlanAllocationStatusType getPortfolioEntryResourcePlanAllocationStatusType() {
        return null;
    }

    @Override
    public BigDecimal getDays() {
        return days;
    }

    @Override
    public BigDecimal getForecastDays() {
        return null;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    protected ResourceAllocationDetail createDetail(ResourceAllocation resourceAllocation, Integer year, Integer month, Double days) {
        return new TimesheetActivityAllocatedActorDetail(this, year, month, days);
    }

    @Override
    public List<? extends ResourceAllocationDetail> getDetails() {
        return timesheetActivityAllocatedActorDetails;
    }
}
