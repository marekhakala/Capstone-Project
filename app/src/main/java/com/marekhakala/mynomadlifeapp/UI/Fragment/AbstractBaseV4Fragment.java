package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.MyNomadLifeApplication;

import butterknife.ButterKnife;

public abstract class AbstractBaseV4Fragment extends Fragment {
    protected Toast mToast;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public abstract String getFragmentName();
    protected abstract void setupComponent(AppComponent component);

    protected void clearToast() {
        if (mToast != null)
            mToast.cancel();
    }

    protected void showToast(int resourceId) {
        clearToast();
        mToast = Toast.makeText(getActivity(), resourceId, Toast.LENGTH_SHORT);
        mToast.show();
    }
}