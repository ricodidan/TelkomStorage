/**
 * Nextcloud Android client application
 *
 * @author Bartosz Przybylski
 * Copyright (C) 2016 Nextcloud
 * Copyright (C) 2016 Bartosz Przybylski
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU AFFERO GENERAL PUBLIC LICENSE
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU AFFERO GENERAL PUBLIC LICENSE for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public
 * License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.telkomsigma.telkomstorage.datastorage;

/**
 * @author Bartosz Przybylski
 */
public class StoragePoint implements Comparable<StoragePoint> {
    private String mDescription;
    private String mPath;

    public StoragePoint(String description, String path) {
        mDescription = description;
        mPath = path;
    }

    public String getPath() {
        return mPath;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public int compareTo(StoragePoint another) {
        return mPath.compareTo(another.getPath());
    }
}
