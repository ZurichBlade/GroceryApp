package com.berry.groceryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.berry.groceryapp.databinding.FragmentListStockBinding;

import java.util.ArrayList;
import java.util.List;

public class ListStockFragment extends Fragment {

    DataBaseHelper dataBaseHelper;
    private FragmentListStockBinding binding;
    private MyAdapter adapter;
    private List<String> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListStockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(getContext());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.btnCancel.setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });

        dataList = new ArrayList<>();
        // Add data to the dataList (e.g., from a database or API)
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        // Add more items as needed


        adapter = new MyAdapter(dataList);
        binding.recyclerView.setAdapter(adapter);

    }
}