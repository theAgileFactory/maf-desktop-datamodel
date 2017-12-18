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
import models.framework_models.parent.IModel;
import models.pmo.Actor;
import play.mvc.Controller;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Guillaume Petit
 */
@MappedSuperclass
public abstract class BizDockModel extends Model implements IModel {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL)
    public Actor updatedBy;

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
}