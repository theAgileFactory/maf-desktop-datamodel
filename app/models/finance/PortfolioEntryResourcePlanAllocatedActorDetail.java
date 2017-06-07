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

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import framework.services.api.commons.IApiObject;
import models.framework_models.parent.IModel;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The portfolio entry resource plan allocated actor detail defines the monthly
 * allocation for an actor and a resource plan.
 *
 * @author Guillaume Petit
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryResourcePlanAllocatedActorDetail extends Model implements IModel, IApiObject {

    @Id
    @JsonProperty
    public long id;

    @ManyToOne(cascade = CascadeType.ALL)
    public PortfolioEntryResourcePlanAllocatedActor portfolioEntryResourcePlanAllocatedActor;

    @JsonProperty
    public Integer year;

    @JsonProperty
    public Integer month;

    @JsonProperty
    public Double days;

    @Version
    public Timestamp lastUpdate;

    public boolean deleted = false;

    public PortfolioEntryResourcePlanAllocatedActorDetail(PortfolioEntryResourcePlanAllocatedActor portfolioEntryResourcePlanAllocatedActor, Integer year, Integer month, Double days) {
        this.portfolioEntryResourcePlanAllocatedActor = portfolioEntryResourcePlanAllocatedActor;
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
}
