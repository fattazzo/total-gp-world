package com.gmail.fattazzo.formula1world.fragments.stats.drivers;

import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.appyvet.rangebar.RangeBar;
import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.service.StatisticsService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import static com.dspot.declex.Action.$DriversWinsStatsFragment;
import static com.dspot.declex.Action.$HomeFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/08/17
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsDriversFragment extends Fragment {

    public static final String TAG = StatisticsDriversFragment.class.getSimpleName();

    @ViewById
    RangeBar seasonsRangeBar;

    @ViewById
    Spinner statsSpinner;

    @ViewById
    FrameLayout statistics_container;

    @Bean
    StatisticsService statisticsService;

    @AfterViews
    void init() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(statisticsService.getLastRaceData());
        int year = cal.get(Calendar.YEAR);
        seasonsRangeBar.setTickEnd(year);
        seasonsRangeBar.setTickStart(1950);
        seasonsRangeBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    statsSpinner(true,statsSpinner.getSelectedItemPosition());
                }
                return false;
            }
        });

        String[] arraySpinner = getResources().getStringArray(R.array.statistics_drivers_entry);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.f1_simple_item, arraySpinner);
        statsSpinner.setAdapter(adapter);
    }

    @ItemSelect
    public void statsSpinner(boolean selected, int position) {
        Integer seasonStart = Integer.valueOf(seasonsRangeBar.getLeftPinValue());
        Integer seasonEnd = Integer.valueOf(seasonsRangeBar.getRightPinValue());
        switch (position) {
            case 1:
                $DriversWinsStatsFragment().seasonStart(seasonStart).seasonEnd(seasonEnd).container(R.id.statistics_container);
                break;
            default:
                statistics_container.removeAllViews();
                break;
        }
    }

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
