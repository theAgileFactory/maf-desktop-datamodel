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
import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;

/**
 * A supplier.
 * 
 * @author Pierre-Yves Cloux
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier extends Model implements IModel, IApiObject {

    public boolean deleted = false;
    @Version
    public Timestamp lastUpdate;

    @Id
    @ApiModelProperty(required = true)
    @JsonProperty
    public Long id;

    /**
     * Reference in the source system.
     */
    @JsonProperty
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String refId;

    /**
     * Name of the supplier.
     */
    @Column(length = IModelConstants.LARGE_STRING, nullable = false)
    public String name;

    /**
     * The purchase orders raised for this supplier.
     */
    @OneToMany(mappedBy = "supplier")
    public List<PurchaseOrderLineItem> purchaseOrderLineItems;

    @Override
    public String audit() {
        return "Supplier [id=" + id + ", refId=" + refId + ", name=" + name + "]";
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

    /* API methods */

    @Override
    @ApiModelProperty(required = true)
    @JsonProperty(value = "name")
    public String getApiName() {
        return name;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
