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
package models.common;

import com.avaje.ebean.Model;
import framework.services.session.IUserSessionManagerPlugin;
import models.framework_models.parent.IModel;
import play.Play;
import play.mvc.Http;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author Guillaume Petit
 */
@MappedSuperclass
public abstract class BizDockModel extends Model implements IModel {

    public boolean deleted = false;

    public Date creationDate;

    public String createdBy;

    public Date lastUpdate;

    public String updatedBy;

    /**
     * Default constructor
     */
    public BizDockModel() {
    }

    @Override
    public String audit() {
        return this.toString();
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        update();
    }

    @PrePersist
    public void prePersist() {
        String userSessionId = Play.application().injector().instanceOf(IUserSessionManagerPlugin.class).getUserSessionId(Http.Context.current());
        this.lastUpdate = new Date();
        this.creationDate = new Date();
        this.updatedBy = userSessionId;
        this.createdBy = userSessionId;
    }

    @PreUpdate
    public void preUpdate() {
        String userSessionId = Play.application().injector().instanceOf(IUserSessionManagerPlugin.class).getUserSessionId(Http.Context.current());
        this.lastUpdate = new Date();
        this.updatedBy = userSessionId;
    }
}