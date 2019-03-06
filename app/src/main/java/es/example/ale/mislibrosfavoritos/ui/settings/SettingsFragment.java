package es.example.ale.mislibrosfavoritos.ui.settings;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceFragmentCompat;
import es.example.ale.mislibrosfavoritos.MainActivityViewModel;
import es.example.ale.mislibrosfavoritos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private MainActivityViewModel viewModelActivity;

    private final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = this;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(TextUtils.equals(getString(R.string.switchPref_key),key)) {
            Boolean habilitado = sharedPreferences.getBoolean(getString(R.string.switchPref_key), false);
            viewModelActivity.setDeshacer(habilitado);
        }
        else if(TextUtils.equals(getString(R.string.prefCheck_key),key)){
            Boolean chequeado = sharedPreferences.getBoolean(getString(R.string.prefCheck_key),false);
            viewModelActivity.setChequear(chequeado);
        }
    }
}
