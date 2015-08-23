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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.utils.ISelectableValueHolder;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.data.validation.Constraints.Required;

/**
 * An portfolio entry planning package pattern is a pattern for a planning
 * package.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryPlanningPackagePattern extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Required
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING)
    public String description;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String cssClass;

    @JsonProperty
    public boolean isImportant = false;

    @Column(name = "`order`")
    @JsonProperty
    public Integer order;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryPlanningPackageGroup portfolioEntryPlanningPackageGroup;

    /**
     * Default constructor.
     */
    public PortfolioEntryPlanningPackagePattern() {
    }

    @Override
    public String audit() {
        return "PortfolioEntryPlanningPackagePattern [id=" + id + ", name=" + name + ", description=" + description + ", cssClass=" + cssClass + " ]";
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
        PortfolioEntryPlanningPackagePattern c = (PortfolioEntryPlanningPackagePattern) o;
        return this.order > c.order ? +1 : this.order < c.order ? -1 : 0;
    }

    @Override
    @JsonProperty(value = "description")
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Long getValue() {
        return this.id;
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
    public void setUrl(String arg0) {
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

}
