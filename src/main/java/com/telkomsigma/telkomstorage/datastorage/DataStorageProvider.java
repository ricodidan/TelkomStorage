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

import android.os.Build;

import com.telkomsigma.telkomstorage.MainApp;
import com.telkomsigma.telkomstorage.datastorage.providers.EnvironmentStoragePointProvider;
import com.telkomsigma.telkomstorage.datastorage.providers.HardcodedStoragePointProvider;
import com.telkomsigma.telkomstorage.datastorage.providers.IStoragePointProvider;
import com.telkomsigma.telkomstorage.datastorage.providers.MountCommandStoragePointProvider;
import com.telkomsigma.telkomstorage.datastorage.providers.SystemDefaultStoragePointProvider;
import com.telkomsigma.telkomstorage.datastorage.providers.VDCStoragePointProvider;

import java.io.File;
import java.util.Vector;

/**
 * @author Bartosz Przybylski
 */
public class DataStorageProvider {

    private static final Vector<IStoragePointProvider> mStorageProviders = new Vector<>();
    private static final UniqueStorageList mCachedStoragePoints = new UniqueStorageList();
    private static final DataStorageProvider sInstance = new DataStorageProvider() {{
        // There is no system wide way to get usb storage so we need to provide multiple
        // handcrafted ways to add those.
        addStoragePointProvider(new SystemDefaultStoragePointProvider());
        addStoragePointProvider(new EnvironmentStoragePointProvider());
        addStoragePointProvider(new VDCStoragePointProvider());
        addStoragePointProvider(new MountCommandStoragePointProvider());
        addStoragePointProvider(new HardcodedStoragePointProvider());
    }};


    private DataStorageProvider() {
    }

    public static DataStorageProvider getInstance() {
        return sInstance;
    }

    public StoragePoint[] getAvailableStoragePoints() {
        if (mCachedStoragePoints.size() != 0) {
            return mCachedStoragePoints.toArray(new StoragePoint[mCachedStoragePoints.size()]);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (File f : MainApp.getAppContext().getExternalMediaDirs()) {
                if (f != null) {
                    mCachedStoragePoints.add(new StoragePoint(f.getAbsolutePath(), f.getAbsolutePath()));
                }
            }
        } else {
            for (IStoragePointProvider p : mStorageProviders) {
                if (p.canProvideStoragePoints()) {
                    mCachedStoragePoints.addAll(p.getAvailableStoragePoint());
                }
            }
        }

        return mCachedStoragePoints.toArray(new StoragePoint[mCachedStoragePoints.size()]);
    }

    public String getStorageDescriptionByPath(String path) {
        for (StoragePoint s : getAvailableStoragePoints()) {
            if (s.getPath().equals(path)) {
                return s.getDescription();
            }
        }
        // Fallback to just display complete path
        return path;
    }

    public void addStoragePointProvider(IStoragePointProvider provider) {
        mStorageProviders.add(provider);
    }

    public void removeStoragePointProvider(IStoragePointProvider provider) {
        mStorageProviders.remove(provider);
    }
}
