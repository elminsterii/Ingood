package com.fff.ingood.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fff.ingood.R;
import com.fff.ingood.adapter.InterestTagListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationInterestFragment extends BaseFragment {

    private RecyclerView mInterestsRecyclerView;
    private InterestTagListAdapter mInterestTagListAdapter;

    public static RegistrationInterestFragment newInstance() {
        return new RegistrationInterestFragment();
    }

    public String getInterests() {
        StringBuilder strInterests = new StringBuilder();

        List<InterestTagListAdapter.InterestData> lsInterestData = mInterestTagListAdapter.getInterestList();

        for(InterestTagListAdapter.InterestData data : lsInterestData) {
            if(data.isSelected())
                strInterests.append(data.getInterestItemTitle()).append(",");
        }

        if(strInterests.length() > 0)
            strInterests.deleteCharAt(strInterests.length() - 1);

        return strInterests.toString();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_interest, container, false);
    }

    @Override
    protected void initView() {
        mInterestsRecyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.interest_list);
    }

    @Override
    protected void initData() {
        List<InterestTagListAdapter.InterestData> lsInterestData = new ArrayList<>();

        InterestTagListAdapter.InterestData interestDataCare = new InterestTagListAdapter.InterestData();
        interestDataCare.setInterestItemIconResId(R.drawable.add_dark);
        interestDataCare.setInterestItemTitle(getResources().getString(R.string.tag_care));
        interestDataCare.setInterestItemDescription(getResources().getString(R.string.tag_care_description));
        lsInterestData.add(interestDataCare);

        InterestTagListAdapter.InterestData interestDataEducation = new InterestTagListAdapter.InterestData();
        interestDataEducation.setInterestItemIconResId(R.drawable.add_dark);
        interestDataEducation.setInterestItemTitle(getResources().getString(R.string.tag_education));
        interestDataEducation.setInterestItemDescription(getResources().getString(R.string.tag_education_description));
        lsInterestData.add(interestDataEducation);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mInterestTagListAdapter = new InterestTagListAdapter(lsInterestData);
        mInterestsRecyclerView.setLayoutManager(mLayoutManager);
        mInterestsRecyclerView.setNestedScrollingEnabled(true);
        mInterestsRecyclerView.setHasFixedSize(true);
        mInterestsRecyclerView.setAdapter(mInterestTagListAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void postInit() {

    }
}
