[![Release](https://jitpack.io/v/MarcinOrlowski/webnet-log.svg)](https://jitpack.io/#MarcinOrlowski/webnet-log)

![WebnetLog](PROJECT/webnet-log-200.png)

WebnetLog - Logging made easy
=============================
 WebnetLog is small extension of [android.util.Log](https://developer.android.com/reference/android/util/Log.html) 
 class, bringing simpler usage and automatically adding
 useful technical information to log entries to simplify debugging of your Android applications.

Add WebnetLog to your project
-----------------------------
 Edit your master `gradle.build` file and add jitpack repository (if you use other jitpack hosted artefacts,
 then you have this already and this step can safely be skipped):

    allprojects {
        repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

 then edit your module's `build.gradle` and add the following as your dependency:

    compile 'com.github.MarcinOrlowski:webnet-log:2.0.0'

Log content
-----------
 The main purpose of WebnetLog class was making debugging easier. This means you should your log entries
 more useful, by providing additional information helping locating adds some additional debug information
 to log message to make it more useful at less efforts. Let's see example usage of standard Log class:

    Log.d("TAG", "foo");

 This would produce following entry (timestamp and package id removed) in your log:

    06-20 15:49:36.640 29827-29834/pkg.id D/TAG﹕foo

 This is not very useful, as all you can tell from this entry is that "foo" was looged, not to mention
 I personally never found tagging log entries useful. I prefer more clear information - class name,
 line numbers etc. So let's check what WebnetLog could give you out of the box. When using WebnetLog
 you do not need to give a tag (but you can if you want, of course), just the message:

    WebnetLog.d("foo");

 and you would get output as follow:

    06-20 15:49:36.640 29827-29834/pkg.id D/WebnetLog﹕StartActivity$Stasis/test()[+25]: foo

 As you can see we can tell, that this log entry was produced by method `d()`, invoked in inner class
 `Stasis` of `StartActivity` class, in method `test()`, in line `25` of the source file.

 Even better, you can just ommit the arguments, and just do:

    WebnetLog.d();

 to get almost the same information logged:

     06-20 15:49:36.640 29827-29834/pkg.id D/WebnetLog﹕StartActivity$Stasis/test()[+25]


Usage
-----
 WebnetLog's API is almost identical to standard Log class, with the exception there's no `TAG`
 argument used. All methods (`i()`, `w()`, `d()`, `e()`) are there with additional signatures for
 easier logging.

 Instead of doing:

    Log.d(TAG, "foo bar");

 you should be now calling:

    WebnetLog.d("foo bar");

 As class adds own content to the logged message, your log message is now optional and you can safely do:

    WebnetLog.d();

 and still have lot of useful debug info logged:

    06-20 15:49:36.640 29827-29834/pkg.id D/WebnetLog﹕StartActivity$Stasis/test()[+25]


Tagging
-------
 If you do not want default `WebnetLog` tag, then you can use `setTag("string")`
 to set up different tag (tag is static, so you need to set it only once):

    WebnetLog.setTag("MyTag");
    WebnetLog.d();

 would produce:

    06-20 15:49:36.640 29827-29834/pkg.id D/MyTag﹕StartActivity$Stasis/test()[+25]

 You can also pass any object to `setTag()` and then tag will be set to object class'
 simple name:

    Class Foo {
      public void test() {
        WebnetLog.setTag(this);
        WebnetLog.d();
      }
    }

would produce:

    06-20 15:49:36.640 29827-29834/pkg.id D/Foo﹕Foo/test()[+25]


Removing logs from production builds
------------------------------------
 You can safely strip WebnetLog class during minification, and library comes with ProGuard config
 bundled. If for any reason you use it differently, add this to your ProGuard config file:

    -assumenosideeffects class com.webnetmobile.tools.webnetlog.WebnetLog {
         public static boolean isLoggable(java.lang.String, int);
         public static int v(...);
         public static int i(...);
         public static int w(...);
         public static int d(...);
         public static int e(...);
    }


Bug reports, improvements or PRs
--------------------------------
 Please submit your bug reports or feature requests to [project issue tracker](https://github.com/MarcinOrlowski/webnet-log/issues).
 
