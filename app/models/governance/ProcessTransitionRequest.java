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
package models.governance;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import play.db.ebean.Model;
import framework.services.api.commons.IApiObject;
import framework.utils.Utilities;
import framework.utils.formats.DateType;

/**
 * A request is issued by a user which want to have a certain governance action
 * to be completed.<br/>
 * A request is associated with one or more attachments.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
public class ProcessTransitionRequest extends Model implements IModel, IApiObject {

    private static final long serialVersionUID = 783973340407387908L;

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.LARGE_STRING, nullable = false)
    public String title;

    @DateType
    public Date creationDate;

    @DateType
    public Date reviewDate;

    public Boolean accepted;

    @Column(length = IModelConstants.VLARGE_STRING)
    public String comments;

    @Column(length = IModelConstants.SMALL_STRING)
    public String requestType;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Actor requester;

    /**
     * Default constructor.
     */
    public ProcessTransitionRequest() {
    }

    @Override
    public String audit() {
        return "ProcessTransitionRequest [deleted=" + deleted + ", lastUpdate=" + lastUpdate + ", id=" + id + ", title=" + title + ", creationDate="
                + creationDate + ", reviewDate=" + reviewDate + ", accepted=" + accepted + ", comments=" + comments + ", requestType=" + requestType + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        this.deleted = true;
        this.update();
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", title, creationDate != null ? Utilities.getDateFormat(IModelConstants.DATE_FORMAT).format(creationDate) : "-");
    }

    /**
     * The possible values for the request type.
     */
    public enum RequestType {
        NEW_PORTFOLIO_ENTRY, MILESTONE_APPROVAL
    }

    @Override
    public String getApiName() {
        return title;
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
