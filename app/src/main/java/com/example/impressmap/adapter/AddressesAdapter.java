package com.example.impressmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.impressmap.R;
import com.example.impressmap.databinding.ItemAddressBinding;
import com.example.impressmap.model.data.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.AddressViewHolder>
{
    private final Context context;
    private OnAddressClickListener onAddressClickListener;

    private List<Address> addressList;

    public AddressesAdapter(Context context)
    {
        this.context = context;
        addressList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType)
    {
        return new AddressViewHolder(
                ItemAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder,
                                 int position)
    {
        Address address = addressList.get(position);
        holder.binding.addressPrimaryView.setText(address.getCountry());
        holder.binding.addressSecondaryView.setText(address.getCity());

        if (address.isSelected())
        {
            holder.binding.getRoot().setBackgroundColor(context.getColor(R.color.gray));
        }
        else
        {
            holder.binding.getRoot().setBackground(context.getDrawable(R.drawable.ripple_effect));
        }

        holder.binding.getRoot().setOnClickListener(v ->
        {
            onAddressClicked(address);
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    public void setAddressList(List<Address> addressList)
    {
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    private void onAddressClicked(Address address)
    {
        onAddressClickListener.onAddressClick(address);
    }

    @Override
    public int getItemCount()
    {
        return addressList.size();
    }

    public void setOnAddressClickListener(OnAddressClickListener listener)
    {
        onAddressClickListener = listener;
    }

    public interface OnAddressClickListener
    {
        void onAddressClick(Address address);
    }

    protected static class AddressViewHolder extends RecyclerView.ViewHolder
    {
        private final ItemAddressBinding binding;

        public AddressViewHolder(@NonNull ItemAddressBinding addressBinding)
        {
            super(addressBinding.getRoot());
            binding = addressBinding;
        }
    }
}
