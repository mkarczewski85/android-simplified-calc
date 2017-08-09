package com.karczewski.calculator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


/**
 * Klasa DisplayFragment
 */


public class DisplayFragment extends Fragment {

    @BindView(R.id.lbl_display)
    TextView display;

    @OnClick(R.id.imb_delete)
    public void onDeleteShortClick(View v){

    }

    @OnLongClick(R.id.imb_delete)
    public void onDeleteLongClick(View v){

    }

    public DisplayFragment() {

    }

    public DisplayFragment newInstance(){
        return new DisplayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_display, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

}
