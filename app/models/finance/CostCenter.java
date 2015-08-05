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
import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;

/**
 * A cost center.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CostCenter extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = 6163020188344280692L;

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    /**
     * Reference in the source system.
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String refId;

    /**
     * Name of the cost center.
     */
    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public Actor owner;

    /**
     * The list of PO line items raised for this cost center.
     */
    @OneToMany(mappedBy = "costCenter")
    public List<PurchaseOrderLineItem> purchaseOrderLineItems;

    @Override
    public String audit() {
        return "CostCenter [id=" + id + ", name=" + name + "]";
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
        return String.format("%s (%s)", name, refId);
    }

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return Msg.get(name);
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
        return true;
    }

    @Override
    public void setUrl(String url) {
    }

    /* API methods */

    @Override
    @ApiModelProperty(required = true)
    @JsonProperty(value = "name")
    public String getApiName() {
        return getName();
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

}
