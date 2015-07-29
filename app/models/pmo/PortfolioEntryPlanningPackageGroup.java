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
 * Define a group of portfolio entry planning package.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryPlanningPackageGroup extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = 3964921668212418223L;

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @ApiModelProperty(required = true)
    public String name;

    @Column(length = IModelConstants.XLARGE_STRING)
    public String description;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntryPlanningPackageGroup")
    @Where(clause = "${ta}.deleted=0")
    @OrderBy("order")
    public List<PortfolioEntryPlanningPackagePattern> portfolioEntryPlanningPackagePatterns;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntryPlanningPackageGroup")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryPlanningPackage> portfolioEntryPlanningPackages;

    /**
     * Default constructor.
     */
    public PortfolioEntryPlanningPackageGroup() {
    }

    /**
     * Get the description.
     */
    @JsonProperty(value = "description")
    public String getDescription() {
        return Msg.get(this.description);
    }

    /**
     * Get the name.
     */
    public String getName() {
        return Msg.get(this.name);
    }

    @Override
    public String audit() {
        return "PortfolioEntryPlanningPackageGroup [id=" + id + ", name=" + name + ", description=" + description + " ]";
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
        return this.getName().toLowerCase().compareTo(v.getName().toLowerCase());
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
