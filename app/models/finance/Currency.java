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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.utils.ISelectableValueHolder;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;

/**
 * A currency is used in all entity that manage amount.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency extends Model implements IModel, IApiObject, ISelectableValueHolder<String> {

    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isActive = true;

    @Id
    @Column(length = IModelConstants.CURRENCY_CODE)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String code;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isDefault = false;

    @Column(scale = 8, precision = 18)
    @JsonProperty
    public BigDecimal conversionRate;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String symbol;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<BudgetBucketLine> budgetBucketLines;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryBudgetLine> portfolioEntryBudgetLines;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<PurchaseOrderLineItem> purchaseOrderLineItems;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<WorkOrder> workOrders;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<GoodsReceipt> goodsReceipt;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryResourcePlanAllocatedActor> portfolioEntryResourcePlanAllocatedActors;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryResourcePlanAllocatedOrgUnit> portfolioEntryResourcePlanAllocatedOrgUnits;

    @OneToMany(mappedBy = "currency")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryResourcePlanAllocatedCompetency> portfolioEntryResourcePlanAllocatedCompetencies;

    /**
     * Default constructor.
     */
    public Currency() {
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", isActive=" + isActive + ", code=" + code + ", isDefault=" + isDefault + ", conversionRate="
                + conversionRate + ", symbol=" + symbol + "]";
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
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<String> v = (ISelectableValueHolder<String>) o;
        return this.getName().compareTo(v.getName());
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return code;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getValue() {
        return code;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean isSelectable() {
        return isActive;
    }

    @Override
    public void setUrl(String url) {
    }

    /* API methods */

    @Override
    public String getApiName() {
        return null;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
