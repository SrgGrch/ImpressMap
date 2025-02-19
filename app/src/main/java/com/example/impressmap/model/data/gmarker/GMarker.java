package com.example.impressmap.model.data.gmarker;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.example.impressmap.model.data.GMarkerMetadata;
import com.example.impressmap.model.data.GObject;
import com.example.impressmap.util.Converter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

public abstract class GMarker implements GObject
{
    private final Context context;
    private final Marker marker;
    private final GMarkerMetadata gMarkerMetadata;
    @DrawableRes
    private final int selectedStateResId;
    @DrawableRes
    private final int deselectedStateResId;
    private boolean selected;

    private boolean clickable;

    public GMarker(Context context,
                   @NonNull Marker marker,
                   GMarkerMetadata gMarkerMetadata,
                   @DrawableRes int selectedStateResId,
                   @DrawableRes int deselectedStateResId)
    {
        this.context = context;
        this.marker = marker;
        this.gMarkerMetadata = gMarkerMetadata;
        this.selectedStateResId = selectedStateResId;
        this.deselectedStateResId = deselectedStateResId;

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(
                Converter.drawableIdToBitmap(context, deselectedStateResId)));
        selected = false;
        clickable = false;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        if (!clickable || this.selected == selected)
        {
            return;
        }

        if (marker != null)
        {
            try
            {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(
                        Converter.drawableIdToBitmap(context,
                                selected ? selectedStateResId : deselectedStateResId)));
            }
            catch (IllegalArgumentException ignored)
            {
            }
        }

        this.selected = selected;
    }

    public GMarkerMetadata getGMarkerMetadata()
    {
        return gMarkerMetadata;
    }

    public boolean isClickable()
    {
        return clickable;
    }

    public void setClickable(boolean clickable)
    {
        this.clickable = clickable;
    }

    public void hide()
    {
        marker.setVisible(false);
    }

    public void show()
    {
        marker.setVisible(true);
    }
}
