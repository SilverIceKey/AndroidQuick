package com.tckx.tckx_demo.common.base;

import androidx.viewbinding.ViewBinding;

import com.sdwfqin.quicklib.mvvm.BaseMvvmFragment;

public abstract class BaseFragment<V extends ViewBinding, VM extends BaseVM> extends BaseMvvmFragment<V, VM> {
    @Override
    protected void initViewModel() {
        super.initViewModel();
        mVm.setActivity((BaseActivity) getActivity());
        mVm.setFragment(this);
    }
}
