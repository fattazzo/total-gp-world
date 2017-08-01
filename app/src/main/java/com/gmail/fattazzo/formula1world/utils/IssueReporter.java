package com.gmail.fattazzo.formula1world.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.R;

import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;

/**
 * @author fattazzo
 *         <p/>
 *         date: 01/08/17
 */
public class IssueReporter {

    private static final String REPORT_LINK = "https://gitreports.com/issue/fattazzo/formula-1-world";

    private static final String QUERY_ISSUE_TITLE = "issue_title";
    private static final String QUERY_ISSUE_DETAILS = "details";

    /**
     * Open report issue page in browser.
     *
     * @param context context
     */
    public static void openReportIssue(Context context, @Nullable String issueTitle, @Nullable String issueDetails, boolean addInfo) {

        if(addInfo) {
            issueDetails = StringUtils.defaultString(issueDetails) + getInfo(context);
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(buildUrl(issueTitle,issueDetails)));
        context.startActivity(i);
    }

    private static String getInfo(Context context) {

        DeviceInfo info = new DeviceInfo(context);
        String bug_report_user_request = context.getResources().getString(R.string.bug_report_user_request);

        return "\n" +
                "---\n" +
                bug_report_user_request +
                "\n" +
                info.toMarkdown();
    }

    private static String buildUrl(@Nullable String issueTitle, @Nullable String issueDetails) {
        try {
            return REPORT_LINK +
                    "?" +
                    QUERY_ISSUE_TITLE +
                    "=" +
                    URLEncoder.encode(StringUtils.defaultString(issueTitle), "UTF-8") +
                    "&" +
                    QUERY_ISSUE_DETAILS +
                    "=" +
                    URLEncoder.encode(StringUtils.defaultString(issueDetails), "UTF-8");
        } catch (Exception e) {
            return REPORT_LINK;
        }
    }


}
