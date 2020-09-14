package com.chinachino.mvvm.Adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.chinachino.mvvm.models.Result;

import java.util.List;

public class MyDiffUtils extends DiffUtil.Callback {
    private List<Result> OldResult;
    private List<Result> NewResult;

    public MyDiffUtils(List<Result> oldResult, List<Result> newResult) {
        OldResult = oldResult;
        NewResult = newResult;
    }

    @Override
    public int getOldListSize() {
        return OldResult.size();
    }

    @Override
    public int getNewListSize() {
        return NewResult.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return OldResult.get(i) == NewResult.get(i1);
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return OldResult.get(i).equals(NewResult.get(i1));
    }
}
