[ ![Download](https://api.bintray.com/packages/webnetmobile/maven/webnet-log/images/download.svg) ](https://bintray.com/webnetmobile/maven/webnet-log/_latestVersion)

![WebnetLog](https://bytebucket.org/webnetmobile/webnetlog/raw/master/PROJECT/webnet-log-200.png)

WebnetLog
=========
 WebnetLog is small extension to Log class, bringing simpler usage and automatically adding
 useful technical information to log entries to simplify application debugging.


Log content
-----------
The main purpose of WebnetLog class was making debugging easier. This means you should your log
entries more useful, by providing additional information helping locating adds some additional
debug information to log message to make it more useful at less efforts. Let's see example usage.
The following call

    Log.d("TAG", "foo");

which would produce following entry (timestamp and package id removed)

    D/TAG﹕foo

while when using WebnetLog class

    WebnetLog.d("foo");

you would get output as follow:

    D/WebnetLog﹕StartActivity$Stasis/test()[+25]: foo

As you can see we can tell, that this log entry was produced by method `d()`, invoked in inner
class `Stasis` of `StartActivity` class, in method `test()`, in line `25` of the source file.


Usage
-----
WebnetLog's API is almost identical to standard Log class, with the exception there's no `TAG`
argument used. All methods (`i()`, `w()`, `d()`, `e()`) are there with additional signatures
for easier logging.

Instead of doing:

    Log.d(TAG, "foo bar");

you will be now doing:

    WebnetLog.d("foo bar");

As class adds own content to the logged message, your log message is now optional and you can safely
do:

    WebnetLog.d();

and still have lot of useful debug info logged.


Tagging
-------
If you do not want default `WebnetLog` tag, then you can i.e use `setTag()` or `setTagFromContext()`
to set up different tag (tag is static, so you need to set it only once).

Add WebnetLog to your project
----------------------------=
WebnetLog is available via jcentral repository, so all you need is to edit your `build.gradle` file
and add one line:

```
    compile 'com.webnetmobile.tools:webnetlog:1.+'
```

to your `dependencies` section.
