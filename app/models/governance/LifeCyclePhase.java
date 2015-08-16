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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import com.avaje.ebean.Model;
import framework.services.api.commons.IApiObject;
import framework.utils.Msg;

/**
 * A life cycle phase is period defined by 2 life cycle milestones and
 * associated to a life cycle process.
 * 
 * @author Johann Kohler
 */
@Entity
public class LifeCyclePhase extends Model implements IModel, IApiObject {

    @Id
    public Long id;

    public boolean deleted = false;

    @Column(length = IModelConstants.MEDIUM_STRING)
    public String name;

    @ManyToOne(cascade = CascadeType.ALL)
    public LifeCycleProcess lifeCycleProcess;

    @Column(name = "`order`", scale = 5)
    public Integer order;

    @ManyToOne(cascade = CascadeType.ALL)
    public LifeCycleMilestone startLifeCycleMilestone;

    @ManyToOne(cascade = CascadeType.ALL)
    public LifeCycleMilestone endLifeCycleMilestone;

    public Integer gapDaysStart;

    public Integer gapDaysEnd;

    @Version
    public Timestamp lastUpdate;

    public boolean isRoadmapPhase;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", name=" + name + ", lifeCycleProcess=" + lifeCycleProcess.getName() + ", order=" + order
                + ", startLifeCycleMilestone=" + startLifeCycleMilestone.getName() + ", endLifeCycleMilestone=" + endLifeCycleMilestone.getName() + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    /**
     * Get the name.
     */
    public String getName() {
        return Msg.get(name);
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
