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

import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;
import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.services.custom_attribute.ICustomAttributeManagerService;
import framework.services.custom_attribute.ICustomAttributeManagerService.CustomAttributeValueObject;
import framework.utils.formats.DateType;
import models.common.ResourceAllocation;
import models.common.ResourceAllocationDetail;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import models.pmo.PortfolioEntryPlanningPackage;
import play.Play;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The portfolioEntry resource plan allocated actor defines the association
 * between an actor and a resource plan. This association contains the number of
 * days which is to be allocated.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryResourcePlanAllocatedActor extends ResourceAllocation implements IModel, IApiObject {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @DateType
    @JsonProperty
    public Date startDate;

    @DateType
    @JsonProperty
    public Date endDate;

    @JsonPropertyLink
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntryResourcePlanAllocationStatusType portfolioEntryResourcePlanAllocationStatusType;

    @JsonPropertyLink
    @ManyToOne(cascade = CascadeType.ALL)
    public Actor lastStatusTypeUpdateActor;

    @JsonProperty
    @DateType
    public Date lastStatusTypeUpdateTime;

    @JsonProperty
    public Boolean followPackageDates;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Actor actor;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntryResourcePlan portfolioEntryResourcePlan;

    @JsonProperty
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryPlanningPackage portfolioEntryPlanningPackage;

    @JsonPropertyLink(linkField = "code")
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL)
    public Currency currency;

    @Column(scale = 8, precision = 18)
    @JsonProperty
    public BigDecimal currencyRate;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    @ApiModelProperty(required = true)
    public BigDecimal days;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    @ApiModelProperty(required = true)
    public BigDecimal dailyRate;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal forecastDays;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal forecastDailyRate;

    @OneToMany(mappedBy = "portfolioEntryResourcePlanAllocatedActor")
    @Where(clause = "${ta}.deleted=0")
    @JsonProperty
    public List<PortfolioEntryResourcePlanAllocatedActorDetail> portfolioEntryResourcePlanAllocatedActorDetails;

    public boolean monthlyAllocated = false;

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

    /* API methods */

    @Override
    public String getApiName() {
        return null;
    }

    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        ICustomAttributeManagerService customAttributeManagerService = Play.application().injector().instanceOf(ICustomAttributeManagerService.class);
        return customAttributeManagerService.getSerializableValues(PortfolioEntryResourcePlanAllocatedActor.class, id);
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    public BigDecimal getDetailsAsTotalDays() {
        return BigDecimal.valueOf(this.portfolioEntryResourcePlanAllocatedActorDetails.stream()
                .mapToDouble(detail -> detail.days)
                .sum());
    }

    @Override
    public PortfolioEntryResourcePlanAllocationStatusType getPortfolioEntryResourcePlanAllocationStatusType() {
        return portfolioEntryResourcePlanAllocationStatusType;
    }

    @Override
    public BigDecimal getDays() {
        return days;
    }

    @Override
    public BigDecimal getForecastDays() {
        return forecastDays;
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
        return new PortfolioEntryResourcePlanAllocatedActorDetail(this, year, month, days);
    }

    @Override
    public List<? extends ResourceAllocationDetail> getDetails() {
        return portfolioEntryResourcePlanAllocatedActorDetails;
    }
}
