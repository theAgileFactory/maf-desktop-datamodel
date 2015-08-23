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
package models.reporting;

import java.sql.Timestamp;
import framework.taftree.INodeEntity;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import com.avaje.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Where;

/**
 * Define a category for the reports.
 * 
 * @author Johann Kohler
 * 
 */
@Entity
public class ReportingCategory extends Model implements IModel, INodeEntity<ReportingCategory> {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.MEDIUM_STRING)
    public String name = "";

    public boolean manageable = true;

    @Column(name = "`order`")
    public int order;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    public ReportingCategory parent;

    @OneToMany(mappedBy = "parent")
    @Where(clause = "${ta}.deleted=0")
    public List<ReportingCategory> children;

    @OneToMany(mappedBy = "reportingCategory")
    @Where(clause = "${ta}.deleted=0")
    public List<Reporting> reports;

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    @Override
    public String audit() {
        return "PortfolioEntry [id=" + id + ", name=" + name + ", manageable=" + manageable + ", order=" + order + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public Long getNodeId() {
        return id;
    }

    @Override
    public boolean isNodeDeleted() {
        return deleted;
    }

    @Override
    public void setNodeDeleted(boolean deleted) {
        this.deleted=deleted;
    }

    @Override
    public String getNodeName() {
        return name;
    }

    @Override
    public void setNodeName(String name) {
        this.name=name;
    }

    @Override
    public boolean isNodeManageable() {
        return manageable;
    }

    @Override
    public void setNodeManageable(boolean manageable) {
        this.manageable=manageable;
    }

    @Override
    public ReportingCategory getNodeParent() {
        return parent;
    }

    @Override
    public void setNodeParent(Long parentId) {
        setParentFromId(parentId);
    }

    @Override
    public List<ReportingCategory> getNodeChildren() {
        return children;
    }

    @Override
    public int getNodeOrder() {
        return order;
    }

    @Override
    public void setNodeOrder(int order) {
        this.order=order;
    }
    
    public boolean hasNodeChildren() {
        return this.children != null && this.children.size() > 0;
    }
    
    @Override
    public int getLastNodeChildrenOrder() {
        List<ReportingCategory> categories =
                Ebean.find(ReportingCategory.class).orderBy("order DESC").where().eq("deleted", false).eq("parent.id", this.id).setMaxRows(1).findList();
        if (categories.size() > 0) {
            return categories.get(0).order;
        }
        return 0;
    }

    @Override
    public ReportingCategory getRootNode() {

        if (this.parent == null) {
            return null;
        } else {

            ReportingCategory lastParent = this.parent;
            ReportingCategory rootParent = null;

            while (lastParent != null) {

                if (lastParent.parent == null) {
                    rootParent = lastParent;
                }

                lastParent = lastParent.parent;

            }

            return rootParent;
        }
    }
    
    private void setParentFromId(Long parentId) {
        this.parent = Ebean.find(ReportingCategory.class).where().eq("deleted", false).eq("id", parentId).findUnique();
    }
}
