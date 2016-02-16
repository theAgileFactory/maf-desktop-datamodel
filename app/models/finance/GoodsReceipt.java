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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.avaje.ebean.Model;

import framework.services.api.commons.IApiObject;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;

/**
 * Good receipt associated with {@link PurchaseOrderLineItem}.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
public class GoodsReceipt extends Model implements IModel, IApiObject {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    /**
     * Reference in the source system.
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String refId;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PurchaseOrderLineItem purchaseOrderLineItem;

    @ManyToOne(cascade = CascadeType.ALL)
    public Currency currency;

    @Column(scale = 8, precision = 18)
    public BigDecimal currencyRate;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    public BigDecimal quantityReceived;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    public BigDecimal amountReceived;

    @Override
    public String audit() {
        return "GoodsReceipt [id=" + id + ", refId=" + refId + "]";
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
        String poNumber = purchaseOrderLineItem != null ? purchaseOrderLineItem.refId : "";
        return String.format("%s (%s)", refId, poNumber);
    }

    @Override
    public String getApiName() {
        return refId;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

}
