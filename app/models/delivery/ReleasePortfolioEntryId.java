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

import javax.persistence.Embeddable;

/**
 * Define the id of the association table between a release and a portfolio
 * entry.
 * 
 * @author Johann Kohler
 */
@Embeddable
public class ReleasePortfolioEntryId {

    public Long releaseId;

    public Long portfolioEntryId;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReleasePortfolioEntryId other = (ReleasePortfolioEntryId) obj;
        if ((this.releaseId == null) ? (other.releaseId != null) : !this.releaseId.equals(other.releaseId)) {
            return false;
        }
        if ((this.portfolioEntryId == null) ? (other.portfolioEntryId != null) : !this.portfolioEntryId.equals(other.portfolioEntryId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.releaseId != null ? this.releaseId.hashCode() : 0);
        hash = 89 * hash + (this.portfolioEntryId != null ? this.portfolioEntryId.hashCode() : 0);
        return hash;
    }

}
