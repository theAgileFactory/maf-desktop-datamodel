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
package models.delivery;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import com.avaje.ebean.Model;

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
import framework.utils.formats.DateType;

/**
 * Define a release.
 * 
 * @author Johann Kohler
 */
@Entity
@Table(name = "`release`")
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Release extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = 3691571315678L;

    @Id
    @JsonProperty
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isActive = true;

    @Column(length = IModelConstants.LARGE_STRING)
    public String name;

    public String description;

    @JsonProperty
    public Integer capacity;

    @DateType
    @JsonProperty
    public Date cutOffDate;

    @DateType
    @JsonProperty
    public Date endTestsDate;

    @DateType
    @JsonProperty
    @ApiModelProperty(required = true)
    public Date deploymentDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "release")
    @Where(clause = "${ta}.deleted=0")
    public List<Requirement> requirements;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    public Actor manager;

    @OneToMany(mappedBy = "release")
    public List<ReleasePortfolioEntry> releasesPortfolioEntries;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "release")
    @Where(clause = "${ta}.deleted=0")
    public List<Iteration> iterations;

    /**
     * Default constructor.
     */
    public Release() {
    }

    /**
     * Get the translated description.
     */
    @JsonProperty(value = "description")
    public String getDescription() {
        return Msg.get(this.description);
    }

    /**
     * Get the translated name.
     */
    public String getName() {
        return Msg.get(this.name);
    }

    @Override
    public String audit() {
        return "Release [id=" + id + ", name=" + name + ", description=" + description + " ]";
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
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
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
    public void setUrl(String url) {
    }

    /** Api methods **/
    @Override
    @JsonProperty(value = "name")
    @ApiModelProperty(required = true)
    public String getApiName() {
        return getName();
    }

    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeApiValue> getCustomAttributesAsSerializableValues() {
        return CustomAttributeApiHandler.getSerializableValues(Release.class, id);
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
