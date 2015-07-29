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
package models.governance;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

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
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;

/**
 * A portfolio entry is implemented within a defined life cycle process. One
 * company can define multiple processes (one for IT projects and one for
 * business projects for instance).
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LifeCycleProcess extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = -6479934931442743920L;

    @Id
    @ApiModelProperty(required = true)
    @JsonProperty
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.SMALL_STRING)
    public String shortName;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING)
    public String description;

    @JsonProperty
    public Boolean isActive;

    @OneToMany(mappedBy = "lifeCycleProcess")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("order")
    public List<LifeCycleMilestone> lifeCycleMilestones;

    @OneToMany(mappedBy = "lifeCycleProcess")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCycleInstance> lifeCycleInstances;

    @OneToMany(mappedBy = "lifeCycleProcess")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("order")
    public List<LifeCyclePhase> lifeCyclePhases;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", shortName=" + shortName + ", name=" + name + ", description=" + description
                + ", isActive=" + isActive + "]";
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
    @JsonProperty(value = "description")
    public String getDescription() {
        return Msg.get(description);
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
    public void setUrl(String url) {
    }

    @Override
    public Long getValue() {
        return this.id;
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

    /**
     * Get the short name.
     */
    @JsonProperty(value = "shortName")
    @ApiModelProperty(required = true)
    public String getShortName() {
        return Msg.get(shortName);
    }

    /* API Methods */

    @Override
    @JsonProperty(value = "name")
    @ApiModelProperty(required = true)
    public String getApiName() {
        return getName();
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
