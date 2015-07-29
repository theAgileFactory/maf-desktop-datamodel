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
import javax.persistence.OneToOne;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.db.ebean.Model;

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
 * A project manager is supposed to deliver some portfolioEntry reports about
 * the portfolioEntries he/she manages.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryReport extends Model implements IModel, IApiObject {

    private static final long serialVersionUID = -8353026570058767344L;

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
    public Date publicationDate;

    @Column(length = IModelConstants.XLARGE_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String comments;

    public Boolean isPublished;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntry portfolioEntry;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public PortfolioEntryReportStatusType portfolioEntryReportStatusType;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public Actor author;

    @OneToOne(mappedBy = "lastPortfolioEntryReport")
    public PortfolioEntry portfolioEntryWithCurrentReportAsLast;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", creationDate=" + creationDate + ", publicationDate=" + publicationDate + ", comments="
                + comments + ", isPublished=" + isPublished + "]";
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
    public List<CustomAttributeApiValue> getCustomAttributesAsSerializableValues() {
        return CustomAttributeApiHandler.getSerializableValues(PortfolioEntryReport.class, id);
    }

}
