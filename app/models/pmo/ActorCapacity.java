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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;

/**
 * A month capacity for an actor.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorCapacity extends Model implements IModel, IApiObject {

    private static final long serialVersionUID = 34612537913415L;

    @Id
    @JsonProperty
    @ApiModelProperty(required = true, position = 1)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    public Actor actor;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Integer year;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Integer month;

    @JsonProperty
    @ApiModelProperty(required = true)
    public Double value;

    @Override
    public String audit() {
        return "Competency [id=" + id + ", actor=" + actor.getName() + ", year=" + year + ", month=" + month + ", value=" + value + "]";
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
    @ApiModelProperty(required = true, position = 2)
    @JsonProperty(value = "name")
    public String getApiName() {
        return actor.getApiName();
    }

}
