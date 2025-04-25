package nathja.finalproject.fragment_tablayout_viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nathja.finalproject.fragment_tablayout_viewpager2.databinding.FragmentNewOrderBinding;

public class FragmentNewOrder extends Fragment {
    FragmentNewOrderBinding binding;

    public FragmentNewOrder() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
        ,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentNewOrderBinding.inflate(inflater, container,  false);

        //recyclerView
        return binding.getRoot();

    }
}







