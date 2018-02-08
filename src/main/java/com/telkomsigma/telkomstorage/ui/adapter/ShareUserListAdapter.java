/**
 * ownCloud Android client application
 *
 * @author masensio
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

package com.telkomsigma.telkomstorage.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.owncloud.android.lib.resources.shares.OCShare;
import com.owncloud.android.lib.resources.shares.ShareType;

import java.util.ArrayList;

/**
 * Adapter to show a user/group in Share With List
 */
public class ShareUserListAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<OCShare> mShares;
    private ShareUserAdapterListener mListener;

    public ShareUserListAdapter(Context context, int resource, ArrayList<OCShare> shares,
                                ShareUserAdapterListener listener) {
        super(context, resource);
        mContext = context;
        mShares = shares;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mShares.size();
    }

    @Override
    public Object getItem(int position) {
        return mShares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(com.telkomsigma.telkomstorage.R.layout.share_user_item, parent, false);

        if (mShares != null && mShares.size() > position) {
            OCShare share = mShares.get(position);

            TextView userName = view.findViewById(com.telkomsigma.telkomstorage.R.id.userOrGroupName);
            ImageView iconView = view.findViewById(com.telkomsigma.telkomstorage.R.id.icon);
            final ImageView editShareButton = view.findViewById(com.telkomsigma.telkomstorage.R.id.editShareButton);
            final ImageView unshareButton = view.findViewById(com.telkomsigma.telkomstorage.R.id.unshareButton);

            String name = share.getSharedWithDisplayName();
            Drawable icon = getContext().getResources().getDrawable(com.telkomsigma.telkomstorage.R.drawable.ic_user);
            if (share.getShareType() == ShareType.GROUP) {
                name = getContext().getString(com.telkomsigma.telkomstorage.R.string.share_group_clarification, name);
                icon = getContext().getResources().getDrawable(com.telkomsigma.telkomstorage.R.drawable.ic_group);
            } else if (share.getShareType() == ShareType.EMAIL) {
                name = getContext().getString(com.telkomsigma.telkomstorage.R.string.share_email_clarification, name);
                icon = getContext().getResources().getDrawable(com.telkomsigma.telkomstorage.R.drawable.ic_email);
                editShareButton.setVisibility(View.INVISIBLE);
            }
            userName.setText(name);
            iconView.setImageDrawable(icon);

            /// bind listener to edit privileges
            editShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.editShare(mShares.get(position));
                }
            });

            /// bind listener to unshare
            unshareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.unshareButtonPressed(mShares.get(position));
                }
            });

        }
        return view;
    }

    public interface ShareUserAdapterListener {
        void unshareButtonPressed(OCShare share);

        void editShare(OCShare share);
    }
}
