/**
 * ownCloud Android client application
 *
 * @author LukeOwncloud
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
package com.telkomsigma.telkomstorage.ui.errorhandling;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ErrorShowActivity extends Activity {

    private static final String TAG = ErrorShowActivity.class.getSimpleName();

    TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "ErrorShowActivity was called. See above for StackTrace.");
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(com.telkomsigma.telkomstorage.R.layout.errorhandling_showerror);
        mError = findViewById(com.telkomsigma.telkomstorage.R.id.errorTextView);
        mError.setText(getIntent().getStringExtra("error"));

    }
}