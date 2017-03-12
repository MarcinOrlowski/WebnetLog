[![Release](https://jitpack.io/v/MarcinOrlowski/webnetlog.svg)](https://jitpack.io/#MarcinOrlowski/webnetlog)

![WebnetLog](img/webnet-log-200.png)

WebnetLog - Logging made easy
=============================
 `WebnetLog` is small extension of [android.util.Log](https://developer.android.com/reference/android/util/Log.html) 
 class, bringing simpler usage and automatically adding
 useful technical information to log entries to simplify debugging of your Android applications.

Installation
============

 Edit your master `gradle.build` file and **add** `maven { url 'https://jitpack.io' }` to your current
 `repositories` block content (if you use other jitpack hosted libraries, then this step can be skipped):

    allprojects {
      repositories {
        maven { url 'https://jitpack.io' }
        }
    }

 Next, edit your **module**'s `build.gradle` and the following dependency:

    compile 'com.github.MarcinOrlowski:WebnetLog:<VERSION>'

 For recent value of `<VERSION>` consult [library releases](https://github.com/MarcinOrlowski/WebnetLog/releases)
 or jitpack badge: [![Release](https://jitpack.io/v/MarcinOrlowski/webnetlog.svg)](https://jitpack.io/#MarcinOrlowski/WebnetLog)

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



Project support
===============
  
 `WebnetLog` is free software and you can use it fully free of charge in any of your projects, open source or 
 commercial, however if you feel it prevent you from reinventing the wheel, helped having your projects done or simply
 saved you time and money  then then feel free to donate to the project by sending some BTC to 
 `1LbfbmZ1KfSNNTGAEHtP63h7FPDEPTa3Yo`.
  
 ![BTC](img/btc.png)
  

Contributing
============
  
 Please report any issue spotted using [GitHub's project tracker](https://github.com/MarcinOrlowski/WebnetLog/issues).
   
 If you'd like to contribute to the this project, please [open new ticket](https://github.com/MarcinOrlowski/WebnetLog/issues) 
 **before doing any work**. This will help us save your time in case I'd not be able to accept such changes. But if all is good and 
 clear then follow common routine:
  
  * fork the project
  * create new branch
  * do your changes
  * send pull request
 
  
License
=======
  
  * Written and copyrighted &copy;2015-2017 by Marcin Orlowski <mail (#) marcinorlowski (.) com>
  * `WebnetLog` is open-sourced library licensed under the Apache 2.0 license
