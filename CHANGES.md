2.0.0 (2017-03-12)
------------------
 - Code cleanup
 - Changed package name

1.0.3 (2015.06.23)
------------------
 - Added `setTag(object)` signature to set tag to object class' simple name

1.0.2 (2015.06.20)
------------------
 - Restored missing `v()`
 - Fixed chaining of `i()`
 - Added `isLoggable()`
 - `setTag()` trims tag to 23 chars long to make Log.* not throw IllegalArgumentException
 - added `formatThrowable()`
 - added `getStackTraceString()`

1.0.1 (2015.06.20)
------------------
 - Added return value to all logging methods to keep compatible with Log's methods return value.

1.0.0 (2015.06.20)
------------------
 - First public release
