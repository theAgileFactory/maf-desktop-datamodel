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
package models.api.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    /**
     * 
     * @param date
     * @return date to Json ISO8601 format with UTC. mean :
     *         "yyyy-mm-dd'T'hh:mm:ss'Z'"
     */
    public static String returnDateToJSonISO8601UTCFormat(
            Date date) {
        DateFormat df = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'");
        return df.format(date);
    }

}
