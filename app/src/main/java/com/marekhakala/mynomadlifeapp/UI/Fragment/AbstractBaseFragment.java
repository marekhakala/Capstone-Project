package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.MyNomadLifeApplication;

import butterknife.ButterKnife;

public abstract class AbstractBaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        setupComponent(MyNomadLifeApplication.get(getActivity()).getComponent());
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
    
    public abstract String getFragmentName();
    protected abstract void setupComponent(AppComponent component);
}