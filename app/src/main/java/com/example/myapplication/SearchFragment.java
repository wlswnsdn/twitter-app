package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.helper.SearchHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchHelper searchHelper;
    private RecyclerView recyclerView;
    private EditText searchEditText;
    private SearchAdapter adapter;
    private List<SearchItem> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        itemList = new ArrayList<>();

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        searchEditText = view.findViewById(R.id.serachEditText);

        searchHelper = new SearchHelper(requireContext());

        // Set up TextWatcher to listen for changes in the EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 검색창에 문자열이 변하면 계속 문자열로 저장함
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Store the current text in a variable
                String searchText = charSequence.toString();
                itemList = new ArrayList<>();
                SQLiteDatabase db = searchHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * from user", null);
                while(cursor.moveToNext()) {
                    String check = cursor.getString(1);
                    if(check.equals(searchText)) {
                        itemList.add(new SearchItem(R.color.black, check, searchHelper.check(searchText)));
                    }
                }
                adapter.notifyDataSetChanged();
                cursor.close();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                adapter = new SearchAdapter(itemList, requireContext());
                recyclerView.setAdapter(adapter);
            }
        });

        // Initialize adapter
        adapter = new SearchAdapter(itemList, requireContext());
        recyclerView.setAdapter(adapter);
        return view;
    }
}
