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
package com.telkomsigma.telkomstorage.ui.dialog;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telkomsigma.telkomstorage.R;
import com.telkomsigma.telkomstorage.utils.ThemeUtils;

public class LoadingDialog extends DialogFragment {

    private String mMessage;

    public LoadingDialog() {
        super();
    }

    public static LoadingDialog newInstance(String message) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.mMessage = message;
        return loadingDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create a view by inflating desired layout
        View v = inflater.inflate(R.layout.loading_dialog, container, false);

        // set value
        TextView tv = v.findViewById(R.id.loadingText);
        tv.setText(mMessage);

        // set progress wheel color
        ProgressBar progressBar = v.findViewById(R.id.loadingBar);
        progressBar.getIndeterminateDrawable().setColorFilter(
                ThemeUtils.primaryAccentColor(), PorterDuff.Mode.SRC_IN);

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }
}
