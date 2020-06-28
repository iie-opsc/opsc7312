package za.ac.iie.opsc.geoweather.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import za.ac.iie.opsc.geoweather.CurrentWeatherFragment;
import za.ac.iie.opsc.geoweather.DailyForecastsFragment;
import za.ac.iie.opsc.geoweather.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    // Using string resources, add the three tabs here
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1,
            R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private final String locationName;
    private final String locationKey;

    public SectionsPagerAdapter(Context context, FragmentManager fm,
                                String locationName, String locationKey) {
        super(fm);
        mContext = context;
        this.locationName = locationName;
        this.locationKey = locationKey;
    }

    /**
     * Get the fragment to use for each position.
     * 0 = Today's Weather
     * 1 = Five Day Forecast
     * 2 = City Weather
     * @param position
     * @return The fragment to display in each position.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new CurrentWeatherFragment();
            case 1:
                return new DailyForecastsFragment();
            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages
        return 3;
    }
}