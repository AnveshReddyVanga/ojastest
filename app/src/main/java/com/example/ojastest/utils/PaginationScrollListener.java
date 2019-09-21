package com.example.ojastest.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = PaginationScrollListener.class.getSimpleName();


    private int visibleThreshold = 1;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;
    private Boolean isScrolling = true;

    private LinearLayoutManager mLinearLayoutManager;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void setPreviousTotal(int previousTotal) {
        this.previousTotal = previousTotal;
    }

    public void setScrolling(Boolean scrolling) {
        isScrolling = scrolling;
    }

    public void setmLinearLayoutManager(LinearLayoutManager mLinearLayoutManager) {
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(dy < 0) {
            return;
        }

        visibleItemCount = mLinearLayoutManager.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();

        if(dy > 0){
            if(isScrolling) {
                if (totalItemCount > previousTotal) {
                    isScrolling = false;
                    previousTotal = totalItemCount;
                }
            }

            if (!isScrolling && (totalItemCount-visibleItemCount) <= (pastVisibleItems+visibleThreshold)){
                onLoadMore();
                isScrolling = true;
            }
        }
    }

    public abstract void onLoadMore();

}