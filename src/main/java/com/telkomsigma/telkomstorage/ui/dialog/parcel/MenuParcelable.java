package com.telkomsigma.telkomstorage.ui.dialog.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MenuParcelable implements Parcelable {

    public static final Parcelable.Creator<MenuParcelable> CREATOR = new Parcelable.Creator<MenuParcelable>() {

        @Override
        public MenuParcelable createFromParcel(Parcel in) {
            return new MenuParcelable(in);
        }

        @Override
        public MenuParcelable[] newArray(int size) {
            return new MenuParcelable[size];
        }
    };
    private List<MenuItemParcelable> mMenuItems = new ArrayList<MenuItemParcelable>();

    public MenuParcelable() {
        mMenuItems = new ArrayList<MenuItemParcelable>();
    }

    public MenuParcelable(Parcel in) {
        in.readTypedList(mMenuItems, MenuItemParcelable.CREATOR);
    }

    public List<MenuItemParcelable> getMenuItems() {
        return mMenuItems;
    }

    public void setMenuItems(List<MenuItemParcelable> menuItems) {
        this.mMenuItems = menuItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel outParcel, int flags) {
        outParcel.writeTypedList(mMenuItems);
    }
}
