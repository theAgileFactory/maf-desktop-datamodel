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
package models.pmo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.finance.PortfolioEntryResourcePlanAllocatedOrgUnit;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.utils.CustomAttributeApiHandler;
import framework.utils.CustomAttributeApiHandler.CustomAttributeApiValue;
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;

/**
 * An organizational unit of the company.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUnit extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = 4886738624472325573L;

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = true)
    @JsonProperty
    public String refId;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean canSponsor;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean canDeliver;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @ApiModelProperty(dataType = "String")
    @JsonPropertyLink
    public OrgUnitType orgUnitType;

    /**
     * An org-unit which contains or is responsible for the current org-unit.
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public OrgUnit parent;

    /**
     * Some org-units which depends on the current org-unit.
     */
    @OneToMany(mappedBy = "parent")
    @Where(clause = "${ta}.deleted=0")
    public List<OrgUnit> childs;

    /**
     * The list of portfolioEntries that this org-unit is sponsoring.
     */
    @OneToMany(mappedBy = "sponsoringUnit")
    public List<PortfolioEntry> sponsoredActivities;

    @OneToMany(mappedBy = "orgUnit")
    @Where(clause = "${ta}.deleted=0")
    public List<Actor> actors;

    @OneToMany(mappedBy = "orgUnit")
    public List<PortfolioEntryResourcePlanAllocatedOrgUnit> portfolioEntryResourcePlanAllocatedOrgUnits;

    /**
     * The list of portfolioEntries that this org-unit is delivering.
     */
    @ManyToMany(mappedBy = "deliveryUnits")
    public List<PortfolioEntry> deliveredActivities;

    /**
     * The manager of the organizational unit (the boss).
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public Actor manager;

    /**
     * Default constructor.
     */
    public OrgUnit() {
        super();
    }

    @Override
    public String audit() {
        return "OrgUnit [id=" + id + ", name=" + name + "]";
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
    public String getDescription() {
        return null;
    }

    @Override
    @JsonProperty(value = "name")
    public String getName() {
        return Msg.get(name);
    }

    /**
     * Get the key of the name.
     */
    public String getNameKey() {
        return name;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void setUrl(String url) {
    }

    @Override
    public Long getValue() {
        return id;
    }

    @Override
    public boolean isSelectable() {
        return isActive;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

    /** Api methods. **/

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    @Override
    @JsonProperty(value = "name")
    @ApiModelProperty(required = true)
    public String getApiName() {
        return getName();
    }

    /**
     * Get the custom attributes.
     */
    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeApiValue> getCustomAttributesAsSerializableValues() {
        return CustomAttributeApiHandler.getSerializableValues(OrgUnit.class, id);
    }
}