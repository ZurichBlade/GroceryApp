package com.berry.groceryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.berry.groceryapp.databinding.FragmentSalesBinding;

public class SalesFragment extends Fragment {

    DataBaseHelper dataBaseHelper;
    private FragmentSalesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSalesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(getContext());

        binding.btnCancel.setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });

        binding.edtDos.setOnClickListener(view1 -> {
            binding.edtDos.setOnClickListener(v -> CommonUtils.openDatePicker(binding.edtDos, requireActivity().getSupportFragmentManager()));
        });

        binding.btnSave.setOnClickListener(view1 -> {
            if (isValid()) {
                String itemCode = binding.edtItemCode.getText().toString();
                String customerName = binding.edtCustomerName.getText().toString();
                String customerEmail = binding.edtCustomerEmail.getText().toString();
                String qtySold = binding.edtQtySold.getText().toString();
                String dos = binding.edtDos.getText().toString();

                // Add a new record to the sales table
                long addNewItem = dataBaseHelper.insertSalesItem(0, Integer.parseInt(itemCode), customerName, customerEmail, Integer.parseInt(qtySold), dos);

                if (addNewItem != -1) {
                    // Successfully inserted the record
                    CommonUtils.showMaterialDialog(getContext(), "Item Added Successfully to Sales.", (dialogInterface, i) -> {
                        binding.edtItemCode.setText("");
                        binding.edtCustomerName.setText("");
                        binding.edtCustomerEmail.setText("");
                        binding.edtQtySold.setText("");
                        binding.edtDos.setText("");
                    });
                } else {
                    // Failed to insert the record
                }

            }

        });

    }


    protected boolean isValid() {
        String itemCode = binding.edtItemCode.getText().toString();
        String customerName = binding.edtCustomerName.getText().toString();
        String customerEmail = binding.edtCustomerEmail.getText().toString();
        String qtySold = binding.edtQtySold.getText().toString();
        String dos = binding.edtDos.getText().toString();
        if (itemCode.length() == 0 || customerName.length() == 0 || customerEmail.length() == 0
                || qtySold.length() == 0 || dos.length() == 0) {
            CommonUtils.showMaterialDialog(getContext(), "You should fill all the blanks!", (dialogInterface, i) -> {
            });
            return false;
        }
        return true;
    }


}