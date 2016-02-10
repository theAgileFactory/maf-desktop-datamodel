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

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.services.kpi.IKpiObjectsContainer;
import framework.services.custom_attribute.ICustomAttributeManagerService;
import framework.services.custom_attribute.ICustomAttributeManagerService.CustomAttributeValueObject;
import framework.utils.ISelectableValueHolder;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.Play;

/**
 * An object which defines a group of {@link PortfolioEntry} mainly for
 * reporting purpose. Such group is not managed as a real project.<br/>
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Portfolio extends Model implements IModel, IApiObject, IKpiObjectsContainer, ISelectableValueHolder<Long> {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isActive;

    /**
     * Reference in the source system.
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String refId;

    /**
     * Name of the portfolioEntry group.
     */
    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    /**
     * The manager of the portfolioEntry.<br/>
     * The one that is on a daily basis managing the work progress.
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    public Actor manager;

    /**
     * The portfolioEntries which are part of this group.
     */
    @ManyToMany(mappedBy = "portfolios")
    public List<PortfolioEntry> portfolioEntries;

    /**
     * The list of stakeholders for this portfolioEntry group.
     */
    @OneToMany(mappedBy = "portfolio")
    public List<Stakeholder> stakeholders;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    public PortfolioType portfolioType;

    @Override
    public String audit() {
        return "Portfolio [id=" + id + ", refId=" + refId + ", name=" + name + ", portfolioType=" + portfolioType + "]";
    }

    @Override
    public void defaults() {
        isActive = true;
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
        return isActive;
    }

    @Override
    public void setUrl(String arg0) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

    @Override
    public List<? extends IKpiObjectsContainer> getAllInstancesForKpi() {
        return Ebean.find(Portfolio.class).where().eq("deleted", false).eq("isActive", true).findList();
    }

    @Override
    public Long getIdForKpi() {
        return this.id;
    }

    @Override
    public Object getObjectByIdForKpi(Long objectId) {
        return Ebean.find(Portfolio.class).where().eq("deleted", false).eq("id", objectId).findUnique();
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
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        ICustomAttributeManagerService customAttributeManagerService = Play.application().injector().instanceOf(ICustomAttributeManagerService.class);
        return customAttributeManagerService.getSerializableValues(Portfolio.class, id);
    }

}
