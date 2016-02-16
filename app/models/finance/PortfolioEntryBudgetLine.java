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
import framework.services.custom_attribute.ICustomAttributeManagerService;
import framework.services.custom_attribute.ICustomAttributeManagerService.CustomAttributeValueObject;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.Play;

/**
 * The portfolioEntry budget line item is a line within the portfolioEntry
 * budget. This line defines a specific “sub part” of the budget (possibly
 * associated with a defined GL account).
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryBudgetLine extends Model implements IModel, IApiObject {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @ApiModelProperty(required = true)
    @JsonProperty
    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @JsonProperty
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String refId;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Boolean isOpex;

    @JsonProperty
    @ApiModelProperty(required = true)
    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    public BigDecimal amount;

    @JsonPropertyLink(linkField = "code")
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL)
    public Currency currency;

    @Column(scale = 8, precision = 18)
    @JsonProperty
    public BigDecimal currencyRate;

    @JsonProperty
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String glAccount;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntryBudget portfolioEntryBudget;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public BudgetBucket budgetBucket;

    @Column(length = IModelConstants.LARGE_STRING)
    public String resourceObjectType;

    public Long resourceObjectId;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", isApproved=" + name + ", refId=" + refId + ", isOpex=" + isOpex + ", amount=" + amount
                + ", glAccount=" + glAccount + ", currency=" + currency + "]";
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
    @ApiModelProperty(required = true)
    public String getApiName() {
        return name;
    }

    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        ICustomAttributeManagerService customAttributeManagerService = Play.application().injector().instanceOf(ICustomAttributeManagerService.class);
        return customAttributeManagerService.getSerializableValues(PortfolioEntryBudgetLine.class, id);
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

}
