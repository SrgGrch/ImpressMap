package com.example.impressmap.model.data;

import static com.example.impressmap.util.Constants.Keys.CHILD_ID_NODE;
import static com.example.impressmap.util.Constants.Keys.DESC_NODE;
import static com.example.impressmap.util.Constants.Keys.FULL_ADDRESS_NODE;
import static com.example.impressmap.util.Constants.Keys.OWNER_ID_NODE;
import static com.example.impressmap.util.Constants.Keys.USER_IDS_NODE;

import androidx.room.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Address implements TransferableToDatabase
{
    private String id = "";

    private String desc = "";
    private String ownerId = "";
    //    @TypeConverters(StringListConverter.class)
    private List<String> userIds = new ArrayList<>();
    private String country = "";
    private String city = "";
    private String state = "";

    public Map<String, Object> prepareToTransferToDatabase()
    {
        Map<String, Object> data = new HashMap<>();

        data.put(CHILD_ID_NODE, id);
        data.put(DESC_NODE, desc);
        data.put(OWNER_ID_NODE, ownerId);
        data.put(FULL_ADDRESS_NODE,
                String.format("%s %s %s", country, city, state));

        StringBuilder userIdsS = new StringBuilder();
        for (String userId : userIds)
        {
            userIdsS.append(userId);
        }

        data.put(USER_IDS_NODE, userIdsS.toString());

        return data;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public List<String> getUserIds()
    {
        return userIds;
    }

    public void setUserIds(List<String> userIds)
    {
        this.userIds = userIds;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
