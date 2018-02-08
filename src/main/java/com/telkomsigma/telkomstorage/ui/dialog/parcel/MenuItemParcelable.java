package com.telkomsigma.telkomstorage.ui.dialog.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.MenuItem;

public class MenuItemParcelable implements Parcelable {
    public static final Parcelable.Creator<MenuItemParcelable> CREATOR =
            new Parcelable.Creator<MenuItemParcelable>() {

                @Override
                public MenuItemParcelable createFromParcel(Parcel source) {
                    return new MenuItemParcelable(source);
                }

                @Override
                public MenuItemParcelable[] newArray(int size) {
                    return new MenuItemParcelable[size];
                }
            };
    int mMenuItemId;
    String mMenuText;

    public MenuItemParcelable() {
    }

    public MenuItemParcelable(MenuItem menuItem) {
        mMenuItemId = menuItem.getItemId();
        mMenuText = menuItem.getTitle().toString();
    }

    public MenuItemParcelable(Parcel read) {
        mMenuItemId = read.readInt();
    }

    public int getMenuItemId() {
        return mMenuItemId;
    }

    public void setMenuItemId(int id) {
        mMenuItemId = id;
    }

    public String getMenuText() {
        return mMenuText;
    }

    public void setMenuText(String mMenuText) {
        this.mMenuText = mMenuText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMenuItemId);
    }
}
