package com.roadstar.driverr.common.utils;

import android.content.Context;

public class JobUtils {
    public static final String STATUS_ACCEPTED = "Accepted";
    public static final String STATUS_ON_WAY = "On The Way";
    public static final String STATUS_ARRIVED = "Arrived";
    public static final String STATUS_ONGOING = "On Going";
    public static final String STATUS_FINISHED = "Finished";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_REMOVED = "Removed By User";
    public static final String STATUS_CANCEL_USER = "Cancelled By User";
    public static final String STATUS_CANCEL_MOVER = "Cancelled By Mover";

    public static String getButtonText(String status, Context context) {
        switch (status) {
            case STATUS_ACCEPTED:
                return "Tap When On The Way";
            case STATUS_ON_WAY:
                return "Tap When Arrived";
            case STATUS_ARRIVED:
                return "Tap when Start";
            case STATUS_ONGOING:
                return "Tap when Finished";
            default:
                return "";
        }
    }

    public static String getStatusUpdateurl(String status, String jobId, boolean isJobPaused) {
        switch (status) {
            case STATUS_ACCEPTED:
                return String.format(AppConstants.ON_WAY_JOB, jobId);
            case STATUS_ON_WAY:
                return String.format(AppConstants.ARRIVED_JOB, jobId);
            case STATUS_ARRIVED:
                return String.format(AppConstants.START_JOB, jobId);
            case STATUS_ONGOING:
                    return String.format(AppConstants.FINISH_JOB, jobId);
            default:
                return "";
        }
    }

    public static boolean showStatusButton(String status) {
        return status.equalsIgnoreCase(STATUS_ACCEPTED) ||
                status.equalsIgnoreCase(STATUS_ON_WAY) ||
                status.equalsIgnoreCase(STATUS_ARRIVED);
    }

    public static boolean canCancelJob(String status) {
        return !(status.equalsIgnoreCase(STATUS_ONGOING) || isPastJob(status));
    }

    public static boolean isCompletedJob(String status) {
        return status.equalsIgnoreCase(STATUS_FINISHED) ||
                status.equalsIgnoreCase(STATUS_COMPLETED);
    }

    public static boolean isPastJob(String status) {
        return status.equalsIgnoreCase(STATUS_FINISHED) ||
                status.equalsIgnoreCase(STATUS_REMOVED) ||
                status.equalsIgnoreCase(STATUS_COMPLETED) ||
                status.equalsIgnoreCase(STATUS_CANCEL_USER) ||
                status.equalsIgnoreCase(STATUS_CANCEL_MOVER);
    }
}
