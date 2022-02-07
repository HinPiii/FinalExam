package com.vku.lmhiep.finalexam.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vku.lmhiep.finalexam.User.tabProfile.strangerFavFragment;
import com.vku.lmhiep.finalexam.User.tabProfile.strangerPostFragment;

public class AdapterStranger extends FragmentStatePagerAdapter {

    public AdapterStranger(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new strangerFavFragment();
            case 1:
                return new strangerPostFragment();

            default:
                return new strangerFavFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Món ăn yêu thích";
                break;
            case 1:
                title = "Bài đăng";
                break;
        }
        return title;
    }
}
