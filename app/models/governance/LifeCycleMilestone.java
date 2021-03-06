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

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.annotation.Where;
import framework.services.api.commons.IApiObject;
import framework.utils.Msg;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import models.pmo.OrgUnit;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * A life cycle process is made of multiple life cycle milestones. A milestone
 * is an optional or mandatory step in the life of the project. It is usually
 * associated with a budget, a resource plan and an approval from the process
 * board members.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
public class LifeCycleMilestone extends Model implements IModel, IApiObject {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.SMALL_STRING, nullable = false)
    public String shortName;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    public String description;

    @Column(name = "`order`", scale = 5)
    public int order;

    @Column(scale = 5)
    public int subOrder;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public LifeCycleProcess lifeCycleProcess;

    public boolean isActive;

    public boolean isAdditional;

    public Type type;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "lifeCycleMilestones")
    @Where(clause = "${ta}.deleted=0")
    public List<Actor> actorApprovers;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "lifeCycleMilestones")
    @Where(clause = "${ta}.deleted=0")
    public List<OrgUnit> orgUnitApprovers;

    @OneToMany(mappedBy = "lifeCycleMilestone")
    public List<LifeCycleMilestoneInstance> lifeCycleMilestoneInstances;

    @OneToMany(mappedBy = "lifeCycleMilestone")
    public List<PlannedLifeCycleMilestoneInstance> plannedLifeCycleMilestoneInstances;

    public boolean isReviewRequired;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public LifeCycleMilestoneInstanceStatusType defaultLifeCycleMilestoneInstanceStatusType;

    @OneToMany(mappedBy = "startLifeCycleMilestone")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCyclePhase> startLifeCyclePhases;

    @OneToMany(mappedBy = "endLifeCycleMilestone")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCyclePhase> endLifeCyclePhases;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [id=" + id + ", shortName=" + shortName + ", name=" + name + ", description=" + description + ", order="
                + order + ", subOrder=" + subOrder + "]";
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

    /**
     * Get the short name.
     */
    public String getShortName() {
        return Msg.get(shortName);
    }

    /**
     * Get the i18n key of the short name.
     */
    public String getShortNameKey() {
        return shortName;
    }

    /**
     * Get the description.
     */
    public String getDescription() {
        return Msg.get(description);
    }

    @Override
    public String getApiName() {
        return getName();
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    /**
     * The milestone type.
     * 
     * @author Johann Kohler
     * 
     */
    public enum Type {

        @EnumValue("IMPLEMENTATION_START_DATE") IMPLEMENTATION_START_DATE(true), @EnumValue("IMPLEMENTATION_END_DATE") IMPLEMENTATION_END_DATE(true);

        public boolean isRelease;

        Type(boolean isRelease) {
            this.isRelease = isRelease;
        }

        /**
         * Get the label.
         */
        public String getLabel() {
            return Msg.get("object.life_cycle_milestone.type." + this.name() + ".label");
        }
    }

    public enum DisplayType {
        SHORT_DISPLAY("preference.governance_milestone_display_preference.short.name"),
        LONG_DISPLAY("preference.governance_milestone_display_preference.long.name");

        public String key;

        DisplayType(String key) {
            this.key = key;
        }
    }
}
