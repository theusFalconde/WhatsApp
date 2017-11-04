package com.developmind.whatsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.developmind.whatsapp.fragment.ContatoFragment;
import com.developmind.whatsapp.fragment.ConversaFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloTabs = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ConversaFragment();
                break;
            case 1:
                fragment = new ContatoFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloTabs[position];
    }
}
