package com.gmail.fattazzo.formula1world.fragments.stats;

import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.appyvet.rangebar.RangeBar;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.activity.settings.SettingsActivity_;
import com.gmail.fattazzo.formula1world.service.StatisticsService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import static com.gmail.fattazzo.formula1world.activity.home.HomeActivity.PREF_ACTIVITY_RESULT;

/**
 * @author fattazzo
 *         <p/>
 *         date: 04/09/17
 */
@OptionsMenu(R.menu.statistics)
@EFragment(R.layout.fragment_statistics)
public abstract class AbstractStatisticsFragment extends Fragment {

    @ViewById
    protected RangeBar seasonsRangeBar;

    @ViewById
    protected Spinner statsSpinner;

    @ViewById
    protected FrameLayout statistics_container;

    @Bean
    protected StatisticsService statisticsService;

    @AfterViews
    protected void init() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(statisticsService.getLastRaceData());
        int year = cal.get(Calendar.YEAR);
        if(year == 1950) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        seasonsRangeBar.setTickEnd(year);
        seasonsRangeBar.setTickStart(1950);
        seasonsRangeBar.setRangeBarEnabled(isMultiSeasons());
        seasonsRangeBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    statsSpinner(true, statsSpinner.getSelectedItemPosition());
                }
                return false;
            }
        });

        String[] arraySpinner = getResources().getStringArray(getStatsListResId());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.f1_simple_item, arraySpinner);
        statsSpinner.setAdapter(adapter);
    }

    @ItemSelect
    public void statsSpinner(boolean selected, int position) {
        Integer seasonStart = Integer.valueOf(seasonsRangeBar.getLeftPinValue());
        Integer seasonEnd = Integer.valueOf(seasonsRangeBar.getRightPinValue());

        showStat(position, seasonStart, seasonEnd, R.id.statistics_container,statistics_container);
    }

    public abstract void showStat(int index, Integer seasonStart, Integer seasonEnd, int containerResId, FrameLayout container);

    /**
     * Multi seasons selection.
     *
     * @return <code>true</code> for multi seasons, <code>false</code> for single season
     */
    public abstract boolean isMultiSeasons();

    /**
     * Array of available statistics.
     *
     * @return res id
     */
    public abstract int getStatsListResId();

    @OptionsItem
    protected void action_settings() {
        SettingsActivity_.intent(getContext()).startForResult(PREF_ACTIVITY_RESULT);
    }
}
