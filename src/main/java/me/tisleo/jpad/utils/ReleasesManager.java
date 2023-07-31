package me.tisleo.jpad.utils;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This class handles all release-related operations.
 */
public final class ReleasesManager {

    /**
     * Calls the GitHub API to check for updates.
     * @return whether there is an update available.
     * @throws ExecutionException if an ExecutionException occurs
     * @throws InterruptedException if an InterruptedException occurs
     * @throws IOException if an I/O error occurs
     */
    public static boolean checkUpdate() throws ExecutionException, InterruptedException, IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/TisLeo/JPad/releases/latest")
                .build();

        ReleaseInfo info;
        try(Response response = client.newCall(request).execute()) {
            if (response.body() == null) return false;

            String responseBody = response.body().string();

            info = new Gson().fromJson(responseBody, ReleaseInfo.class);
        }

        int[] ghVersions;
        if (info.getName().length() > 7) {
            ghVersions = toIntArr(info.getName().substring(1, 6).split("\\."));
        } else {
            ghVersions = toIntArr(info.getName().substring(1).split("\\."));
        }

        int[] currentVersion = LiveAppStore.APP_VERSION;

        // 0 is major, 1 is minor, 2 is patch
        return ghVersions[0] > currentVersion[0] || ghVersions[1] > currentVersion[1] || ghVersions[2] > currentVersion[2];
    }

    /**
     * Converts a String[] to an int[].
     * @param arr The String[] to convert.
     * @return The int[].
     */
    private static int[] toIntArr(String[] arr) throws NumberFormatException {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = Integer.parseInt(arr[i]);
        }
        return newArr;
    }

    /**
     * A class to represent the "name" field in the GitHub API response.
     */
    static class ReleaseInfo {
        private String name;

        public String getName() {
            return name;
        }
    }

}
