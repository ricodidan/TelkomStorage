/**
 * ownCloud Android client application
 * <p>
 * Copyright (C) 2015 ownCloud Inc.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.telkomsigma.telkomstorage.ui.activity;

import android.accounts.Account;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.telkomsigma.telkomstorage.datamodel.OCFile;
import com.telkomsigma.telkomstorage.ui.fragment.FileFragment;
import com.telkomsigma.telkomstorage.ui.fragment.OCFileListFragment;
import com.telkomsigma.telkomstorage.utils.AnalyticsUtils;

public class UploadPathActivity extends FolderPickerActivity implements FileFragment.ContainerActivity,
        OnClickListener, OnEnforceableRefreshListener {

    public static final String KEY_INSTANT_UPLOAD_PATH = "INSTANT_UPLOAD_PATH";

    private static final String SCREEN_NAME = "Set upload path";

    private static final String TAG = FolderPickerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String instantUploadPath = getIntent().getStringExtra(KEY_INSTANT_UPLOAD_PATH);

        // The caller activity (Preferences) is not a FileActivity, so it has no OCFile, only a path.
        OCFile folder = new OCFile(instantUploadPath);

        setFile(folder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsUtils.setCurrentScreenName(this, SCREEN_NAME, TAG);
    }

    /**
     * Called when the ownCloud {@link Account} associated to the Activity was
     * just updated.
     */
    @Override
    protected void onAccountSet(boolean stateWasRecovered) {
        super.onAccountSet(stateWasRecovered);
        if (getAccount() != null) {

            updateFileFromDB();

            OCFile folder = getFile();
            if (folder == null || !folder.isFolder()) {
                // fall back to root folder
                setFile(getStorageManager().getFileByPath(OCFile.ROOT_PATH));
                folder = getFile();
            }

            onBrowsedDownTo(folder);

            if (!stateWasRecovered) {
                OCFileListFragment listOfFolders = getListOfFilesFragment();
                listOfFolders.listDirectory(folder, false, false);

                startSyncFolderOperation(folder, false);
            }

            updateNavigationElementsInActionBar();
        }
    }
}
