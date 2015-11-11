package com.dizarale.deliverone.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dizarale.deliverone.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CurrentOrderActivityFragment extends Fragment {

    public CurrentOrderActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_order, container, false);
    }
}
