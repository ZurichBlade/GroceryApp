package com.berry.groceryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.berry.groceryapp.databinding.FragmentSearchStockBinding;

public class SearchStockFragment extends Fragment {

    DataBaseHelper dataBaseHelper;
    private FragmentSearchStockBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSearchStockBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(getContext());

        binding.btnCancel.setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });


        binding.btnSave.setOnClickListener(view1 -> {
            if (isValid()) {
                String itemCode = binding.edtItemCode.getText().toString();
            }
        });


    }


    protected boolean isValid() {
        String itemCode = binding.edtItemCode.getText().toString();
        if (itemCode.length() == 0) {
            CommonUtils.showMaterialDialog(getContext(), "You should fill the blanks!", (dialogInterface, i) -> {
            });
            return false;
        }
        return true;
    }


}