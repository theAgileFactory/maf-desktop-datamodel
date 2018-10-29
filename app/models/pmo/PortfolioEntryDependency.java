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

import models.common.BizDockModel;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Define the association table for the portfolio entry dependencies.
 * 
 * @author Johann Kohler
 */
@Entity
public class PortfolioEntryDependency extends BizDockModel {

    public static final long serialVersionUID = 4369856314752364L;

    @EmbeddedId
    public PortfolioEntryDependencyId id = new PortfolioEntryDependencyId();

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_portfolio_entry_id", insertable = false, updatable = false)
    public PortfolioEntry sourcePortfolioEntry;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_portfolio_entry_id", insertable = false, updatable = false)
    public PortfolioEntry destinationPortfolioEntry;

    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_entry_dependency_type_id", insertable = false, updatable = false)
    public PortfolioEntryDependencyType portfolioEntryDependencyType;

    /**
     * Get the source portfolio entry.
     */
    public PortfolioEntry getSourcePortfolioEntry() {
        return this.sourcePortfolioEntry.deleted ? null : this.sourcePortfolioEntry;
    }

    /**
     * Get the destination portfolio entry.
     */
    public PortfolioEntry getDestinationPortfolioEntry() {
        return this.destinationPortfolioEntry.deleted ? null : this.destinationPortfolioEntry;
    }

    /**
     * Get the dependency type.
     */
    public PortfolioEntryDependencyType getPortfolioEntryDependencyType() {
        return this.portfolioEntryDependencyType.deleted ? null : this.portfolioEntryDependencyType;
    }
}
