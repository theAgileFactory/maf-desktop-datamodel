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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import com.avaje.ebean.Model;
import framework.utils.ISelectableValueHolder;
import framework.utils.Msg;

/**
 * A type for a portfolio entry dependency.
 * 
 * @author Johann Kohler
 */
@Entity
public class PortfolioEntryDependencyType extends Model implements IModel, ISelectableValueHolder<Long> {

    private static final long serialVersionUID = -4541231658489L;

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.LARGE_STRING, nullable = false)
    public String name;

    @Column(length = IModelConstants.LARGE_STRING, nullable = false)
    public String contrary;

    @Column(length = IModelConstants.VLARGE_STRING, nullable = true)
    public String description;

    public boolean isActive;

    @OneToMany(mappedBy = "portfolioEntryDependencyType")
    public List<PortfolioEntryDependency> portfolioEntryDependencies;

    /**
     * Default constructor.
     */
    public PortfolioEntryDependencyType() {
        super();
    }

    @Override
    public String audit() {
        return "PortfolioEntryType [deleted=" + deleted + ", lastUpdate=" + lastUpdate + ", id=" + id + ", name=" + name + ", contrary=" + contrary
                + ", description=" + description + ", isActive=" + isActive;
    }

    @Override
    public String toString() {
        return Msg.get(this.name);
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        this.deleted = true;
        update();
    }

    @Override
    public String getDescription() {
        return Msg.get(this.description);
    }

    @Override
    public String getName() {
        return Msg.get(this.name);
    }

    /**
     * Get the name as a i18n key.
     */
    public String getNameKey() {
        return this.name;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void setUrl(String url) {
    }

    @Override
    public Long getValue() {
        return this.id;
    }

    @Override
    public boolean isSelectable() {
        return this.isActive;
    }

    @Override
    public boolean isDeleted() {
        return this.deleted;
    }

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

}
