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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;
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
import framework.utils.Msg;
import framework.utils.Utilities;
import framework.utils.formats.DateType;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import models.pmo.Actor;
import models.pmo.PortfolioEntryPlanningPackage;
import org.apache.commons.lang3.tuple.Pair;
import play.Play;

/**
 * The portfolioEntry resource plan allocated actor defines the association
 * between an actor and a resource plan. This association contains the number of
 * days which is to be allocated.
 * 
 * @author Thomas Badin
 * @author Johann Kohler
 */
@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioEntryResourcePlanAllocatedActor extends Model implements IModel, IApiObject {

    @Id
    @JsonProperty
    @ApiModelProperty(required = true)
    public long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @DateType
    @JsonProperty
    public Date startDate;

    @DateType
    @JsonProperty
    public Date endDate;

    @JsonProperty
    public boolean isConfirmed = false;

    @JsonProperty
    public Boolean followPackageDates;

    @JsonPropertyLink
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Actor actor;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public PortfolioEntryResourcePlan portfolioEntryResourcePlan;

    @JsonProperty
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public PortfolioEntryPlanningPackage portfolioEntryPlanningPackage;

    @JsonPropertyLink(linkField = "code")
    @ApiModelProperty(dataType = "String", required = true)
    @ManyToOne(cascade = CascadeType.ALL)
    public Currency currency;

    @Column(scale = 8, precision = 18)
    @JsonProperty
    public BigDecimal currencyRate;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    @ApiModelProperty(required = true)
    public BigDecimal days;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    @ApiModelProperty(required = true)
    public BigDecimal dailyRate;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal forecastDays;

    @Column(scale = IModelConstants.BIGNUMBER_SCALE, precision = IModelConstants.BIGNUMBER_PRECISION)
    @JsonProperty
    public BigDecimal forecastDailyRate;

    @OneToMany(mappedBy = "portfolioEntryResourcePlanAllocatedActor")
    @Where(clause = "${ta}.deleted=0")
    @JsonProperty
    public List<PortfolioEntryResourcePlanAllocatedActorDetail> portfolioEntryResourcePlanAllocatedActorDetails;

    public boolean monthlyAllocated = false;

    @Override
    public String audit() {
        return this.getClass().getSimpleName() + " [" + ", days=" + days + "]";
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
     * Get the date for a display.
     */
    public String getDisplayDate() {
        if (this.startDate != null && this.endDate != null) {
            return Msg.get("object.allocated_resource.date.period", Utilities.getDateFormat(null).format(this.startDate),
                    Utilities.getDateFormat(null).format(this.endDate));
        } else if (this.endDate != null) {
            return Utilities.getDateFormat(null).format(this.endDate);
        } else {
            return null;
        }
    }

    /* API methods */

    @Override
    public String getApiName() {
        return null;
    }

    @JsonProperty(value = "customAttributes")
    @ApiModelProperty(dataType = "String", required = false)
    public List<CustomAttributeValueObject> getCustomAttributesAsSerializableValues() {
        ICustomAttributeManagerService customAttributeManagerService = Play.application().injector().instanceOf(ICustomAttributeManagerService.class);
        return customAttributeManagerService.getSerializableValues(PortfolioEntryResourcePlanAllocatedActor.class, id);
    }

    @Override
    public boolean getApiDeleted() {
        return this.deleted;
    }

    /**
     * Clear all allocations details from allocated actor
     */
    public void clearAllocations() {
        for (PortfolioEntryResourcePlanAllocatedActorDetail detail : portfolioEntryResourcePlanAllocatedActorDetails) {
            detail.doDelete();
            detail.save();
        }
        portfolioEntryResourcePlanAllocatedActorDetails.clear();
    }

    public PortfolioEntryResourcePlanAllocatedActorDetail getDetail(int year, int month) {
        if (this.portfolioEntryResourcePlanAllocatedActorDetails.isEmpty()) {
            this.computeAllocationDetails();
        }
        Optional<PortfolioEntryResourcePlanAllocatedActorDetail> optionalDetail = this.portfolioEntryResourcePlanAllocatedActorDetails.stream().filter(detail -> detail.month.equals(month) && detail.year.equals(year)).findFirst();
        return optionalDetail.isPresent() ? optionalDetail.get() : null;
    }

    public void computeAllocationDetails() {
        // Clear current allocation details
        this.clearAllocations();

        // Distribute allocations monthly from start date to end date
        long endMillis = removeTime(this.endDate).getTimeInMillis();
        long startMillis = removeTime(this.startDate).getTimeInMillis();
        int days = 1 + (int) ((endMillis - startMillis) / (1000 * 60 * 60 * 24));
        Double dayRate = this.days.doubleValue() / days;
        Calendar start = removeTime(this.startDate);
        Map<Pair<Integer, Integer>, Double> daysMap = new HashMap<>();
        for (int i = 0; i < days; i++) {
            Pair<Integer, Integer> month = Pair.of(start.get(Calendar.YEAR), start.get(Calendar.MONTH));
            Double d = daysMap.get(month) == null ? 0.0 : daysMap.get(month);
            daysMap.put(month, d + dayRate);
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        for (Pair<Integer, Integer> month : daysMap.keySet()) {
            createOrUpdateAllocationDetail(month.getLeft(), month.getRight(), daysMap.get(month));
        }
    }

    private static Calendar removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public PortfolioEntryResourcePlanAllocatedActorDetail createOrUpdateAllocationDetail(Integer year, Integer month, Double days) {
        if (year == null || month == null || days == null) {
            return null;
        }
        Optional<PortfolioEntryResourcePlanAllocatedActorDetail> optionalDetail = this.portfolioEntryResourcePlanAllocatedActorDetails.stream().filter(detail -> detail.year.equals(year) && detail.month.equals(month)).findFirst();
        PortfolioEntryResourcePlanAllocatedActorDetail detail;
        if (optionalDetail.isPresent()) {
            // Update
            detail = optionalDetail.get();
            detail.days = days;
            detail.update();
        } else {
            // Create
            detail = new PortfolioEntryResourcePlanAllocatedActorDetail(this, year, month, days);
            detail.save();
        }
        return detail;
    }

}
