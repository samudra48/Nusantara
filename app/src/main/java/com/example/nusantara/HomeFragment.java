package com.example.nusantara;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    FloatingActionButton btnFab;
    FirebaseAuth mAuth;
    List<ItemDashboard> list;
    List<ItemDashboard> sList;

    List<Provinsi> provinsiList;
    List<Provinsi> provinsiFull;

    List<Object> provList;
    List<Object> provFull;

    FirebaseFirestore db;

    private AdapterDashboard mAdapter;
    private RecyclerView mRecycle;
    private RecyclerView rvProv;

    private ProvinsiAdapter provinsiAdapter;
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecycle = view.findViewById(R.id.rv_recycle);
        btnFab = view.findViewById(R.id.fab);
        rvProv = view.findViewById(R.id.rv_prov);

        rvProv.setVisibility(View.GONE);

        provinsiList = new ArrayList<>();
        provinsiFull = new ArrayList<>();

        provFull = new ArrayList<>();
        provList = new ArrayList<>();

        sList = new ArrayList<>();
        list = new ArrayList<>();

        provinsiAdapter = new ProvinsiAdapter(getContext(), provList);
        mAdapter= new AdapterDashboard(list ,getContext());

        rvProv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProv.setAdapter(provinsiAdapter);

        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycle.setAdapter(mAdapter);

        db = FirebaseFirestore.getInstance();
        db.collection("provinsi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful() && task.getResult() != null){
                    for (QueryDocumentSnapshot doc : task.getResult()){
//                        Provinsi prov = doc.toObject(Provinsi.class);
//                        provinsiList.add(prov);
//                        provinsiFull.add(prov);

                        Map<String, Object> provinsiMap = new HashMap<>();
                        provinsiMap.put("nama", doc.get("Nama"));
                        provinsiMap.put("budaya", doc.get("Budaya"));
                        provinsiMap.put("ibukota", doc.get("Ibukota"));

                        provList.add(provinsiMap.get("nama"));
                        provFull.add(provinsiMap.get("nama"));
                    }
                    provinsiAdapter.notifyDataSetChanged();
                }
            }

        });

        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UnggahFoto.class));
            }
        });

        initializeData();
    }

    private void initializeData() {
        String[] rekomendasi = getResources()
                .getStringArray(R.array.rekomendasi_title);
        String[] rekomendasiinfo = getResources()
                .getStringArray(R.array.info_rekomendasi);
        TypedArray gambarrekomendasi = getResources()
                .obtainTypedArray(R.array.gambar_rekomendasi);

        list.clear();

        for (int i = 0; i < rekomendasi.length; i++) {
            list.add(new ItemDashboard(rekomendasi[i], rekomendasiinfo[i],
                    gambarrekomendasi.getResourceId(i, 0)));
            sList.add(new ItemDashboard(rekomendasi[i], rekomendasiinfo[i],
                    gambarrekomendasi.getResourceId(i, 0)));
        }

        gambarrekomendasi.recycle();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.option_search).getActionView();
        searchView.setQueryHint("Pencarian");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (!s.isEmpty()) {
                    mRecycle.setVisibility(View.GONE);
                    rvProv.setVisibility(View.VISIBLE);
                    provList.clear();
                    String search = s.toLowerCase();
                    for (Object item : provFull) {
                        if (item.toString().toLowerCase().contains(search)){
                            provList.add(item);
                        }
                    }
                }else{
                    mRecycle.setVisibility(View.VISIBLE);
                    rvProv.setVisibility(View.GONE);
                    provList.addAll(provFull);
                }
                provinsiAdapter.notifyDataSetChanged();

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_logout:
                mAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
