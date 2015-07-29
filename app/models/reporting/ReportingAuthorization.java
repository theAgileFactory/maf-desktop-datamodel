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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import models.framework_models.account.Principal;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.Where;

/**
 * Define an authorization for a report.
 * 
 * @author Johann Kohler
 * 
 */
@Entity
public class ReportingAuthorization extends Model implements IModel {

    private static final long serialVersionUID = -4426666790914793903L;

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.LARGE_STRING)
    public String expression = "";

    /**
     * The type of the authorization.<br/>
     * 0 for Principal<br/>
     * 1 for SystemLevelRoleType
     */
    public int type = 0;

    @OneToMany(mappedBy = "reportingAuthorization")
    @Where(clause = "${ta}.deleted=0")
    public List<Reporting> reports;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "principal_reporting_authorization")
    @Where(clause = "${ta}.deleted=0")
    public List<Principal> principals;

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    @Override
    public String audit() {
        return "ReportingAuthorization [id=" + id + ", expression=" + expression + ", type=" + type + "]";
    }

    @Override
    public void defaults() {
    }
}
