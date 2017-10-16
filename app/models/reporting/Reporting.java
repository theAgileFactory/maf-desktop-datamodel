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
import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.avaje.ebean.Model;

import framework.services.configuration.II18nMessagesPlugin;
import framework.services.configuration.Language;
import framework.utils.DefaultSelectableValueHolder;
import framework.utils.DefaultSelectableValueHolderCollection;
import framework.utils.Msg;
import models.framework_models.parent.IModel;
import models.framework_models.parent.IModelConstants;
import play.Logger;

/**
 * Define a report.
 * 
 * @author Johann Kohler
 * 
 */
@Entity
public class Reporting extends Model implements IModel {

    public static Finder<Long, Reporting> find = new Finder<>(Reporting.class);

    @Id
    public Long id;

    public boolean deleted = false;

    @Version
    public Timestamp lastUpdate;

    @Column(length = IModelConstants.MEDIUM_STRING)
    public String name;

    @Column(length = IModelConstants.LARGE_STRING)
    public String description;

    public boolean isPublic = false;

    public boolean isActive = true;

    public boolean isStandard = true;

    @Column(length = IModelConstants.MEDIUM_STRING)
    public String template;

    @Column(length = IModelConstants.LARGE_STRING)
    public String languages;

    @Column(length = IModelConstants.LARGE_STRING)
    public String formats;
    
    @Column(length = IModelConstants.LARGE_STRING)
    public String defaultFormat;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public ReportingCategory reportingCategory;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public ReportingAuthorization reportingAuthorization;

    @Override
    public void doDelete() {
        deleted = true;
        save();
    }

    @Override
    public String audit() {
        return "PortfolioEntry [id=" + id + ", name=" + name + ", description=" + description + ", isPublic=" + isPublic + ", isActive=" + isActive
                + ", isStandard=" + isStandard + ", template=" + template + "]";
    }

    @Override
    public void defaults() {
    }

    /**
     * Get the description.
     */
    public String getDescription() {
        return Msg.get(this.description);
    }

    /**
     * Get the name.
     */
    public String getName() {
        return Msg.get(this.name);
    }

    public String getDefaultFormat()
    {
    	return this.defaultFormat;
    }
    
    public DefaultSelectableValueHolderCollection<String> getLanguagesAsVHC(II18nMessagesPlugin i18nMessagesPlugin) {
        DefaultSelectableValueHolderCollection<String> vhc = new DefaultSelectableValueHolderCollection<>();
        for (String code : this.languages.split(",")) {
            Language language = i18nMessagesPlugin.getLanguageByCode(code);
            if (language != null) {
                vhc.add(new DefaultSelectableValueHolder<>(code, language.getName()));
            }
        }
        return vhc;
    }

    public DefaultSelectableValueHolderCollection<String> getFormatsAsVHC() {
        DefaultSelectableValueHolderCollection<String> vhc = new DefaultSelectableValueHolderCollection<>();
        for (String code : this.formats.split(",")) {
            try {
                Format format = Format.valueOf(code);
                if (format != null) {
                    vhc.add(new DefaultSelectableValueHolder<>(code, format.getLabel()));
                }
            } catch (Exception e) {
                Logger.debug("impossible to find the report format " + code);
            }
        }
        return vhc;
    }

    /**
     * Available formats.
     * 
     * @author Johann Kohler
     */
    public enum Format {

        PDF("PDF"), EXCEL("Excel"), CSV("CSV"), WORD("Word"), RTF("RTF"),POWER_POINT("PowerPoint");

        String label;

        /**
         * Default constructor.
         * 
         * @param label
         *            the format label
         */
        Format(String label) {
            this.label = label;
        }

        /**
         * Get the format label.
         */
        String getLabel() {
            return this.label;
        }

        /**
         * Get a value holder collection of all available formats.
         */
        public static DefaultSelectableValueHolderCollection<String> getValueHolderCollection() {
            DefaultSelectableValueHolderCollection<String> vhc = new DefaultSelectableValueHolderCollection<>();
            for (Format format : Arrays.asList(Format.values())) {
                vhc.add(new DefaultSelectableValueHolder<>(format.name(), format.getLabel()));
            }
            return vhc;
        }        
    }

}
