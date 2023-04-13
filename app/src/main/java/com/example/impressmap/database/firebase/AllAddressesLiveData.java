package com.example.impressmap.database.firebase;

import static com.example.impressmap.util.Constants.DATABASE_REF;
import static com.example.impressmap.util.Constants.Keys.ADDRESSES_NODE;
import static com.example.impressmap.util.Constants.Keys.MAIN_LIST_NODE;
import static com.example.impressmap.util.Constants.UID;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.impressmap.model.data.Address;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllAddressesLiveData extends LiveData<List<Address>>
{
    private final DatabaseReference userAddressesRef;

    private final ValueEventListener listener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot)
        {
            DatabaseReference addressesRef = DATABASE_REF.child(ADDRESSES_NODE);

            List<Address> addresses = new ArrayList<>();
            for (DataSnapshot dataSnapshot : snapshot.getChildren())
            {
                Address value = dataSnapshot.getValue(Address.class);

                addressesRef.child(value.getId())
                            .addListenerForSingleValueEvent(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot)
                                {
                                    Address address = snapshot.getValue(Address.class);
                                    addresses.add(address);
                                    setValue(addresses);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error)
                                {

                                }
                            });
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error)
        {

        }
    };

    public AllAddressesLiveData()
    {
        userAddressesRef = DATABASE_REF.child(MAIN_LIST_NODE).child(UID);
    }

    @Override
    protected void onActive()
    {
        userAddressesRef.addValueEventListener(listener);
        super.onActive();
    }

    @Override
    protected void onInactive()
    {
        userAddressesRef.removeEventListener(listener);
        super.onInactive();
    }
}
