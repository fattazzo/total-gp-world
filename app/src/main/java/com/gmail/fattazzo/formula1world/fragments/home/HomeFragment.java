package com.gmail.fattazzo.formula1world.fragments.home;

import android.view.MenuItem;
import android.view.View;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.activity.settings.SettingsActivity_;
import com.gmail.fattazzo.formula1world.fragments.BaseFragment;
import com.gmail.fattazzo.formula1world.fragments.home.circuit.CurrentCircuitTask;
import com.gmail.fattazzo.formula1world.fragments.home.constructorstandings.CurrentConstructorStandingsTask;
import com.gmail.fattazzo.formula1world.fragments.home.driverstandings.CurrentDriverStandingsTask;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.IssueReporter;
import com.gmail.fattazzo.formula1world.utils.Utils;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;

import static com.gmail.fattazzo.formula1world.activity.home.HomeActivity.PREF_ACTIVITY_RESULT;

/**
 * @author fattazzo
 *         <p/>
 *         date: 15/04/17
 */
@OptionsMenu(R.menu.home)
@EFragment(R.layout.content_home)
public class HomeFragment extends BaseFragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Bean
    Utils utils;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @Bean
    CurrentCircuitTask currentCircuitTask;

    @Bean
    CurrentDriverStandingsTask currentDriverStandingsTask;

    @Bean
    CurrentConstructorStandingsTask currentConstructorStandingsTask;

    @Bean
    DataService dataService;

    @AfterViews
    void init() {
        currentCircuitTask.loadCurrentSchedule();
        currentDriverStandingsTask.loadCurrentStandings();
        currentConstructorStandingsTask.loadCurrentStandings();
    }

    @Click
    void fab() {
        dataService.clearColorsCache();

        currentCircuitTask.loadCurrentSchedule(true);
        currentDriverStandingsTask.loadCurrentStandings(true);
        currentConstructorStandingsTask.loadCurrentStandings(true);
    }

    @Override
    public void backPressed() {
        new LovelyStandardDialog(getActivity())
                .setTopColorRes(R.color.colorPrimaryDark)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.app_name)
                .setMessage(R.string.app_exit_comfirmation)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                SettingsActivity_.intent(getContext()).startForResult(PREF_ACTIVITY_RESULT);
                return true;
            case R.id.action_report_bug:
                IssueReporter.openReportIssue(getActivity(), null, null, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
