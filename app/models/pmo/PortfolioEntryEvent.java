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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;
import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.services.custom_attribute.ICustomAttributeManagerService;
import framework.services.custom_attribute.ICustomAttributeManagerService.CustomAttributeValueObject;
import framework.utils.formats.DateType;
import models.common.BizDockModel;
import models.framework_models.parent.IModel;
import play.Play;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

/**
 * An portfolio entry event is a message with a type that is displayed in the
 * overview of a portfolio entry.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryEvent extends BizDockModel implements IModel, IApiObject {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    @DateType
    @JsonProperty
    public Date getCreationDate() {
        return this.creationDate;
    }

    @ManyToOne
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    public PortfolioEntryEventType portfolioEntryEventType;

    @JsonProperty
    @ApiModelProperty(required = true)
    public String message;

    @ManyToOne
    public PortfolioEntry portfolioEntry;

    @ManyToOne(optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public Actor actor;

    /**
     * Default constructor.
     */
    public PortfolioEntryEvent() {
    }

    @Override
    public String audit() {
        return "PortfolioEntryEvent [id=" + id + ", message=" + message + "]";
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
    public String getApiName() {
        return null;
    }

    /**
     * Get custom attribute values.
     */
    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        ICustomAttributeManagerService customAttributeManagerService = Play.application().injector().instanceOf(ICustomAttributeManagerService.class);
        return customAttributeManagerService.getSerializableValues(PortfolioEntryEvent.class, id);
    }

}
