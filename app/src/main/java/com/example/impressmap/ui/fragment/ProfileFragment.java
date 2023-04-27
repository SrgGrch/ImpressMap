package com.example.impressmap.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.impressmap.R;
import com.example.impressmap.databinding.FragmentProfileBinding;
import com.example.impressmap.ui.viewmodel.MainViewModel;
import com.example.impressmap.ui.viewmodel.ProfileFragmentViewModel;

public class ProfileFragment extends Fragment
{
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        binding.toolbar.setNavigationOnClickListener(
                v -> requireActivity().getSupportFragmentManager().popBackStack());

        binding.settingsAddressesView.setOnClickListener(v ->
        {
            String name = AddressesFragment.class.getSimpleName();
            requireActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.container, AddressesFragment.newInstance())
                             .addToBackStack(name)
                             .commit();
        });

        ProfileFragmentViewModel viewModel = new ViewModelProvider(this).get(
                ProfileFragmentViewModel.class);
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(
                MainViewModel.class);
        viewModel.getUser().observe(getViewLifecycleOwner(), mainViewModel::setUser);

        mainViewModel.getUser().observe(getViewLifecycleOwner(), user ->
        {
            binding.collapsingToolbar.setTitle(user.getFullName());
            binding.phoneNumberView.setText(user.getPhoneNumber() != null ? user.getPhoneNumber()
                                                                          : getText(
                                                                                  R.string.phone_number_not_set));
        });
    }
}
