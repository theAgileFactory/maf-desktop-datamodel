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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
import framework.services.api.commons.JsonPropertyLink;
import framework.utils.CustomAttributeApiHandler;
import framework.utils.CustomAttributeApiHandler.CustomAttributeApiValue;
import framework.utils.formats.DateType;

/**
 * An portfolio entry can be associated with some risks. These risks can evolve
 * during the life of the project (occur, be avoided or mitigated).
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryRisk extends Model implements IModel, IApiObject {

    private static final long serialVersionUID = 3998785409668477844L;

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @DateType
    @JsonProperty
    public Date creationDate;

    @DateType
    @JsonProperty
    public Date targetDate;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String description;

    @JsonProperty
    public boolean hasOccured;

    @JsonProperty
    public boolean isActive;

    @JsonProperty
    public boolean isMitigated;

    @DateType
    @JsonProperty
    public Date closureDate;

    @Column(length = IModelConstants.VLARGE_STRING)
    @JsonProperty
    public String mitigationComment;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public PortfolioEntryRiskType portfolioEntryRiskType;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntry portfolioEntry;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public Actor owner;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", creationDate=" + creationDate + ", targetDate=" + targetDate + ", name=" + name
                + ", description=" + description + ", hasOccured=" + hasOccured + ", isActive=" + isActive + ", isMitigated=" + isMitigated + ", dosureDate="
                + closureDate + ", mitigationComment=" + mitigationComment + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
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
        return name;
    }

    /**
     * Get the custom attribute values.
     */
    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeApiValue> getCustomAttributesAsSerializableValues() {
        return CustomAttributeApiHandler.getSerializableValues(PortfolioEntryRisk.class, id);
    }

}
