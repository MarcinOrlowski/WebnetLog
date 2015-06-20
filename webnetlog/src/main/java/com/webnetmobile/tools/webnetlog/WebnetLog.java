package com.webnetmobile.tools.webnetlog;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/*
 ******************************************************************************
 *
 * Copyright 2013-2015 Marcin Orlowski, Webnet <http://webnetmobile.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************
 *
 * @author Marcin Orlowski <marcin.orlowski@webnet.pl>
 *
 ******************************************************************************
 */

/**
 * Logging made easy
 */
public class WebnetLog {
	static protected String TAG = "WebnetLog";

	/**
	 * Optional initialization method
	 *
	 * @param tag Any string you want to be used as log TAG, instead of default value
	 */
	public static void setTag(String tag) {
		TAG = tag;
	}

	/**
	 * Optional initialization method. Sets TAG to package name
	 *
	 * @param context (Context, Activity) to obtain package
	 */
	public static void setTagFromContext(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo mPackageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			setTag(mPackageInfo.packageName);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Gets caller trace. It's important to understand what we got on stack and NOT
	 * call this method directly or the output will be wrong.
	 *
	 * 0: getCallerTrace() (this)
	 * 1: formatMessage()
	 * 2: one of local calling methods (i.e. i())
	 * 3: caller class and method
	 *
	 * FIXME we could work that out by analyzing stack and skipping all invocation from this class but as for now it's not done
	 *
	 * @return the caller trace
	 */
	protected static String getCallerTrace() {
		return getCallerTrace(4);
	}

	/**
	 * Gets caller trace.
	 *
	 * @param depth depth
	 *
	 * @return the caller trace
	 */
	protected static String getCallerTrace(int depth) {
		Throwable throwable = new Throwable();
		StackTraceElement[] stackTrace = throwable.getStackTrace();

		String callerMethod = stackTrace[depth].getMethodName();
		String callerClass = stackTrace[depth].getClassName();
		int lineNumber = stackTrace[depth].getLineNumber();

		String[] nameParts = callerClass.split("\\.");
		callerClass = nameParts[nameParts.length - 1];

		return (callerClass + "/" + callerMethod + "()[+" + lineNumber + "]");
	}

	/**
	 * Format message.
	 *
	 * @return the string
	 */
	protected static String formatMessage() {
		return getCallerTrace();
	}

	/**
	 * Format message.
	 *
	 * @param message message to be added to result message
	 *
	 * @return message string
	 */
	protected static String formatMessage(String message) {
		return getCallerTrace() + ": " + message;
	}

	/**
	 * Format message.
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return message string
	 */
	protected static String formatMessage(String tag, String message) {
		return tag + ": " + getCallerTrace() + ": " + message;
	}

	//--[ i ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Information entry
	 *
	 * @return The number of bytes written
	 */
	public static int i() {
		return _i(TAG, formatMessage());
	}

	/**
	 * Logs Information entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int i(int message) {
		return i("(int): " + message);
	}

	/**
	 * Logs Information entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int i(String message) {
		return _i(TAG, formatMessage(message));
	}

	/**
	 * Logs Information entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int i(String tag, String message) {
		return Log.i(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Information entry
	 *
	 * @param message   message to be added to result message
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 *
	 * @return The number of bytes written
	 */
	public static int i(String message, Throwable throwable) {
		return _i(TAG, formatMessage(message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Information entry
	 *
	 * @param tag       custom tag message shall be PREFIXED with (not tagged!)
	 * @param message   message to be added to result message
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 *
	 * @return The number of bytes written
	 */
	public static int i(String tag, String message, Throwable throwable) {
		return _i(TAG, formatMessage(tag, message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Information entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	protected static int _i(String tag, String message) {
		return Log.i(tag, message);
	}

	//--[ w ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Warning entry
	 *
	 * @return The number of bytes written
	 */
	public static int w() {
		return _w(TAG, formatMessage());
	}

	/**
	 * Logs Warning entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int w(String message) {
		return _w(TAG, formatMessage(message));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int w(String tag, String message) {
		return _w(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param message   message to be added to result message
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 *
	 * @return The number of bytes written
	 */
	public static int w(String message, Throwable throwable) {
		return _w(TAG, formatMessage(message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param tag       custom tag message shall be PREFIXED with (not tagged!)
	 * @param message   message to be added to result message
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 *
	 * @return The number of bytes written
	 */
	public static int w(String tag, String message, Throwable throwable) {
		return _w(TAG, formatMessage(tag, message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	protected static int _w(String tag, String message) {
		return Log.w(tag, message);
	}

	//--[ e ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Error entry
	 *
	 * @return The number of bytes written
	 */
	public static int e() {
		return _e(TAG, formatMessage());
	}

	/**
	 * Logs Error entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int e(String message) {
		return _e(TAG, formatMessage(message));
	}

	/**
	 * Logs Error entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int e(String tag, String message) {
		return _e(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Error entry
	 *
	 * @param message   message to be added to result message
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 *
	 * @return The number of bytes written
	 */
	public static int e(String message, Throwable throwable) {
		return _e(TAG, formatMessage(message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Error entry
	 *
	 * @param tag       custom tag message shall be PREFIXED with (not tagged!)
	 * @param message   message to be added to result message
	 * @param throwable throwable to log
	 *
	 * @return The number of bytes written
	 */
	public static int e(String tag, String message, Throwable throwable) {
		return _e(TAG, formatMessage(tag, message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Error entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	protected static int _e(String tag, String message) {
		return Log.e(tag, message);
	}

	//--[ d ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Debug entry
	 *
	 * @return The number of bytes written
	 */
	public static int d() {
		return _d(TAG, formatMessage());
	}

	/**
	 * Logs Debug entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int d(String message) {
		return _d(TAG, formatMessage(message));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int d(String tag, String message) {
		return _d(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param message   message to be added to result message
	 * @param throwable throwable
	 *
	 * @return The number of bytes written
	 */
	public static int d(String message, Throwable throwable) {
		return _d(TAG, formatMessage(message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param tag       custom tag message shall be PREFIXED with (not tagged!)
	 * @param message   message to be added to result message
	 * @param throwable throwable
	 *
	 * @return The number of bytes written
	 */
	public static int d(String tag, String message, Throwable throwable) {
		return _d(TAG, formatMessage(tag, message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	protected static int _d(String tag, String message) {
		return Log.d(tag, message);
	}

}
