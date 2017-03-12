package com.marcinorlowski.webnetlog;

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

	/**
	 * The mTag.
	 */
	protected static String mTag = "WebnetLog";

	/**
	 * Max length of tag string, as per Log.isLoggable() docs, tag limit is 23 chars
	 */
	final static int MAX_TAG_LENGTH = 23;

	/**
	 * Optional initialization method
	 *
	 * @param tag Any string you want to be used as log mTag, instead of default value
	 */
	public static void setTag(String tag) {
		if (tag.length() > MAX_TAG_LENGTH) {
			tag = tag.substring(0, (MAX_TAG_LENGTH - 1));
		}
		mTag = tag;
	}

	/**
	 * Sets logging tag to simple class name of passed object.
	 *
	 * @param obj object to get simple class name of as tag
	 */
	public static void setTag(Object obj) {
		setTag(obj.getClass().getSimpleName());
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

	/**
	 * Format throwable log entry
	 *
	 * @param throwable throwable
	 *
	 * @return string to be placed in log
	 */
	public static String formatThrowable(Throwable throwable) {
		return formatThrowable(throwable, " ");
	}

	/**
	 * Format throwable log entry
	 *
	 * @param throwable throwable to format. It's safe to pass @null as throwable
	 * @param prefix optional prefix (or @null)
	 *
	 * @return string to be placed in log. In case throwable is @null, returns empty string
	 */
	public static String formatThrowable(Throwable throwable, String prefix) {
		if (throwable != null) {
			return ((prefix != null) ? prefix : "") + throwable.getMessage() + " " + getStackTraceString(throwable);
		} else {
			return "";
		}
	}


	//--[ Compatibility ]---------------------------------------------------------------------------------------------------------

	/**
	 * Checks to see whether or not a log for the specified tag is loggable at the specified level. The default level of any tag is
	 * set to INFO. This means that any level above and including INFO will be logged. Before you make any calls to a logging method
	 * you should check to see if your tag should be logged.
	 *
	 * @param tag the tag to check
	 * @param level the level to check
	 *
	 * @return Returns @true when is allowed to be logged, @false otherwise
	 */
	public static boolean isLoggable(String tag, int level) {
		return Log.isLoggable(tag, level);
	}

	/**
	 * Handy function to get a loggable stack trace from a Throwable
	 *
	 * @param throwable An exception to log
	 *
	 * @return formatted stacktrace as string
	 */
	public static String getStackTraceString(Throwable throwable) {
		return Log.getStackTraceString(throwable);
	}

	//--[ v ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Verbose entry
	 *
	 * @return The number of bytes written
	 */
	public static int v() {
		return _v(mTag, formatMessage());
	}

	/**
	 * Logs Verbose entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int v(int message) {
		return v("(int): " + message);
	}

	/**
	 * Logs Verbose entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int v(String message) {
		return _v(mTag, formatMessage(message));
	}

	/**
	 * Logs Verbose entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int v(String tag, String message) {
		return _v(mTag, formatMessage(tag, message));
	}

	/**
	 * Logs Verbose entry
	 *
	 * @param message   message to be added to result message
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 *
	 * @return The number of bytes written
	 */
	public static int v(String message, Throwable throwable) {
		return _v(mTag, formatMessage(message + formatThrowable(throwable)));
	}

	/**
	 * Logs Verbose entry
	 *
	 * @param tag       custom tag message shall be PREFIXED with (not tagged!)
	 * @param message   message to be added to result message
	 * @param throwable exception to log (calls getMessage() and appends it to log entry)
	 *
	 * @return The number of bytes written
	 */
	public static int v(String tag, String message, Throwable throwable) {
		return _v(mTag, formatMessage(tag, message + formatThrowable(throwable)));
	}

	/**
	 * Logs Verbose entry
	 *
	 * @param tag     custom tag message shall be PREFIXED with (not tagged!)
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	protected static int _v(String tag, String message) {
		return Log.v(tag, message);
	}


	//--[ i ]---------------------------------------------------------------------------------------------------------------------

	/**
	 * Logs Information entry
	 *
	 * @return The number of bytes written
	 */
	public static int i() {
		return _i(mTag, formatMessage());
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
		return _i(mTag, formatMessage(message));
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
		return _i(mTag, formatMessage(tag, message));
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
		return _i(mTag, formatMessage(message + formatThrowable(throwable)));
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
		return _i(mTag, formatMessage(tag, message + formatThrowable(throwable)));
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
		return _w(mTag, formatMessage());
	}

	/**
	 * Logs Warning entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int w(String message) {
		return _w(mTag, formatMessage(message));
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
		return _w(mTag, formatMessage(tag, message));
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
		return _w(mTag, formatMessage(message + formatThrowable(throwable)));
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
		return _w(mTag, formatMessage(tag, message + formatThrowable(throwable)));
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
		return _e(mTag, formatMessage());
	}

	/**
	 * Logs Error entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int e(String message) {
		return _e(mTag, formatMessage(message));
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
		return _e(mTag, formatMessage(tag, message));
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
		return _e(mTag, formatMessage(message + formatThrowable(throwable)));
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
		return _e(mTag, formatMessage(tag, message + formatThrowable(throwable)));
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
		return _d(mTag, formatMessage());
	}

	/**
	 * Logs Debug entry
	 *
	 * @param message message to be added to result message
	 *
	 * @return The number of bytes written
	 */
	public static int d(String message) {
		return _d(mTag, formatMessage(message));
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
		return _d(mTag, formatMessage(tag, message));
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
		return _d(mTag, formatMessage(message + " " + throwable.getMessage()));
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
		return _d(mTag, formatMessage(tag, message + formatThrowable(throwable)));
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
