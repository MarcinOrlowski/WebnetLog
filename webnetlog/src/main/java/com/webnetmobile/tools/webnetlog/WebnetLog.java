package com.webnetmobile.tools.webnetlog;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/*
 ******************************************************************************
 *
 * Copyright 2013-2015 Marcin Or³owski, Webnet <http://webnetmobile.com/>
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
final public class WebnetLog {
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
	 * Optional initialization method
	 *
	 * @param context
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
	 *  0: getCallerTrace() (this)
	 *  1: formatMessage()
	 *  2: one of local calling methods (i.e. i())
	 *  3: caller class and method
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
	 * @param tag custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return message string
	 */
	protected static String formatMessage(String tag, String message) {
		return tag + ": " + getCallerTrace() + ": " + message;
	}

	//--[ i ]---------------------------------------------------------------------------------------------------------------------

	public static void i() {
		_i(TAG, formatMessage());
	}

	public static void i(int message) {
		i("(int): " + message);
	}

	public static void i(String message) {
		_i(TAG, formatMessage(message));
	}

	/**
	 * Logs Information entry
	 *
	 * @param tag log entry tag
	 * @param message message
	 */
	public static void i(String tag, String message) {
		Log.i(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Information entry
	 *
	 * @param message message
	 *
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 */
	public static void i(String message, Throwable throwable) {
		_i(TAG, formatMessage(message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Information entry
	 *
	 * @param tag tag
	 * @param message message
	 *
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 */
	public static void i(String tag, String message, Throwable throwable) {
		_i(TAG, formatMessage(tag, message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Information entry
	 *
	 * @param tag tag
	 * @param msg msg
	 */
	protected static void _i(String tag, String msg) {
		Log.i(tag, msg);
	}

	//--[ w ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Warning entry
	 */
	public static void w() {
		_w(TAG, formatMessage());
	}

	/**
	 * Logs Warning entry
	 *
	 * @param message message
	 */
	public static void w(String message) {
		_w(TAG, formatMessage(message));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param tag tag
	 * @param message message
	 */
	public static void w(String tag, String message) {
		_w(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param message message
	 * @param tr tr
	 */
	public static void w(String message, Throwable tr) {
		_w(TAG, formatMessage(message + " " + tr.getMessage()));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param tag tag
	 * @param message message
	 * @param tr tr
	 */
	public static void w(String tag, String message, Throwable tr) {
		_w(TAG, formatMessage(tag, message + " " + tr.getMessage()));
	}

	/**
	 * Logs Warning entry
	 *
	 * @param tag tag
	 * @param msg msg
	 */
	protected static void _w(String tag, String msg) {
		Log.w(tag, msg);
	}

	//--[ e ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Error entry
	 */
	public static void e() {
		_e(TAG, formatMessage());
	}

	/**
	 * Logs Error entry
	 *
	 * @param message message
	 */
	public static void e(String message) {
		_e(TAG, formatMessage(message));
	}

	/**
	 * Logs Error entry
	 *
	 * @param tag tag
	 * @param message message
	 */
	public static void e(String tag, String message) {
		_e(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Error entry
	 *
	 * @param message message
	 * @param throwable tr
	 */
	public static void e(String message, Throwable throwable) {
		_e(TAG, formatMessage(message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Error entry
	 *
	 * @param tag tag
	 * @param message message
	 * @param throwable tr
	 */
	public static void e(String tag, String message, Throwable throwable) {
		_e(TAG, formatMessage(tag, message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Error entry
	 *
	 * @param tag tag
	 * @param msg msg
	 */
	protected static void _e(String tag, String msg) {
		Log.e(tag, msg);
	}

	//--[ d ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Debug entry
	 */
	public static void d() {
		_d(TAG, formatMessage());
	}

	/**
	 * Logs Debug entry
	 *
	 * @param message message
	 */
	public static void d(String message) {
		_d(TAG, formatMessage(message));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param tag tag
	 * @param message message
	 */
	public static void d(String tag, String message) {
		_d(TAG, formatMessage(tag, message));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param message message
	 * @param throwable throwable
	 */
	public static void d(String message, Throwable throwable) {
		_d(TAG, formatMessage(message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param tag tag
	 * @param message message
	 * @param throwable throwable
	 */
	public static void d(String tag, String message, Throwable throwable) {
		_d(TAG, formatMessage(tag, message + " " + throwable.getMessage()));
	}

	/**
	 * Logs Debug entry
	 *
	 * @param tag tag
	 * @param msg msg
	 */
	protected static void _d(String tag, String msg) {
		Log.d(tag, msg);
	}


}
