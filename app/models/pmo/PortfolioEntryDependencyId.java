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

import javax.persistence.Embeddable;

/**
 * Define the id of the association table for the portfolio entry dependencies.
 * 
 * @author Johann Kohler
 */
@Embeddable
public class PortfolioEntryDependencyId {

    public Long sourcePortfolioEntryId;

    public Long destinationPortfolioEntryId;

    public Long portfolioEntryDependencyTypeId;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PortfolioEntryDependencyId other = (PortfolioEntryDependencyId) obj;
        if ((this.sourcePortfolioEntryId == null) ? (other.sourcePortfolioEntryId != null) : !this.sourcePortfolioEntryId
                .equals(other.sourcePortfolioEntryId)) {
            return false;
        }
        if ((this.destinationPortfolioEntryId == null) ? (other.destinationPortfolioEntryId != null) : !this.destinationPortfolioEntryId
                .equals(other.destinationPortfolioEntryId)) {
            return false;
        }
        if ((this.portfolioEntryDependencyTypeId == null) ? (other.portfolioEntryDependencyTypeId != null) : !this.portfolioEntryDependencyTypeId
                .equals(other.portfolioEntryDependencyTypeId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.sourcePortfolioEntryId != null ? this.sourcePortfolioEntryId.hashCode() : 0);
        hash = 89 * hash + (this.destinationPortfolioEntryId != null ? this.destinationPortfolioEntryId.hashCode() : 0);
        hash = 89 * hash + (this.portfolioEntryDependencyTypeId != null ? this.portfolioEntryDependencyTypeId.hashCode() : 0);
        return hash;
    }

}
