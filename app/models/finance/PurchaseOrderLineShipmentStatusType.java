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

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import framework.services.api.commons.IApiObject;
import framework.utils.ISelectableValueHolder;

/**
 * The shipment status for a {@link PurchaseOrderLineItem}.<br/>
 * For instance this could be:
 * <ul>
 * <li>OPEN</li>
 * <li>CLOSED</li>
 * <li>CLOSED FOR RECEIVING</li>
 * <li>CLOSED FOR INVOICE</li>
 * <li>FINALLY CLOSED</li>
 * </ul>
 * 
 * @author Pierre-Yves Cloux
 */
@Entity
public class PurchaseOrderLineShipmentStatusType extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {
    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    public Boolean selectable;

    @Id
    public Long id;

    /**
     * Reference in the source system.
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String refId;

    /**
     * Name of the supplier.
     */
    @Column(length = IModelConstants.SMALL_STRING, nullable = false)
    public String name;

    /**
     * Name of the supplier.
     */
    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    public String description;

    @Required
    public Boolean isAmountExpanded;

    /**
     * Returns the list of PurchaseOrderLineItem which are of the current type.
     */
    @OneToMany(mappedBy = "shipmentType")
    public List<PurchaseOrderLineItem> sameTypePurchaseOrderLineItems;

    @Override
    public String audit() {
        return "PurchaseOrderLineShipmentStatusType [id=" + id + ", name=" + name + ", isAmountExpanded=" + isAmountExpanded + "]";
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

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

    @Override
    @JsonProperty(value = "description")
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Long getValue() {
        return id;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public void setUrl(String arg0) {
    }

    /* API methods */

    @Override
    public String getApiName() {
        return getName();
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
