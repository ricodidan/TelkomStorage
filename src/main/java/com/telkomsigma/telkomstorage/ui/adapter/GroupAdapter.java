/**
 * ownCloud Android client application
 * <p>
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
package com.telkomsigma.telkomstorage.ui.adapter;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter {

    public final List<String> children = new ArrayList<String>();
    public String string;

    public GroupAdapter(String string) {
        this.string = string;
    }

}