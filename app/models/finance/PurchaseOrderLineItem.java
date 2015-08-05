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
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;

import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.utils.formats.DateType;

/**
 * A purchase order.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseOrderLineItem extends Model implements IModel, IApiObject {

    private static final long serialVersionUID = 3700694627486850139L;

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Boolean isCancelled;

    /**
     * Reference in the source system.
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String refId;

    @Column(length = IModelConstants.VLARGE_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String description;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink(linkField = "code")
    @ApiModelProperty(dataType = "String", required = true)
    public Currency currency;

    /**
     * The line number.
     */
    @Required
    @JsonProperty
    public Integer lineId;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public Supplier supplier;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal quantity;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal quantityTotalReceived;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal quantityBilled;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    @ApiModelProperty(required = true)
    public BigDecimal amount;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal amountReceived;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal amountBilled;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal unitPrice;

    @Column(length = IModelConstants.SMALL_STRING)
    @JsonProperty
    public String materialCode;

    @Column(length = IModelConstants.SMALL_STRING)
    @JsonProperty
    public String glAccount;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Boolean isOpex;

    @DateType
    @JsonProperty
    @ApiModelProperty(required = true)
    public Date creationDate;

    @DateType
    @JsonProperty
    public Date dueDate;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PurchaseOrderLineShipmentStatusType shipmentType;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public Actor requester;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public CostCenter costCenter;

    @OneToMany(mappedBy = "purchaseOrderLineItem")
    @Where(clause = "${ta}.deleted=0")
    public List<GoodsReceipt> goodsReceipts;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PurchaseOrder purchaseOrder;

    @OneToMany(mappedBy = "purchaseOrderLineItem")
    @Where(clause = "${ta}.deleted=0")
    public List<WorkOrder> workOrders;

    @Override
    public String toString() {
        return refId;
    }

    @Override
    public String audit() {
        return "PurchaseOrderLineItem [id=" + id + ", refId=" + refId + ", description=" + description + ", lineId=" + lineId + ", quantity=" + quantity
                + ", quantityTotalReceived=" + quantityTotalReceived + ", quantityBilled=" + quantityBilled + ", amount=" + amount + ", amountReceived="
                + amountReceived + ", amountBilled=" + amountBilled + ", unitPrice=" + unitPrice + ", materialCode=" + materialCode + ", currency="
                + currency + ", glAccount=" + glAccount + ", isOpex=" + isOpex + ", creationDate=" + creationDate + ", dueDate=" + dueDate + "]";
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
     * Get the remaining amount of the line item by ignoring a work order
     * amount.
     * 
     * if the line item is associated and shared: if the line item is associated
     * to the work order then do not subtract its amount, else return the normal
     * remaining amount
     * 
     * else: return the normal remaining amount
     * 
     * @param workOrder
     *            the work order to ignore
     */
    public Double getRemainingAmount(WorkOrder workOrder) {

        Double remainingAmount = this.getRemainingAmount();

        if (isAssociated() && isShared()) {
            if (workOrder.purchaseOrderLineItem != null && workOrder.purchaseOrderLineItem.id.equals(this.id)) {
                remainingAmount += workOrder.amount.doubleValue();
            }
        }

        return remainingAmount;

    }

    /**
     * Get the remaining amount of the line item.
     * 
     * if the line item is not associated: return the line item amount
     * 
     * if the line item is associated and not shared: return 0
     * 
     * if the line item is associated and shared: return the line item amount
     * minus the sum of work order amounts
     */
    @JsonProperty(value = "remainingAmount")
    public Double getRemainingAmount() {

        Double remainingAmount = this.amount.doubleValue();

        if (isAssociated()) {
            if (isShared()) {
                for (WorkOrder wo : this.workOrders) {
                    remainingAmount -= wo.amount.doubleValue();
                }
            } else {
                remainingAmount = 0.0;
            }
        }

        return remainingAmount;
    }

    /**
     * Define if a line item is associated with at least one work order.
     */
    public boolean isAssociated() {
        return workOrders != null && !workOrders.isEmpty();
    }

    /**
     * Define if a line item is shared.
     * 
     * if the line item is not associated: return null
     * 
     * if the line item is associated with one or many shared work orders:
     * return true
     * 
     * if the line item is associated with any shared work order: return false
     */
    public Boolean isShared() {
        Boolean shared = null;
        if (isAssociated()) {
            shared = workOrders.get(0).shared;
        }
        return shared;
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
