package com.fff.ingood.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fff.ingood.R;
import com.fff.ingood.adapter.RadioListAdapter;
import com.fff.ingood.tools.ParserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationInterestFragment extends BaseFragment {

    private static final String[] DEFAULT_INTERESTS = { "Sport", "Music", "Food", "Book", "Movie", "Culture"};

    private ListView mInterestsListView;
    private RadioListAdapter mRadioListAdapter;

    public static RegistrationInterestFragment newInstance() {
        return new RegistrationInterestFragment();
    }

    public String getInterests() {
        String strInterests;

        List<Boolean> lsRadioStates = mRadioListAdapter.getRadioStateList();
        List<String> lsInterests = new ArrayList<>();

        for(int i = 0; i < lsRadioStates.size(); i++){
            if(lsRadioStates.get(i))
                lsInterests.add(DEFAULT_INTERESTS[i]);
        }

        strInterests = ParserUtils.listStringToString(lsInterests, ',');

        return strInterests;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_interest, container, false);
    }

    @Override
    protected void initView() {
        mInterestsListView = Objects.requireNonNull(getActivity()).findViewById(R.id.interest_list);
    }

    @Override
    protected void initData() {
        ArrayList<Boolean> radioStateList = new ArrayList<>();
        for (String ignored : DEFAULT_INTERESTS)
            radioStateList.add(false);

        mRadioListAdapter = new RadioListAdapter(getActivity(), DEFAULT_INTERESTS, radioStateList);
        mInterestsListView.setAdapter(mRadioListAdapter);
    }

    @Override
    protected void initListener() {

    }
}
