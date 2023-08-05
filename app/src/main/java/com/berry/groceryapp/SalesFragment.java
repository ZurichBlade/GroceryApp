package com.berry.groceryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.berry.groceryapp.databinding.FragmentSalesBinding;
import com.berry.groceryapp.model.StockItemData;

public class SalesFragment extends Fragment {

    DataBaseHelper dataBaseHelper;
    private FragmentSalesBinding binding;
    private int qtyStock = -1;

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


                checkItemExist(itemCode, customerName, customerEmail, qtySold, dos);

            }

        });

    }


    private void checkItemExist(String itemCode, String customerName, String customerEmail, String qtySold, String dos) {

        StockItemData stockItemData = dataBaseHelper.getStockItemById(Integer.parseInt(itemCode));
        if (stockItemData != null) {
            // The item with the given ID was found, and you can access its properties using stockItem.
            qtyStock = stockItemData.getQtyStock();

            if (qtyStock < 1 || qtyStock < Integer.parseInt(qtySold)) {
                CommonUtils.showMaterialDialog(getContext(), "Not enough quantity in the stock! \n" +
                        "Current Stock: " + qtyStock, (dialogInterface, i) -> {
                });
            } else {
                addSalesData(itemCode, customerName, customerEmail, qtySold, dos);
            }

        } else {
            // The item with the given ID was not found.
            CommonUtils.showMaterialDialog(getContext(), "Item Not Found", (dialogInterface, i) -> {
            });
        }

    }

    private void addSalesData(String itemCode, String customerName, String customerEmail, String qtySold, String dos) {

        //                // Add a new record to the sales table
        long addNewItem = dataBaseHelper.insertSalesItem(Integer.parseInt(itemCode), customerName, customerEmail, Integer.parseInt(qtySold), dos);

        if (addNewItem != -1) {
            // Successfully inserted the record
            CommonUtils.showMaterialDialog(getContext(), "Item Successfully Sold.", (dialogInterface, i) -> {
                binding.edtItemCode.setText("");
                binding.edtCustomerName.setText("");
                binding.edtCustomerEmail.setText("");
                binding.edtQtySold.setText("");
                binding.edtDos.setText("");
            });

            //updating the stock table
            dataBaseHelper.updateSalesQtyStock(Integer.parseInt(itemCode), Integer.parseInt(qtySold));

        } else {
            // Failed to insert the record
        }

    }


    protected boolean isValid() {
        String itemCode = binding.edtItemCode.getText().toString();
        String customerName = binding.edtCustomerName.getText().toString();
        String customerEmail = binding.edtCustomerEmail.getText().toString();
        String qtySold = binding.edtQtySold.getText().toString();
        String dos = binding.edtDos.getText().toString();
        if (itemCode.length() == 0 || customerName.length() == 0 || customerEmail.length() == 0
                || qtySold.length() == 0 || dos.length() == 0) {
            CommonUtils.showMaterialDialog(getContext(), "You should fill all the fields!", (dialogInterface, i) -> {
            });
            return false;
        } else if (Integer.parseInt(qtySold) == 0) {
            CommonUtils.showMaterialDialog(getContext(), "Qty can't be Zero!", (dialogInterface, i) -> {
            });
            return false;
        }
        return true;
    }


}