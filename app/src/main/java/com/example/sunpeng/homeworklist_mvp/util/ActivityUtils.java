package com.example.sunpeng.homeworklist_mvp.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by sunpeng on 2017/4/27.
 */

public class ActivityUtils {
    public static void addFragmentToActivity ( FragmentManager fragmentManager,
                                               Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

}
