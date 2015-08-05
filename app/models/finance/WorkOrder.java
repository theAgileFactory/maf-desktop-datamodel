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

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.PortfolioEntry;
import models.pmo.PortfolioEntryPlanningPackage;
import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.utils.CustomAttributeApiHandler;
import framework.utils.CustomAttributeApiHandler.CustomAttributeApiValue;
import framework.utils.formats.DateType;

/**
 * A work order is a unit of work associated with a cost.<br/>
 * This one can be associated with a PO or not (in such case this is a
 * forecast).<br/>
 * This one can be exclusively associated with a PO (in such case no other work
 * orders can) or multiple work orders can share the same PO.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkOrder extends Model implements IModel, IApiObject {

    private static final long serialVersionUID = 8276881244927141735L;

    @Id
    @ApiModelProperty(required = true)
    @JsonProperty
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = true)
    @ApiModelProperty(required = true)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    @JsonProperty
    public String description;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @ApiModelProperty(required = true)
    public BigDecimal amount;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    public BigDecimal amountReceived;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Boolean isOpex;

    public boolean isEngaged;

    @DateType
    @JsonProperty
    @ApiModelProperty(required = true)
    public Date creationDate;

    @DateType
    @JsonProperty
    public Date dueDate;

    @DateType
    @JsonProperty
    public Date startDate;

    @JsonProperty
    public Boolean followPackageDates;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink(linkField = "code")
    @ApiModelProperty(dataType = "String", required = true)
    public Currency currency;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Boolean shared;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntry portfolioEntry;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PurchaseOrderLineItem purchaseOrderLineItem;

    @JsonProperty
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryPlanningPackage portfolioEntryPlanningPackage;

    /**
     * Default constructor.
     */
    public WorkOrder() {
    }

    @Override
    public String audit() {
        return "WorkOrder [id=" + id + ", name=" + name + ", description=" + description + ", amount=" + amount + ", amountReceived=" + amountReceived
                + ", isOpex=" + isOpex + ", creationDate=" + creationDate + ", dueDate=" + dueDate + ", currency=" + currency + ", shared=" + shared + "]";
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
    public String toString() {
        return name;
    }

    /**
     * Get the computed "isEngaged".
     * 
     * if purchase orders are enabled: a work order is engaged if it's
     * associated to a not cancelled line item
     * 
     * else: the flag isEngaged is used
     * 
     * @param usePurchaseOrder
     *            true if the purchase order are enabled
     */
    public boolean getComputedIsEngaged(boolean usePurchaseOrder) {
        if (!usePurchaseOrder) {
            return isEngaged;
        } else {
            return purchaseOrderLineItem != null && purchaseOrderLineItem.isCancelled == false ? true : false;
        }
    }

    /**
     * Get the computed "amount".
     * 
     * if purchase orders are enabled: if the work order is engaged and not
     * shared then return the amount of the line item, else return the work
     * order amount
     * 
     * else: the work order amount is used
     * 
     * @param usePurchaseOrder
     *            true if the purchase order are enabled
     */
    public BigDecimal getComputedAmount(boolean usePurchaseOrder) {

        if (!usePurchaseOrder) {
            return amount;
        } else {
            if (getComputedIsEngaged(usePurchaseOrder) && !shared) {
                return purchaseOrderLineItem.amount;
            } else {
                return amount;
            }
        }

    }

    /**
     * Get the computed "amountReceived".
     * 
     * if the work order is not engaged: return null
     * 
     * if the work order is engaged and purchase orders are enabled: if the work
     * order is not shared then return the received amount of the line item,
     * else return the work order received amount
     * 
     * if the work order is engaged and purchase orders are not enabled: the
     * work order received amount is used
     * 
     * @param usePurchaseOrder
     *            true if the purchase order are enabled
     */
    public BigDecimal getComputedAmountReceived(boolean usePurchaseOrder) {
        if (getComputedIsEngaged(usePurchaseOrder)) {
            if (!usePurchaseOrder) {
                return amountReceived;
            } else {
                if (shared) {
                    return amountReceived;
                } else {
                    return purchaseOrderLineItem.amountReceived;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Get the open amount.
     * 
     * if the work order is not engaged: return null
     * 
     * if the work order is engaged and the amount/received amount are defined
     * then return the difference
     * 
     * @param usePurchaseOrder
     *            true if the purchase order are enabled
     */
    public BigDecimal getAmountOpen(boolean usePurchaseOrder) {

        if (getComputedIsEngaged(usePurchaseOrder)) {

            BigDecimal a = getComputedAmount(usePurchaseOrder);
            BigDecimal ar = getComputedAmountReceived(usePurchaseOrder);

            if (a != null && ar != null) {

                BigDecimal ao = a.subtract(ar);
                return ao;

            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /* API methods */

    @Override
    @JsonProperty(value = "name")
    @ApiModelProperty(required = true)
    public String getApiName() {
        return name;
    }

    /**
     * 
     * @return List of customAttributes
     */
    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeApiValue> getCustomAttributesAsSerializableValues() {
        return CustomAttributeApiHandler.getSerializableValues(WorkOrder.class, id);
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
