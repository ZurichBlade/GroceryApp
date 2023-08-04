package com.berry.groceryapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.berry.groceryapp.CommonUtils;
import com.berry.groceryapp.DataBaseHelper;
import com.berry.groceryapp.databinding.FragmentAddStockBinding;


public class AddStockFragment extends Fragment {

    DataBaseHelper dataBaseHelper;
    private FragmentAddStockBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentAddStockBinding.inflate(inflater, container, false);

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
                String itemName = binding.edtItemName.getText().toString();
                String qtyStock = binding.edtQntyStock.getText().toString();
                String price = binding.edtPrice.getText().toString();
                boolean returnable = binding.rbReturnable.isChecked();


                // Add a new record to the Stock table
                long addNewItem = dataBaseHelper.insertStockItem(itemName, Integer.parseInt(qtyStock), Integer.parseInt(price), returnable);

                if (addNewItem != -1) {
                    // Successfully inserted the record
                    CommonUtils.showMaterialDialog(getContext(), "Item Added Successfully to Stock.", (dialogInterface, i) -> {
                        binding.edtItemName.setText("");
                        binding.edtQntyStock.setText("");
                        binding.edtPrice.setText("");
                    });
                } else {
                    // Failed to insert the record
                }

            }
        });


    }


    protected boolean isValid() {
        String userName = binding.edtItemName.getText().toString();
        String password = binding.edtQntyStock.getText().toString();
        String price = binding.edtPrice.getText().toString();
        if (userName.length() == 0 || password.length() == 0 || price.length() == 0) {
            CommonUtils.showMaterialDialog(getContext(), "You should fill the blanks!", (dialogInterface, i) -> {
            });
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}