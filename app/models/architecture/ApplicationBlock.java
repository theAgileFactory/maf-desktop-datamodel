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

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Where;

import framework.taftree.INodeEntity;
import framework.utils.ISelectableValueHolder;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;

/**
 * An application block is a hierarchical representation of IT concepts.
 * 
 * @author Johann Kohler
 */
@Entity
public class ApplicationBlock extends Model implements IModel, ISelectableValueHolder<Long>, INodeEntity<ApplicationBlock> {

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
    public int getLastNodeChildrenOrder() {
        List<ApplicationBlock> blocks =
                Ebean.find(ApplicationBlock.class).orderBy("order DESC").where().eq("deleted", false).eq("parent.id", this.id).setMaxRows(1).findList();
        if (blocks.size() > 0) {
            return blocks.get(0).order;
        }
        return 0;
    }

    @Override
    public List<ApplicationBlock> getNodeChildren() {
        return Ebean.find(ApplicationBlock.class).orderBy("order").where().eq("deleted", false).eq("archived", false).eq("parent.id", this.id).findList();
    }

    @Override
    public Long getNodeId() {
        return id;
    }

    @Override
    public String getNodeName() {
        return name;
    }

    @Override
    public int getNodeOrder() {
        return order;
    }

    @Override
    public ApplicationBlock getNodeParent() {
        return parent;
    }

    @Override
    public ApplicationBlock getRootNode() {
        if (this.parent == null) {
            return null;
        } else {

            ApplicationBlock lastParent = this.parent;
            ApplicationBlock rootParent = null;

            while (lastParent != null) {

                if (lastParent.parent == null) {
                    rootParent = lastParent;
                }

                lastParent = lastParent.parent;

            }

            return rootParent;
        }
    }

    @Override
    public boolean hasNodeChildren() {
        return this.children != null && this.children.size() > 0;
    }

    @Override
    public boolean isNodeDeleted() {
        return deleted;
    }

    @Override
    public boolean isNodeManageable() {
        return true;
    }

    @Override
    public void setNodeDeleted(boolean deleted) {
        this.deleted=deleted;
    }

    @Override
    public void setNodeManageable(boolean arg0) {
        
    }

    @Override
    public void setNodeName(String name) {
        this.name=name;
    }

    @Override
    public void setNodeOrder(int order) {
        this.order=order;
    }

    @Override
    public void setNodeParent(Long parentId) {
        this.parent = Ebean.find(ApplicationBlock.class).where().eq("deleted", false).eq("id", parentId).findUnique();
    }
}
