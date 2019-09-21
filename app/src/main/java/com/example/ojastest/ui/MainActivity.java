package com.example.ojastest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.ojastest.utils.PaginationScrollListener;
import com.example.ojastest.R;
import com.example.ojastest.databinding.ActivityMainBinding;
import com.example.ojastest.dtos.Hit;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainViewModel mainViewModel;
    PaginationScrollListener scrollListener;
    private int pageNumber = 1;
    private int selectCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setViewModel(mainViewModel);
        binding.setAdapter(new OjasAdapter());

        scrollListener = new PaginationScrollListener((LinearLayoutManager) binding.recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                if (binding.getAdapter().getItemCount() - 1 < mainViewModel.totalSize) {
                    pageNumber++;
                    mainViewModel.getMoreOjas(pageNumber);
                }

            }
        };

        setTitle("SelectCount:" +selectCount);

        binding.recyclerView.addOnScrollListener(scrollListener);
        mainViewModel.listMutableLiveData.observe(this, new Observer<List<Hit>>() {
            @Override
            public void onChanged(List<Hit> ojas) {
                binding.swipe.setRefreshing(false);
                binding.getAdapter().addAll(ojas);
            }
        });

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.totalSize = 0;
                setInitialValuePagination();
                mainViewModel.getOjas();
            }
        });
    }

    public void setInitialValuePagination() {
        pageNumber = 1;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)binding.recyclerView.getLayoutManager();
        scrollListener.setmLinearLayoutManager(linearLayoutManager);
        scrollListener.setPreviousTotal(0);
        scrollListener.setScrolling(true);
//        getViewModel().fulfilledDonationsAdapter.setIsScrolling(false);
        binding.getAdapter().clear();
//        getViewModel().inProgressDonationsAdapter.setTotalSize(0);
    }

}
