package com.example.impressmap.ui.fragment.map;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.impressmap.R;
import com.example.impressmap.databinding.NavHeaderMainBinding;
import com.example.impressmap.model.MenuItemMeta;
import com.example.impressmap.ui.fragment.addresses.AddressesFragment;
import com.example.impressmap.ui.fragment.addresses.useraddresses.UserAddressesFragment;
import com.example.impressmap.ui.fragment.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

import java.util.HashMap;
import java.util.Map;

public class NavigationDrawer implements NavigationView.OnNavigationItemSelectedListener
{
    private final int PROFILE_GROUP = 0;
    private final int SETTINGS_GROUP = 1; // не испльзуется
    private final MapFragment fragment;
    private final NavigationView navigationView;
    private final DrawerLayout drawerLayout;
    private final FragmentManager fragmentManager;
    private final Map<Integer, MenuItemMeta> menuItemMeta = new HashMap<>();

    public NavigationDrawer(MapFragment fragment,
                            NavigationView navigationView,
                            DrawerLayout drawerLayout,
                            FragmentManager fragmentManager)
    {
        this.fragment = fragment;
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
        this.fragmentManager = fragmentManager;


        NavHeaderMainBinding navHeaderMainBinding = NavHeaderMainBinding.bind(
                navigationView.getHeaderView(0));
        navHeaderMainBinding.dayNightFab.setOnClickListener(v ->
        {

        });
        initNavigationMenu();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        /*
         * 1. Тут может быть NPE, нужно проверять что значение не null
         * 2. Мне кажется такая реализация излишняя, можно тут поставить просто обычных switch
         * и по R.id.menu_* определять на что кликнули и обрабатывать.
         */
        menuItemMeta.get(item.getItemId()).onClick();
        close();

        return true;
    }

    private void initNavigationMenu()
    {
        Menu menu = navigationView.getMenu();

        int order = 0;

        addNavigationMenuItem(menu, PROFILE_GROUP, order++, R.string.menu_profile,
                new MenuItemMeta(() ->
                {
                    String name = ProfileFragment.class.getSimpleName();
                    fragmentManager.beginTransaction()
                                   .replace(R.id.container, ProfileFragment.newInstance())
                                   .addToBackStack(name)
                                   .commit();
                }), R.drawable.ic_menu_profile);

        addNavigationMenuItem(menu, PROFILE_GROUP, order++, R.string.menu_addresses,
                new MenuItemMeta(() ->
                {
                    String name = UserAddressesFragment.class.getSimpleName();
                    fragmentManager.beginTransaction()
                                   .replace(R.id.container, UserAddressesFragment.newInstance())
                                   .addToBackStack(name)
                                   .commit();
                }), R.drawable.ic_address);

        addNavigationMenuItem(menu, PROFILE_GROUP, order++, R.string.menu_join_address,
                new MenuItemMeta(() ->
                {
                    String name = AddressesFragment.class.getSimpleName();
                    fragmentManager.beginTransaction()
                                   .replace(R.id.container, AddressesFragment.newInstance())
                                   .addToBackStack(name)
                                   .commit();
                }), R.drawable.ic_join_address);
        // the next submenu
        /*Menu submenuSettings = menu.addSubMenu(SETTINGS_GROUP, Menu.NONE, order,
                R.string.nav_header_item_settings);

        addNavigationMenuItem(submenuSettings, SETTINGS_GROUP, ++order, R.string.menu_language,
                new MenuItemMeta(() -> fragmentManager.beginTransaction()
                                                      .replace(R.id.container, new Fragment())
                                                      .addToBackStack("")
                                                      .commit()), R.drawable.ic_menu_international);*/
    }

    private void addNavigationMenuItem(Menu menu,
                                       int groupId,
                                       int order,
                                       @StringRes int title,
                                       MenuItemMeta menuItemMeta,
                                       @DrawableRes int icon)
    {
        addNavigationMenuItem(menu, groupId, order, fragment.getString(title), menuItemMeta, icon);
    }

    private void addNavigationMenuItem(Menu menu,
                                       int groupId,
                                       int order,
                                       CharSequence title,
                                       MenuItemMeta menuItemMeta,
                                       @DrawableRes int icon)
    {
        MenuItem menuItem = menu.add(groupId, order, order, title).setIcon(icon);

        this.menuItemMeta.put(menuItem.getItemId(), menuItemMeta);
    }

    public boolean isOpen()
    {
        return drawerLayout.isDrawerOpen(navigationView);
    }

    public void open()
    {
        drawerLayout.openDrawer(navigationView);
    }

    public boolean isClosed()
    {
        return !drawerLayout.isDrawerOpen(navigationView);
    }

    public void close()
    {
        drawerLayout.closeDrawer(navigationView);
    }
}
