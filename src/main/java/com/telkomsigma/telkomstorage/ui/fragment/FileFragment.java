/**
 * ownCloud Android client application
 *
 * @author David A. Velasco
 * Copyright (C) 2015  ownCloud Inc.
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

package com.telkomsigma.telkomstorage.ui.fragment;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.telkomsigma.telkomstorage.datamodel.OCFile;
import com.telkomsigma.telkomstorage.files.services.FileDownloader;
import com.telkomsigma.telkomstorage.files.services.FileUploader;
import com.telkomsigma.telkomstorage.ui.activity.ComponentsGetter;
import com.telkomsigma.telkomstorage.ui.activity.FileActivity;


/**
 * Common methods for {@link Fragment}s containing {@link OCFile}s
 */
public class FileFragment extends Fragment {

    protected ContainerActivity mContainerActivity;
    private OCFile mFile;


    /**
     * Creates an empty fragment.
     *
     * It's necessary to keep a public constructor without parameters; the system uses it when
     * tries to reinstantiate a fragment automatically.
     */
    public FileFragment() {
        mFile = null;
    }

    /**
     * Creates an instance for a given {@OCFile}.
     *
     * @param file
     */
    public static FileFragment newInstance(OCFile file) {
        FileFragment fileFragment = new FileFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable(FileActivity.EXTRA_FILE, file);
        fileFragment.setArguments(bundle);

        return fileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        setFile(bundle.getParcelable(FileActivity.EXTRA_FILE));
    }

    /**
     * Getter for the hold {@link OCFile}
     *
     * @return The {@link OCFile} hold
     */
    public OCFile getFile() {
        return mFile;
    }


    protected void setFile(OCFile file) {
        mFile = file;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mContainerActivity = (ContainerActivity) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    ContainerActivity.class.getSimpleName());
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        mContainerActivity = null;
        super.onDetach();
    }


    /**
     * Interface to implement by any Activity that includes some instance of FileListFragment
     * Interface to implement by any Activity that includes some instance of FileFragment
     */
    public interface ContainerActivity extends ComponentsGetter {

        /**
         * Request the parent activity to show the details of an {@link OCFile}.
         *
         * @param file      File to show details
         */
        void showDetails(OCFile file);


        ///// TO UNIFY IN A SINGLE CALLBACK METHOD - EVENT NOTIFICATIONs  -> something happened
        // inside the fragment, MAYBE activity is interested --> unify in notification method

        /**
         * Callback method invoked when a the user browsed into a different folder through the
         * list of files
         *
         * @param folder
         */
        void onBrowsedDownTo(OCFile folder);

        /**
         * Callback method invoked when a the 'transfer state' of a file changes.
         *
         * This happens when a download or upload is started or ended for a file.
         *
         * This method is necessary by now to update the user interface of the double-pane layout
         * in tablets because methods {@link FileDownloader.FileDownloaderBinder#isDownloading(Account, OCFile)}
         * and {@link FileUploader.FileUploaderBinder#isUploading(Account, OCFile)}
         * won't provide the needed response before the method where this is called finishes. 
         *
         * TODO Remove this when the transfer state of a file is kept in the database
         * (other thing TODO)
         *
         * @param file          OCFile which state changed.
         * @param downloading   Flag signaling if the file is now downloading.
         * @param uploading     Flag signaling if the file is now uploading.
         */
        void onTransferStateChanged(OCFile file, boolean downloading, boolean uploading);

    }

}
