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
package models.datasyndication;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.Model;

import models.framework_models.parent.IModel;

/**
 * A data syndication is a data the has been provided by another instance.
 * 
 * @author Johann Kohler
 */
@Entity
public class DataSyndication extends Model implements IModel {

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    public Long dataSyndicationAgreementLinkId;

    public Long dataSyndicationAgreementItemId;

    public String data;

    /**
     * Default constructor.
     */
    public DataSyndication() {
        super();
    }

    @Override
    public String audit() {
        return "Actor [id=" + id + ", dataSyndicationAgreementLinkId=" + dataSyndicationAgreementLinkId + ", dataSyndicationAgreementItemId="
                + dataSyndicationAgreementItemId + "]";
    }

    @Override
    public void defaults() {
    }

    @Override
    public void doDelete() {
        this.deleted = true;
        save();
    }
}
