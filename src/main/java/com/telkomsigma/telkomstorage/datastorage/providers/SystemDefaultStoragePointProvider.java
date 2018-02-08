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

package com.telkomsigma.telkomstorage.datastorage.providers;

import com.telkomsigma.telkomstorage.MainApp;
import com.telkomsigma.telkomstorage.datastorage.StoragePoint;

import java.io.File;
import java.util.Vector;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * @author Bartosz Przybylski
 */
public class SystemDefaultStoragePointProvider extends AbstractStoragePointProvider {
    @Override
    public boolean canProvideStoragePoints() {
        return true;
    }

    @Override
    public Vector<StoragePoint> getAvailableStoragePoint() {
        Vector<StoragePoint> result = new Vector<>();

        final String defaultStringDesc = MainApp.getAppContext().getString(com.telkomsigma.telkomstorage.R.string.storage_description_default);
        File path;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            path = MainApp.getAppContext().getExternalMediaDirs()[0];
        } else {
            path = getExternalStorageDirectory();
        }

        if (path != null && path.canWrite()) {
            result.add(new StoragePoint(defaultStringDesc, path.getAbsolutePath()));
        }

        return result;
    }
}
