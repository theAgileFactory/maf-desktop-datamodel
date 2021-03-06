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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.finance.PortfolioEntryResourcePlanAllocatedCompetency;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;

/**
 * A competency (reference data).
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Competency extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @ApiModelProperty(required = true)
    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    public String description;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Boolean isActive;

    @OneToMany(mappedBy = "competency")
    public List<PortfolioEntryResourcePlanAllocatedCompetency> portfolioEntryResourcePlanAllocatedCompetencies;

    @ManyToMany(mappedBy = "competencies")
    public List<Actor> actors;

    @OneToMany(mappedBy = "defaultCompetency")
    public List<Actor> actorsWithDefault;

    @Override
    public String audit() {
        return "Competency [id=" + id + ", name=" + name + "]";
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
    @JsonProperty(value = "description")
    public String getDescription() {
        return Msg.get(this.description);
    }

    @Override
    @JsonProperty(value = "name")
    public String getName() {
        return Msg.get(this.name);
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

    /** Api methods. **/

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    @Override
    @ApiModelProperty(required = true)
    public String getApiName() {
        return getName();
    }

}
