[ ![Download](https://api.bintray.com/packages/webnetmobile/maven/webnet-log/images/download.svg) ](https://bintray.com/webnetmobile/maven/webnet-log/_latestVersion)

![WebnetLog](https://bytebucket.org/webnetmobile/webnetlog/raw/master/PROJECT/webnet-log-200.png)

WebnetLog - Logging made easy
=============================
 WebnetLog is small extension to Log class, bringing simpler usage and automatically adding
 useful technical information to log entries to simplify application debugging.


Log content
-----------
 The main purpose of WebnetLog class was making debugging easier. This means you should your log entries
 more useful, by providing additional information helping locating adds some additional debug information
 to log message to make it more useful at less efforts. Let's see example usage. The following call:

    Log.d("TAG", "foo");

 which would produce following entry (timestamp and package id removed):

    06-20 15:49:36.640 29827-29834/pkg.id D/TAG﹕foo

 while when using WebnetLog class:

    WebnetLog.d("foo");

 you would get output as follow:

    06-20 15:49:36.640 29827-29834/pkg.id D/WebnetLog﹕StartActivity$Stasis/test()[+25]: foo

 As you can see we can tell, that this log entry was produced by method `d()`, invoked in inner class
 `Stasis` of `StartActivity` class, in method `test()`, in line `25` of the source file.


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
 If you do not want default `WebnetLog` tag, then you can i.e use `setTag()` or `setTagFromContext()`
 to set up different tag (tag is static, so you need to set it only once):

    WebnetLog.setTag("MyTag");
    WebnetLog.d();

 would produce:

    06-20 15:49:36.640 29827-29834/pkg.id D/MyTag﹕StartActivity$Stasis/test()[+25]

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

Add WebnetLog to your project
-----------------------------
 WebnetLog is available via jcenter repository, so all you need is to edit your `build.gradle` file
 and add one line:

    compile 'com.webnetmobile.tools:webnetlog:1.+'

 to your `dependencies` section.


Bug reports, improvements or PRs
--------------------------------
 Please submit your bug reports or feature requests to project issue tracker at Bitbucket:
 https://bitbucket.org/webnetmobile/webnetlog/issues
