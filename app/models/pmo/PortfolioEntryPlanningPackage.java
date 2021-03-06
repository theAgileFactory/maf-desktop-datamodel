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
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumMapping;
import com.avaje.ebean.annotation.Where;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import framework.services.api.commons.IApiObject;
import framework.services.api.commons.JsonPropertyLink;
import framework.services.custom_attribute.ICustomAttributeManagerService;
import framework.services.custom_attribute.ICustomAttributeManagerService.CustomAttributeValueObject;
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;
import framework.utils.Utilities;
import framework.utils.formats.DateType;
import models.common.BizDockModel;
import models.delivery.PortfolioEntryDeliverable;
import models.finance.PortfolioEntryResourcePlanAllocatedActor;
import models.finance.PortfolioEntryResourcePlanAllocatedCompetency;
import models.finance.PortfolioEntryResourcePlanAllocatedOrgUnit;
import models.finance.WorkOrder;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.timesheet.TimesheetEntry;
import play.Play;
import play.data.validation.Constraints.Required;

/**
 * An portfolio entry planning package is a date or an interval that defines
 * "how to delive" something.
 * 
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryPlanningPackage extends BizDockModel implements IModel, IApiObject, ISelectableValueHolder<Long> {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public Long id;

    @Column(length = IModelConstants.MEDIUM_STRING)
    public String refId;

    @Required
    @Column(length = IModelConstants.MEDIUM_STRING)
    public String name;

    @Column(length = IModelConstants.VLARGE_STRING)
    public String description;

    @DateType
    @JsonProperty
    public Date startDate;

    @DateType
    @JsonProperty
    public Date endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public PortfolioEntryPlanningPackageType portfolioEntryPlanningPackageType;

    @Column(name = "`order`")
    public Integer order;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JsonPropertyLink
    @ApiModelProperty(dataType = "String")
    public PortfolioEntryPlanningPackageGroup portfolioEntryPlanningPackageGroup;

    public Status status;

    public boolean isOpex;

    @ManyToOne
    public PortfolioEntry portfolioEntry;

    @OneToMany(mappedBy = "portfolioEntryPlanningPackage")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryResourcePlanAllocatedActor> portfolioEntryResourcePlanAllocatedActors;

    @OneToMany(mappedBy = "portfolioEntryPlanningPackage")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryResourcePlanAllocatedOrgUnit> portfolioEntryResourcePlanAllocatedOrgUnits;

    @OneToMany(mappedBy = "portfolioEntryPlanningPackage")
    @Where(clause = "${ta}.deleted=0")
    public List<PortfolioEntryResourcePlanAllocatedCompetency> portfolioEntryResourcePlanAllocatedCompetencies;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioEntryPlanningPackage")
    @Where(clause = "${ta}.deleted=0")
    public List<TimesheetEntry> timesheetEntries;

    @OneToMany(mappedBy = "portfolioEntryPlanningPackage")
    @Where(clause = "${ta}.deleted=0")
    public List<WorkOrder> workOrders;

    @OneToMany(mappedBy = "portfolioEntryPlanningPackage")
    public List<PortfolioEntryDeliverable> portfolioEntryDeliverable;

    /**
     * Default constructor.
     */
    public PortfolioEntryPlanningPackage() {
    }

    /**
     * Construct a package for a portfolio entry thanks a pattern.
     * 
     * @param portfolioEntry
     *            the portfolio entry
     * @param pattern
     *            the pattern
     */
    public PortfolioEntryPlanningPackage(PortfolioEntry portfolioEntry, PortfolioEntryPlanningPackagePattern pattern) {

        this.name = pattern.name;
        this.description = pattern.description;
        this.portfolioEntryPlanningPackageType = pattern.portfolioEntryPlanningPackageType;
        this.order = pattern.order;
        this.portfolioEntryPlanningPackageGroup = pattern.portfolioEntryPlanningPackageGroup;
        this.status = Status.NOT_STARTED;
        this.portfolioEntry = portfolioEntry;
        this.isOpex = pattern.isOpex;

    }

    @Override
    public String audit() {
        return "PortfolioEntryPlanningPackage [id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate + ", endDate="
                + endDate + " ]";
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
    public int compareTo(Object o) {
        PortfolioEntryPlanningPackage c = (PortfolioEntryPlanningPackage) o;
        if (this.order == null) {
            return -1;
        }
        if (c.order == null) {
            return +1;
        }
        return this.order > c.order ? +1 : this.order < c.order ? -1 : 0;
    }

    @Override
    @JsonProperty(value = "description")
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Long getValue() {
        return this.id;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public void setUrl(String arg0) {
    }

    /**
     * Get the date for a display.
     */
    public String getDisplayDate() {
        if (this.startDate != null && this.endDate != null) {
            return Msg.get("object.portfolio_entry_planning_package.date.period", Utilities.getDateFormat(null).format(this.startDate),
                    Utilities.getDateFormat(null).format(this.endDate));
        } else if (this.endDate != null) {
            return Utilities.getDateFormat(null).format(this.endDate);
        } else {
            return null;
        }
    }

    /**
     * Define the status of a planning package.
     * 
     * @author Johann Kohler
     */
    @EnumMapping(nameValuePairs = "NOT_STARTED=NOT_STARTED, ON_GOING=ON_GOING, CLOSED=CLOSED", integerType = false)
    public enum Status {
        NOT_STARTED, ON_GOING, CLOSED
    }

    /** Api methods. **/

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    @Override
    @JsonProperty(value = "name")
    @ApiModelProperty(required = true)
    public String getApiName() {
        return getName();
    }

    /**
     * Get the status.
     */
    @JsonProperty(value = "status")
    @ApiModelProperty(required = true)
    public String getStatusApi() {
        return status.name();
    }

    /**
     * Get the custom attribute values.
     */
    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        ICustomAttributeManagerService customAttributeManagerService = Play.application().injector().instanceOf(ICustomAttributeManagerService.class);
        return customAttributeManagerService.getSerializableValues(PortfolioEntryPlanningPackage.class, id);
    }
}
