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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import com.avaje.ebean.Model;
import framework.services.api.commons.IApiObject;
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;

/**
 * A life cycle milestone instance status type is used when a milestone instance
 * is passed to define its status (approved or not).
 * 
 * @author Johann Kohler
 */
@Entity
public class LifeCycleMilestoneInstanceStatusType extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = -8103953429197184883L;

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.SMALL_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    public String description;

    public Boolean isApproved;

    public Boolean selectable;

    @OneToMany(mappedBy = "lifeCycleMilestoneInstanceStatusType")
    public List<LifeCycleMilestoneInstance> lifeCycleMilestoneInstances;

    @OneToMany(mappedBy = "defaultLifeCycleMilestoneInstanceStatusType")
    public List<LifeCycleMilestone> lifeCycleMilestones;

    @Override
    public String audit() {
        return "LifeCycleMilestoneInstanceStatusType [deleted=" + deleted + ", lastUpdate=" + lastUpdate + ", id=" + id + ", name=" + name + ", description="
                + description + ", isApproved=" + isApproved + ", selectable=" + selectable + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        this.update();
    }

    @Override
    public String getDescription() {
        return Msg.get(description);
    }

    @Override
    public String getName() {
        return Msg.get(name);
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
        return id;
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

    @Override
    public String getApiName() {
        return getName();
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }
}
