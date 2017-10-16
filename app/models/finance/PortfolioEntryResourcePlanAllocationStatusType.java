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
package models.finance;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;
import framework.services.api.commons.IApiObject;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The status for a ressource allocation request. Can be one of the following:
 * <ul>
 * <li>PENDING</li>
 * <li>CONFIRMED</li>
 * <li>REFUSED</li>
 * </ul>
 *
 * @author Guillaume Petit
 */
@Entity
public class PortfolioEntryResourcePlanAllocationStatusType extends Model implements IModel, IApiObject {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.SMALL_STRING, nullable = false)
    @Enumerated(EnumType.STRING)
    public AllocationStatus status;

    public enum AllocationStatus {
        DRAFT,
        PENDING,
        CONFIRMED,
        REFUSED
    }

    @Override
    public String getApiName() {
        return this.status.name();
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    @Override
    public String audit() {
        return "PortfolioEntryResourcePlanAllocationStatusType [deleted=" + deleted + ", lastUpdate=" + lastUpdate + ", id=" + id + ", status=" + status + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        this.deleted = true;
        update();
    }

    public String getCssClass() {
        switch (status) {
            case DRAFT:
                return "default";
            case CONFIRMED:
                return "success";
            case PENDING:
                return "warning";
            case REFUSED:
                return "danger";
            default:
                return "";
        }
    }
}