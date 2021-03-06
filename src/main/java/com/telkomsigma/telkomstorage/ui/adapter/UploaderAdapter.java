/**
 * ownCloud Android client application
 *
 * @author Tobias Kaminsky
 * Copyright (C) 2016 ownCloud Inc.
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

package com.telkomsigma.telkomstorage.ui.adapter;

import android.accounts.Account;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.telkomsigma.telkomstorage.datamodel.FileDataStorageManager;
import com.telkomsigma.telkomstorage.datamodel.OCFile;
import com.telkomsigma.telkomstorage.datamodel.ThumbnailsCacheManager;
import com.telkomsigma.telkomstorage.utils.DisplayUtils;
import com.telkomsigma.telkomstorage.utils.MimeTypeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploaderAdapter extends SimpleAdapter {

    private Context mContext;
    private Account mAccount;
    private FileDataStorageManager mStorageManager;
    private LayoutInflater inflater;

    public UploaderAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from,
                           int[] to, FileDataStorageManager storageManager, Account account) {
        super(context, data, resource, from, to);
        mAccount = account;
        mStorageManager = storageManager;
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(com.telkomsigma.telkomstorage.R.layout.uploader_list_item_layout, null);
        }

        HashMap<String, OCFile> data = (HashMap<String, OCFile>) getItem(position);
        OCFile file = data.get("dirname");

        TextView filename = vi.findViewById(com.telkomsigma.telkomstorage.R.id.filename);
        filename.setText(file.getFileName());

        ImageView fileIcon = vi.findViewById(com.telkomsigma.telkomstorage.R.id.thumbnail);
        fileIcon.setTag(file.getFileId());

        TextView lastModV = vi.findViewById(com.telkomsigma.telkomstorage.R.id.last_mod);
        lastModV.setText(DisplayUtils.getRelativeTimestamp(mContext, file.getModificationTimestamp()));

        TextView fileSizeV = vi.findViewById(com.telkomsigma.telkomstorage.R.id.file_size);
        TextView fileSizeSeparatorV = vi.findViewById(com.telkomsigma.telkomstorage.R.id.file_separator);

        if (!file.isFolder()) {
            fileSizeV.setVisibility(View.VISIBLE);
            fileSizeSeparatorV.setVisibility(View.VISIBLE);
            fileSizeV.setText(DisplayUtils.bytesToHumanReadable(file.getFileLength()));
        } else {
            fileSizeV.setVisibility(View.GONE);
            fileSizeSeparatorV.setVisibility(View.GONE);
        }

        if (file.isFolder()) {
            fileIcon.setImageDrawable(MimeTypeUtil.getFolderTypeIcon(file.isSharedWithMe() ||
                    file.isSharedWithSharee(), file.isSharedViaLink(), mAccount));
        } else {
            // get Thumbnail if file is image
            if (MimeTypeUtil.isImage(file) && file.getRemoteId() != null) {
                // Thumbnail in Cache?
                Bitmap thumbnail = ThumbnailsCacheManager.getBitmapFromDiskCache(
                        String.valueOf(file.getRemoteId())
                );
                if (thumbnail != null && !file.needsUpdateThumbnail()) {
                    fileIcon.setImageBitmap(thumbnail);
                } else {
                    // generate new Thumbnail
                    if (ThumbnailsCacheManager.cancelPotentialThumbnailWork(file, fileIcon)) {
                        final ThumbnailsCacheManager.ThumbnailGenerationTask task =
                                new ThumbnailsCacheManager.ThumbnailGenerationTask(fileIcon, mStorageManager,
                                        mAccount);
                        if (thumbnail == null) {
                            if (MimeTypeUtil.isVideo(file)) {
                                thumbnail = ThumbnailsCacheManager.mDefaultVideo;
                            } else {
                                thumbnail = ThumbnailsCacheManager.mDefaultImg;
                            }
                        }
                        final ThumbnailsCacheManager.AsyncThumbnailDrawable asyncDrawable = new ThumbnailsCacheManager.AsyncThumbnailDrawable(
                                mContext.getResources(),
                                thumbnail,
                                task
                        );
                        fileIcon.setImageDrawable(asyncDrawable);
                        task.execute(new ThumbnailsCacheManager.ThumbnailGenerationTaskObject(file, file.getRemoteId()));
                    }
                }
            } else {
                fileIcon.setImageDrawable(
                        MimeTypeUtil.getFileTypeIcon(file.getMimetype(), file.getFileName(), mAccount)
                );
            }
        }

        return vi;
    }


}
