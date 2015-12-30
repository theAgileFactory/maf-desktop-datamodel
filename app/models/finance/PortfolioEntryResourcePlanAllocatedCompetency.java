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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.utils.CustomAttributeFormAndDisplayHandler;
import framework.utils.CustomAttributeFormAndDisplayHandler.CustomAttributeValueObject;
import framework.utils.Msg;
import framework.utils.Utilities;
import framework.utils.formats.DateType;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Competency;
import models.pmo.PortfolioEntryPlanningPackage;

/**
 * The portfolioEntry resource plan allocated competency defines the association
 * between a competency and a resource plan. This association contains the
 * number of days which is to be allocated.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryResourcePlanAllocatedCompetency extends Model implements IModel, IApiObject {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @JsonProperty
    @ApiModelProperty(required = true)
    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    public BigDecimal days;

    @DateType
    @JsonProperty
    public Date startDate;

    @DateType
    @JsonProperty
    public Date endDate;

    @JsonProperty
    public boolean isConfirmed = false;

    @JsonProperty
    public Boolean followPackageDates;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Competency competency;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntryResourcePlan portfolioEntryResourcePlan;

    @JsonProperty
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryPlanningPackage portfolioEntryPlanningPackage;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @ApiModelProperty(required = true)
    public BigDecimal dailyRate;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @ApiModelProperty(required = true)
    public BigDecimal forecastDays;

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

    /**
     * Get the date for a display.
     */
    public String getDisplayDate() {
        if (this.startDate != null && this.endDate != null) {
            return Msg.get("object.allocated_resource.date.period", Utilities.getDateFormat(null).format(this.startDate),
                    Utilities.getDateFormat(null).format(this.endDate));
        } else if (this.endDate != null) {
            return Utilities.getDateFormat(null).format(this.endDate);
        } else {
            return null;
        }
    }

    /* API methods */

    @Override
    public String getApiName() {
        return null;
    }

    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        return CustomAttributeFormAndDisplayHandler.getSerializableValues(PortfolioEntryResourcePlanAllocatedCompetency.class, id);
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

}
