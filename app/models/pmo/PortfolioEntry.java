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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.services.kpi.IKpiObjectsContainer;
import framework.utils.CustomAttributeFormAndDisplayHandler;
import framework.utils.CustomAttributeFormAndDisplayHandler.CustomAttributeValueObject;
import framework.utils.Msg;
import framework.utils.formats.DateType;
import models.delivery.Iteration;
import models.delivery.PortfolioEntryDeliverable;
import models.delivery.Requirement;
import models.finance.PurchaseOrder;
import models.finance.WorkOrder;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.governance.LifeCycleInstance;
import models.governance.LifeCycleMilestoneInstance;
import models.governance.LifeCycleProcess;
import models.timesheet.TimesheetEntry;

/**
 * An portfolioEntry is a unit of work consuming some resources.<br/>
 * Various types can be envisioned.<br/>
 * The most common one being a project.
 * 
 * @author Pierre-Yves Cloux
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntry extends Model implements IModel, IApiObject, IKpiObjectsContainer {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    /**
     * Reference in the source system.
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String refId;

    /**
     * A unique name (usually a number) which is known by everybody as the
     * public Id for this portfolioEntry.
     */
    @Column(length = IModelConstants.SMALL_STRING)
    @JsonProperty
    public String governanceId;

    /**
     * Reference in the ERP system (usually the ERP is using a different type of
     * reference).
     */
    @Column(length = IModelConstants.MEDIUM_STRING)
    @JsonProperty
    public String erpRefId;

    @Column(length = IModelConstants.MEDIUM_STRING, nullable = false)
    @ApiModelProperty(required = true)
    public String name;

    @Column(length = IModelConstants.XLARGE_STRING)
    @ApiModelProperty(required = true)
    public String description;

    @DateType
    @JsonProperty
    @ApiModelProperty(required = true)
    public Date creationDate;

    /**
     * True if the portfolioEntry is visible to other users than its manager,
     * its stakeholders and the PMO.
     */
    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean isPublic;

    /**
     * True if the portfolioEntry is "closed" or archived<br/>
     * This flag is to be used to flag an portfolioEntry which is not more
     * active.
     */
    @JsonProperty
    @ApiModelProperty(required = true)
    public boolean archived;

    public boolean isSyndicated;

    /**
     * The manager of the portfolioEntry.<br/>
     * The one that is on a daily basis managing the work progress.
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    public Actor manager;

    /**
     * The organizational unit which is officially sponsoring the portfolioEntry
     * <br/>
     * Usually this is a business team.
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public OrgUnit sponsoringUnit;

    /**
     * The organizational units which are officially performing the
     * portfolioEntry (and is thus accountable for the delivery).<br/>
     * Usually this is one or more IT team.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "portfolio_entry_has_delivery_unit")
    @Where(clause = "${ta}.deleted=0")
    public List<OrgUnit> deliveryUnits;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    public PortfolioEntryType portfolioEntryType;

    /**
     * The groups to which this portfolioEntry is associated.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "portfolio_has_portfolio_entry")
    @Where(clause = "${ta}.deleted=0")
    public List<Portfolio> portfolios;

    /**
     * The list of stakeholders for this portfolioEntry.
     */
    @OneToMany(mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<Stakeholder> stakeholders;

    /**
     * The list of PO items raised for this portfolioEntry.
     */
    @OneToMany(mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<PurchaseOrder> purchaseOrders;

    @OneToMany(mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryRisk> portfolioEntryRisks;

    @OneToMany(mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryReport> portfolioEntryReports;

    @OneToMany(mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<WorkOrder> workOrders;

    @OneToMany(mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<LifeCycleInstance> lifeCycleInstances;

    /**
     * We store the current life cycle instance of the portfolio entry in the DB
     * in order to allow filters and to improve performance. This information is
     * redundant with the attribute "lifeCycleInstances".
     */
    @OneToOne
    public LifeCycleInstance activeLifeCycleInstance;

    /**
     * we store (if exists) the last approved milestone instance of the
     * portfolio entry in the DB in order to allow filters and to improve
     * performance. This information is redundant and could be found with:
     * activeLifeCycleInstance.lifeCycleMilestoneInstances
     */
    @OneToOne
    public LifeCycleMilestoneInstance lastApprovedLifeCycleMilestoneInstance;

    /**
     * we store (if exists) the last create report of the portfolio entry in the
     * DB in order to allow filters and to improve performance. This information
     * is redundant with the attribute "portfolioEntryReports".
     */
    @OneToOne
    public PortfolioEntryReport lastPortfolioEntryReport;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryEvent> portfolioEntryEvents;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryPlanningPackage> planningPackages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetEntry> timesheetEntries;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<Requirement> requirements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntry")
    @Where(clause = "${ta}.deleted=0")
    public List<Iteration> iterations;

    @OneToMany(mappedBy = "sourcePortfolioEntry")
    public List<PortfolioEntryDependency> sourceDependencies;

    @OneToMany(mappedBy = "destinationPortfolioEntry")
    public List<PortfolioEntryDependency> destinationDependencies;

    @OneToMany(mappedBy = "portfolioEntry")
    public List<PortfolioEntryDeliverable> portfolioEntryDeliverables;

    @Override
    public String audit() {
        return "PortfolioEntry [id=" + id + ", refId=" + refId + ", erpRefId=" + erpRefId + ", name=" + name + ", description=" + description + "]";
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
        return governanceId != null ? String.format("%s (%s)", name, governanceId) : name;
    }

    /**
     * Getter for the description.
     */
    @JsonProperty(value = "description")
    @ApiModelProperty(required = true)
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the name with a close eye if confidential.
     */
    public String getDisplayName() {
        if (!isPublic) {
            return "<i id='portfolio-entry-confidential' class='glyphicons glyphicons-eye-close' title='"
                    + Msg.get("object.portfolio_entry.is_condfidential.label") + "'></i> " + getName();
        } else {
            return getName();
        }
    }

    @Override
    public Long getIdForKpi() {
        return this.id;
    }

    @Override
    public Object getObjectByIdForKpi(Long objectId) {
        return Ebean.find(PortfolioEntry.class).where().eq("deleted", false).eq("id", objectId).findUnique();
    }

    @Override
    public List<? extends IKpiObjectsContainer> getAllInstancesForKpi() {
        return Ebean.find(PortfolioEntry.class).where().eq("deleted", false).eq("archived", false).findList();
    }

    /** Api methods. **/

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    /**
     * Get custom attribute values.
     */
    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        return CustomAttributeFormAndDisplayHandler.getSerializableValues(PortfolioEntry.class, id);
    }

    @Override
    @JsonProperty(value = "name")
    @ApiModelProperty(required = true)
    public String getApiName() {
        return getName();
    }

    /**
     * Get the current life cycle process.
     */
    @JsonPropertyLink(value = "lifeCycleProcess")
    @ApiModelProperty(dataType = "String", required = true)
    public LifeCycleProcess getlifeCycleProcess() {
        return activeLifeCycleInstance.lifeCycleProcess;
    }

    /**
     * Get the last portfolio entry report.
     */
    @JsonProperty("lastPortfolioEntryReport")
    public PortfolioEntryReport getApiLastPortfolioEntryReport() {
        return lastPortfolioEntryReport != null
                ? Ebean.find(PortfolioEntryReport.class).where().eq("deleted", false).eq("id", this.lastPortfolioEntryReport.id).findUnique() : null;
    }

}
