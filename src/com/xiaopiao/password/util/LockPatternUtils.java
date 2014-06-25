package com.xiaopiao.password.util;

import java.util.ArrayList;
import java.util.List;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.xiaopiao.password.ui.view.LockPatternView;



/**
 * Utilities for the lock patten and its settings.
 */
public class LockPatternUtils {

	private static final String TAG = "LockPatternUtils";

	private static final String LOCK_PATTERN_FILE = "/system/gesture.key";
	private static final String LOCK_PASSWORD_FILE = "/system/password.key";

	/**
	 * The maximum number of incorrect attempts before the user is prevented
	 * from trying again for {@link #FAILED_ATTEMPT_TIMEOUT_MS}.
	 */
	public static final int FAILED_ATTEMPTS_BEFORE_TIMEOUT = 5;

	/**
	 * The number of incorrect attempts before which we fall back on an
	 * alternative method of verifying the user, and resetting their lock
	 * pattern.
	 */
	public static final int FAILED_ATTEMPTS_BEFORE_RESET = 20;

	/**
	 * How long the user is prevented from trying again after entering the wrong
	 * pattern too many times.
	 */
	public static final long FAILED_ATTEMPT_TIMEOUT_MS = 30000L;

	/**
	 * The interval of the countdown for showing progress of the lockout.
	 */
	public static final long FAILED_ATTEMPT_COUNTDOWN_INTERVAL_MS = 1000L;

	/**
	 * The minimum number of dots in a valid pattern.
	 */
	public static final int MIN_LOCK_PATTERN_SIZE = 4;

	/**
	 * The minimum number of dots the user must include in a wrong pattern
	 * attempt for it to be counted against the counts that affect
	 * {@link #FAILED_ATTEMPTS_BEFORE_TIMEOUT} and
	 * {@link #FAILED_ATTEMPTS_BEFORE_RESET}
	 */
	public static final int MIN_PATTERN_REGISTER_FAIL = 3;

	public final static String PASSWORD_TYPE_KEY = "lockscreen.password_type";

	private final Context mContext;
	private final ContentResolver mContentResolver;
	private DevicePolicyManager mDevicePolicyManager;
	private static String sLockPatternFilename;
	private static String sLockPasswordFilename;

	public DevicePolicyManager getDevicePolicyManager() {
		if (mDevicePolicyManager == null) {
			mDevicePolicyManager = (DevicePolicyManager) mContext
					.getSystemService(Context.DEVICE_POLICY_SERVICE);
			if (mDevicePolicyManager == null) {
				Log.e(TAG,
						"Can't get DevicePolicyManagerService: is it running?",
						new IllegalStateException("Stack trace:"));
			}
		}
		return mDevicePolicyManager;
	}

	/**
	 * @param contentResolver
	 *            Used to look up and save settings.
	 */
	public LockPatternUtils(Context context) {
		mContext = context;
		mContentResolver = context.getContentResolver();
		// Initialize the location of gesture lock file
		if (sLockPatternFilename == null) {
			sLockPatternFilename = android.os.Environment.getDataDirectory()
					.getAbsolutePath() + LOCK_PATTERN_FILE;
			sLockPasswordFilename = android.os.Environment.getDataDirectory()
					.getAbsolutePath() + LOCK_PASSWORD_FILE;
		}
	}

	/**
	 * Deserialize a pattern.
	 * 
	 * @param string
	 *            The pattern serialized with {@link #patternToString}
	 * @return The pattern.
	 */
	public static List<LockPatternView.Cell> stringToPattern(String string) {
		List<LockPatternView.Cell> result = new ArrayList();

		final byte[] bytes = string.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			result.add(LockPatternView.Cell.of(b / 3, b % 3));
		}
		return result;
	}

	/**
	 * Serialize a pattern.
	 * 
	 * @param pattern
	 *            The pattern.
	 * @return The pattern in string form.
	 * 
	 */
	public static String patternToString(List<LockPatternView.Cell> pattern) {
		if (pattern == null) {
			return "";
		}
		final int patternSize = pattern.size();

		byte[] res = new byte[patternSize];
		for (int i = 0; i < patternSize; i++) {
			LockPatternView.Cell cell = pattern.get(i);
			res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
		}
		return arrayToString(res);
	}

	public static final String arrayToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			buff.append(bytes[i]);
		}
		return buff.toString();
	}
}