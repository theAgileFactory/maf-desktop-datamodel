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
package models.architecture;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import com.avaje.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Where;

import framework.taftree.ITafTreeNode;
import framework.utils.ISelectableValueHolder;

/**
 * An application block is a hierarchical representation of IT concepts.
 * 
 * @author Johann Kohler
 */
@Entity
public class ApplicationBlock extends Model implements IModel, ISelectableValueHolder<Long>, ITafTreeNode {

    private static final long serialVersionUID = -5220286436901888446L;

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    public boolean archived;

    @Column(name = "`order`")
    public int order;

    @Column(length = IModelConstants.MEDIUM_STRING)
    public String refId;

    @Column(length = IModelConstants.LARGE_STRING)
    public String name;

    @Column(length = IModelConstants.XLARGE_STRING)
    public String description;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public ApplicationBlock parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy("order")
    @Where(clause = "${ta}.deleted=0")
    public List<ApplicationBlock> children;

    /**
     * Default constructor.
     */
    public ApplicationBlock() {
        super();
    }

    @Override
    public String audit() {
        return "Actor [id=" + id + ", refId=" + refId + ", archived=" + archived + ", name=" + name + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        this.deleted = true;
        save();
    }

    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        ISelectableValueHolder<Long> v = (ISelectableValueHolder<Long>) o;
        return this.getName().compareTo(v.getName());
    }

    @Override
    public String getDescription() {
        return this.description;
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
        return this.deleted;
    }

    @Override
    public boolean isSelectable() {
        return !this.archived;
    }

    @Override
    public void setUrl(String arg0) {
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<? extends ITafTreeNode> getChildren() {
        return Ebean.find(ApplicationBlock.class).orderBy("order").where().eq("deleted", false).eq("archived", false).eq("parent.id", this.id).findList();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public int getLastChildrenOrder() {
        List<ApplicationBlock> blocks =
                Ebean.find(ApplicationBlock.class).orderBy("order DESC").where().eq("deleted", false).eq("parent.id", this.id).setMaxRows(1).findList();
        if (blocks.size() > 0) {
            return blocks.get(0).order;
        }
        return 0;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public boolean hasChildren() {
        return this.getChildren() != null && this.getChildren().size() > 0;
    }

    @Override
    public boolean isManageable() {
        return true;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;

    }

    @Override
    public void setManageable(boolean arg0) {
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public void setOrder(int order) {
        this.order = order;

    }

    @Override
    public ITafTreeNode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Long parentId) {
        this.parent = Ebean.find(ApplicationBlock.class).where().eq("deleted", false).eq("id", parentId).findUnique();
    }
}
