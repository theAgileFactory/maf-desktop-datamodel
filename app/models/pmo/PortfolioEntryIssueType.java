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

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;
import framework.services.api.commons.IApiObject;
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * A type of portfolio entry issue.
 * 
 * @author Pierre-Yves Cloux
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryIssueType extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    @ApiModelProperty(required = true)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    public String description;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean selectable;

    /**
     * Default constructor.
     */
    public PortfolioEntryIssueType() {
        super();
    }

    @Override
    public String audit() {
        return "PortfolioEntryIssueType [deleted=" + deleted + ", lastUpdate=" + lastUpdate + ", id=" + id + ", name=" + name + ", description=" + description
                + ", selectable=" + selectable + "]";
    }

    @OneToMany(mappedBy = "portfolioEntryIssueType")
    public List<PortfolioEntryIssue> portfolioEntryIssues;

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        update();
    }

    @Override
    @JsonProperty(value = "name")
    public String getDescription() {
        return Msg.get(this.description);
    }

    @Override
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
        return selectable;
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

}
