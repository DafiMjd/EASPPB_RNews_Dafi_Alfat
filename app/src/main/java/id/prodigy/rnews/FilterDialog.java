package id.prodigy.rnews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.prodigy.rnews.filters.Category;
import id.prodigy.rnews.filters.Country;

public class FilterDialog extends AppCompatDialogFragment {

    TextInputLayout countrySpinner;
    AutoCompleteTextView actCountry;
    ArrayAdapter<Country> listCountryAdapter;
    TextInputLayout categorySpinner;
    AutoCompleteTextView actCategory;
    ArrayAdapter<Category> listCategoryAdapter;

    private ArrayList<Country> listCountry = new ArrayList<>();
    private ArrayList<Category> listCategory = new ArrayList<>();

    private FilterDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.filter_dialog, null);

        setComponentsID(view);
        layoutInflater();

        builder.setView(view)
                .setTitle("Filters")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String,String> filter = new HashMap<>();
                        String country = actCountry.getText().toString();
                        country.trim();
                        for (Country c : listCountry) {
                            if (c.getName().equals(country)) {
                                filter.put("country", c.getId());
                                break;
                            }
                        }

                        String category = actCategory.getText().toString();
                        category.trim();
                        for (Category c : listCategory) {
                            if (c.getName().equals(category)) {
                                filter.put("category", c.getId());

                                break;
                            }
                        }

                        listener.applyFilter(filter);
                    }
                });
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (FilterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement FilterDialogListener");
        }
    }

    public interface FilterDialogListener{
        void applyFilter(Map<String, String> filter);
    }

    private void setComponentsID(View view) {

        countrySpinner = view.findViewById(R.id.countrySpinner);
        actCountry = view.findViewById(R.id.actCountry);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        actCategory = view.findViewById(R.id.actCategory);

        listCountry.add(new Country("id", "Indonesia"));
        listCountry.add(new Country("us", "USA"));

        listCategory.add(new Category("general", "General"));
        listCategory.add(new Category("business", "Business"));
        listCategory.add(new Category("entertainment", "Entertainment"));
    }

    private void layoutInflater() {
        listCountryAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.tv_entity, listCountry);
        listCategoryAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.tv_entity, listCategory);

        actCountry.setAdapter(listCountryAdapter);
        actCategory.setAdapter(listCategoryAdapter);

        actCountry.setThreshold(1);
        actCategory.setThreshold(1);
    }
}
