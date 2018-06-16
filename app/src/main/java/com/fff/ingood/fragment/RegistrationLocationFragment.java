package com.fff.ingood.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationLocationFragment extends BaseFragment {

    private Spinner mSpinnerLocation;

    public static RegistrationLocationFragment newInstance() {
        return new RegistrationLocationFragment();
    }

    public String getLocation() {
        String strLocation = null;

        if(mSpinnerLocation.getSelectedItemPosition() != 0)
            strLocation = mSpinnerLocation.getSelectedItem().toString();
        else
            Toast.makeText(getActivity(), getResources().getText(R.string.register_location_choose), Toast.LENGTH_SHORT).show();

        return strLocation;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_location, container, false);
    }

    @Override
    protected void initView() {
        mSpinnerLocation = Objects.requireNonNull(getActivity()).findViewById(R.id.spinner_location);
    }

    @Override
    protected void initData() {
        String[] arrLocations = getResources().getStringArray(R.array.user_location_list);
        ArrayAdapter<String> spinnerLocationAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, arrLocations);
        spinnerLocationAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerLocation.setAdapter(spinnerLocationAdapter);
    }

    @Override
    protected void initListener() {

    }
}
