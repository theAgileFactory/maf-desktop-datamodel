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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.services.custom_attribute.ICustomAttributeManagerService;
import framework.services.custom_attribute.ICustomAttributeManagerService.CustomAttributeValueObject;
import framework.utils.Msg;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import models.pmo.PortfolioEntry;
import play.Play;

/**
 * Define a requirement.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Requirement extends Model implements IModel, IApiObject {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @JsonProperty
    public boolean isDefect;

    @ManyToOne(cascade = CascadeType.ALL)
    public PortfolioEntry portfolioEntry;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String externalRefId;

    @Column(length = IModelConstants.LARGE_STRING)
    @JsonProperty
    public String externalLink;

    @Column(length = IModelConstants.LARGE_STRING)
    public String name;

    public String description;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String category;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public RequirementStatus requirementStatus;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @ApiModelProperty(dataType = "String")
    @JsonPropertyLink
    public RequirementPriority requirementPriority;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @ApiModelProperty(dataType = "String")
    @JsonPropertyLink
    public RequirementSeverity requirementSeverity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public Actor author;

    @JsonProperty
    public Integer storyPoints;

    @JsonProperty
    public Double initialEstimation;

    @JsonProperty
    public Double effort;

    @JsonProperty
    public Double remainingEffort;

    @JsonProperty
    public Boolean isScoped;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonProperty
    public Iteration iteration;

    @ManyToMany(mappedBy = "requirements")
    public List<Deliverable> deliverables;

    /**
     * Default constructor.
     */
    public Requirement() {
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
        return "Requirement [id=" + id + ", name=" + name + ", description=" + description + " ]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    /** Api methods **/

    @Override
    @ApiModelProperty(required = true)
    @JsonProperty(value = "name")
    public String getApiName() {
        return getName();
    }

    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        ICustomAttributeManagerService customAttributeManagerService = Play.application().injector().instanceOf(ICustomAttributeManagerService.class);
        return customAttributeManagerService.getSerializableValues(Requirement.class, id);
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
