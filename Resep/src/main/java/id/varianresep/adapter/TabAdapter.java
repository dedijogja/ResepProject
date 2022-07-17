package id.varianresep.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> daftarFragment = new ArrayList<>();
    private final List<String> daftarJudulTab = new ArrayList<>();

    public TabAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return daftarFragment.get(position);
    }

    @Override
    public int getCount() {
        return daftarFragment.size();
    }

    public void addFragment(Fragment fragment, String title) {
        daftarFragment.add(fragment);
        daftarJudulTab.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return daftarJudulTab.get(position);
    }
}