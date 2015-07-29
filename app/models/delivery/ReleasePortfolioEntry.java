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

import models.pmo.PortfolioEntry;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.EnumValue;

import framework.utils.Msg;

/**
 * Define the association table between a release and a portfolio entry.
 * 
 * @author Johann Kohler
 */
@Entity
public class ReleasePortfolioEntry extends Model {

    public static final long serialVersionUID = 4369856314752364L;

    @EmbeddedId
    public ReleasePortfolioEntryId id = new ReleasePortfolioEntryId();

    @ManyToOne(optional = false)
    @JoinColumn(name = "release_id", insertable = false, updatable = false)
    private Release release;

    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_entry_id", insertable = false, updatable = false)
    private PortfolioEntry portfolioEntry;

    public Type type;

    /**
     * Default constructor.
     * 
     * @param release
     *            the release
     * @param portfolioEntry
     *            the portfolio entry
     */
    public ReleasePortfolioEntry(Release release, PortfolioEntry portfolioEntry) {
        this.release = release;
        this.portfolioEntry = portfolioEntry;
        this.id.releaseId = release.id;
        this.id.portfolioEntryId = portfolioEntry.id;
    }

    /**
     * Get the release.
     */
    public Release getRelease() {
        return this.release.deleted ? null : this.release;
    }

    /**
     * Get the portfolio entry.
     */
    public PortfolioEntry getPortfolioEntry() {
        return this.portfolioEntry.deleted ? null : this.portfolioEntry;
    }

    /**
     * The requirements' relation type.
     * 
     * @author Johann Kohler
     * 
     */
    public enum Type {
        @EnumValue("ALL")
        ALL, @EnumValue("BY_ITERATION")
        BY_ITERATION, @EnumValue("BY_REQUIREMENT")
        BY_REQUIREMENT, @EnumValue("NONE")
        NONE;

        /**
         * Get the label.
         */
        public String getLabel() {
            return Msg.get("object.release.portfolio_entry.type." + this.name() + ".label");
        }
    }
}
