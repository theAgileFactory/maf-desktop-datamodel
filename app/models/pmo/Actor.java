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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.delivery.Release;
import models.delivery.Requirement;
import models.finance.CostCenter;
import models.finance.PortfolioEntryResourcePlanAllocatedActor;
import models.finance.PurchaseOrderLineItem;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.governance.LifeCycleMilestone;
import models.governance.LifeCycleMilestoneInstance;
import models.governance.LifeCycleMilestoneInstanceApprover;
import models.governance.ProcessTransitionRequest;
import models.timesheet.TimesheetActivityAllocatedActor;
import models.timesheet.TimesheetReport;
import com.avaje.ebean.Model;

import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.utils.CustomAttributeApiHandler;
import framework.utils.CustomAttributeApiHandler.CustomAttributeApiValue;
import framework.utils.ISelectableValueHolder;

/**
 * Represent a person involved in a governance process.<br/>
 * This person might use the system and be also a Principal or not. The link
 * between Actor and Principal is performed using the "login" field.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor extends Model implements IModel, IApiObject, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = -5220286436901888446L;

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isActive = true;

    @Version
    public Timestamp lastUpdate;

    /**
     * Unique Id in the source system if any.
     */
    @Column(length = IModelConstants.LARGE_STRING)
    @JsonProperty
    @ApiModelProperty(position = 2)
    public String refId;

    /**
     * Reference in the ERP system (usually the ERP is using a different type of
     * reference).
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String erpRefId;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String firstName;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    @ApiModelProperty(required = true)
    public String lastName;

    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String title;

    @Column(length = IModelConstants.LARGE_STRING)
    @JsonProperty
    public String mail;

    /**
     * The mobile phone number.<br/>
     * Format is : +41787879070
     */
    @Column(length = IModelConstants.PHONE_NUMBER)
    @JsonProperty
    public String mobilePhone;

    /**
     * The fixed phone number.<br/>
     * Format is : +41217879070
     */
    @Column(length = IModelConstants.PHONE_NUMBER)
    @JsonProperty
    public String fixPhone;

    /**
     * Id of the employee in the HR system.
     */
    @Column(length = IModelConstants.SMALL_STRING)
    @JsonProperty
    public String employeeId;

    /**
     * Login of the employee (usually Windows-login).
     */
    @Column(length = IModelConstants.LARGE_STRING)
    @JsonProperty
    public String uid;

    /**
     * The org-unit to which belongs this actor.
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Where(clause = "${ta}.deleted=0")
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public OrgUnit orgUnit;

    /**
     * Return the list of Org-unit which this actor is managing.
     */
    @OneToMany(mappedBy = "manager")
    public List<OrgUnit> managedOrgUnits;

    /**
     * Return the type of the actor.
     */
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public ActorType actorType;

    /**
     * Return the manager of this actor (if known).
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @ApiModelProperty(dataType = "String")
    @JsonPropertyLink
    public Actor manager;

    /**
     * The default competency of the actor.
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @ApiModelProperty(dataType = "String")
    @JsonPropertyLink
    public Competency defaultCompetency;

    /**
     * Return the list of Actors for which the current Actor is a manager.
     */
    @OneToMany(mappedBy = "manager")
    public List<Actor> subordinates;

    /**
     * Return the list of PortfolioEntry managed by this actor.
     */
    @OneToMany(mappedBy = "manager")
    public List<PortfolioEntry> portfolioEntries;

    /**
     * Return the list of Portfolio managed by this actor.
     */
    @OneToMany(mappedBy = "manager")
    public List<Portfolio> portfolios;

    /**
     * Return the list of PortfolioEntryReport for which the actor is the
     * author.
     */
    @OneToMany(mappedBy = "author")
    public List<PortfolioEntryReport> portfolioEntryReports;

    /**
     * Return the list of ProcessTransitionRequests raised by this actor.
     */
    @OneToMany(mappedBy = "requester")
    public List<ProcessTransitionRequest> processTransitionRequests;

    /**
     * The list of relationships that this actor has with some
     * {@link PortfolioEntry} or {@link Portfolio}.
     */
    @OneToMany(mappedBy = "actor")
    public List<Stakeholder> stakeholders;

    /**
     * The list of POs line items requested by this actor.
     */
    @OneToMany(mappedBy = "requester")
    public List<PurchaseOrderLineItem> purchaseOrderLineItems;

    /**
     * The list of cost centers owned by this actor.
     */
    @OneToMany(mappedBy = "owner")
    public List<CostCenter> costCenters;

    /**
     * The list of risks for which the actor is the owner.
     */
    @OneToMany(mappedBy = "owner")
    public List<PortfolioEntryRisk> portfolioEntryRisks;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "life_cycle_milestone_approver")
    public List<LifeCycleMilestone> lifeCycleMilestones;

    @OneToMany(mappedBy = "actor")
    public List<LifeCycleMilestoneInstanceApprover> lifeCycleMilestoneInstanceApprovers;

    @OneToMany(mappedBy = "actor")
    public List<PortfolioEntryResourcePlanAllocatedActor> portfolioEntryResourcePlanAllocatedActors;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actor")
    public List<PortfolioEntryEvent> portfolioEntryEvents;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actor")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetReport> timesheetReports;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @Where(clause = "${ta}.deleted=0")
    public List<Requirement> requirements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manager")
    @Where(clause = "${ta}.deleted=0")
    public List<Release> releases;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "actor_has_competency")
    @Where(clause = "${ta}.deleted=0")
    public List<Competency> competencies;

    @OneToMany(mappedBy = "actor")
    public List<ActorCapacity> capacities;

    @OneToMany(mappedBy = "actor")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetActivityAllocatedActor> timesheetActivityAllocatedActors;

    @OneToMany(mappedBy = "approver")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCycleMilestoneInstance> approvedLifeCycleMilestoneInstances;

    /**
     * Default constructor.
     */
    public Actor() {
        super();
    }

    /**
     * Get the first name.
     */
    public String getFirstName() {
        if (firstName != null) {
            return firstName;
        }
        return "";
    }

    /**
     * Get the last name.
     * 
     */
    public String getLastName() {
        if (lastName != null) {
            return lastName;
        }
        return "";
    }

    /**
     * Get the full name.<br>
     * LastName firstName
     */
    public String getName() {
        String name = getLastName() + " " + getFirstName();
        return name.trim();
    }

    /**
     * Get the full name.<br>
     * firstName LastName
     */
    public String getNameHumanReadable() {
        String name = getFirstName() + " " + getLastName();
        return name.trim();
    }

    @Override
    public String audit() {
        return "Actor [id=" + id + ", refId=" + refId + ", firstName=" + firstName + ", lastName=" + lastName + ", title=" + title + ", mail=" + mail
                + ", mobilePhone=" + mobilePhone + ", fixPhone=" + fixPhone + ", employeeId=" + employeeId + ", uid=" + uid + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Long getValue() {
        return id;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean isSelectable() {
        return isActive;
    }

    @Override
    public void setUrl(String arg0) {
    }

    /** Api methods. **/

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    @Override
    public String getApiName() {
        return getName();
    }

    /**
     * Get the custom attributes.
     */
    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String")
    public List<CustomAttributeApiValue> getCustomAttributesAsSerializableValues() {
        return CustomAttributeApiHandler.getSerializableValues(Actor.class, id);
    }
}
