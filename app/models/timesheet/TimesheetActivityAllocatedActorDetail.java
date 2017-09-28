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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import framework.services.api.commons.IApiObject;
import models.common.ResourceAllocationDetail;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimesheetActivityAllocatedActorDetail extends ResourceAllocationDetail implements IApiObject {

    @Id
    @JsonProperty
    public long id;

    @ManyToOne(cascade = CascadeType.ALL)
    public TimesheetActivityAllocatedActor timesheetActivityAllocatedActor;

    @JsonProperty
    public Integer year;

    @JsonProperty
    public Integer month;

    @JsonProperty
    public Double days;

    @Version
    public Timestamp lastUpdate;

    public boolean deleted = false;

    public TimesheetActivityAllocatedActorDetail(TimesheetActivityAllocatedActor timesheetActivityAllocatedActor, Integer year, Integer month, Double days) {
        this.timesheetActivityAllocatedActor = timesheetActivityAllocatedActor;
        this.year = year;
        this.month = month;
        this.days = days;
    }

    @Override
    public String getApiName() {
        return null;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    @Override
    public String audit() {
        return this.getClass().getSimpleName();
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
    public Integer getYear() {
        return year;
    }

    @Override
    public Integer getMonth() {
        return month;
    }

    @Override
    public void setDays(Double days) {
        this.days = days;
    }

    @Override
    public Double getDays() {
        return days;
    }
}
