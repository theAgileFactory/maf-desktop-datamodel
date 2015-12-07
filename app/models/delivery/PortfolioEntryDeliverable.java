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
package models.delivery;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;

import framework.utils.Msg;
import models.pmo.PortfolioEntry;
import models.pmo.PortfolioEntryPlanningPackage;

/**
 * Define the association table between a portfolio entry and a deliverable.
 * 
 * @author Johann Kohler
 */
@Entity
public class PortfolioEntryDeliverable extends Model {

    public static final long serialVersionUID = 4369856314752364L;

    @EmbeddedId
    public PortfolioEntryDeliverableId id = new PortfolioEntryDeliverableId();

    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_entry_id", insertable = false, updatable = false)
    private PortfolioEntry portfolioEntry;

    @ManyToOne(optional = false)
    @JoinColumn(name = "deliverable_id", insertable = false, updatable = false)
    private Deliverable deliverable;

    public Type type;

    @ManyToOne(optional = true)
    public PortfolioEntryPlanningPackage portfolioEntryPlanningPackage;

    /**
     * Default constructor.
     * 
     * @param portfolioEntry
     *            the portfolio entry
     * @param deliverable
     *            the deliverable
     */
    public PortfolioEntryDeliverable(PortfolioEntry portfolioEntry, Deliverable deliverable) {
        this.portfolioEntry = portfolioEntry;
        this.deliverable = deliverable;
        this.id.portfolioEntryId = portfolioEntry.id;
        this.id.deliverableId = deliverable.id;
    }

    /**
     * Get the portfolio entry.
     */
    public PortfolioEntry getPortfolioEntry() {
        return this.portfolioEntry.deleted ? null : this.portfolioEntry;
    }

    /**
     * Get the deliverable.
     */
    public Deliverable getDeliverable() {
        return this.deliverable.deleted ? null : this.deliverable;
    }

    /**
     * The portfolio entries' relation type.
     * 
     * Note: for a deliverable there is at least ONE relation with a PE, and
     * exactly ONE "OWNER" relation.
     * 
     * @author Johann Kohler
     * 
     */
    public enum Type {
        @EnumValue("OWNER") OWNER, @EnumValue("FOLLOWER") FOLLOWER;

        /**
         * Get the label.
         */
        public String getLabel() {
            return Msg.get("object.portfolio_entry.deliverable.type." + this.name() + ".label");
        }
    }
}
