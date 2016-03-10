## About IntelliGuard ##
I am developing IntelliGuard in my spare time. The idea of this project came up while looking for an obfuscator to be used with a Maven/Intellij project. There are a lot of obfuscators out there, at least [according to Google](http://www.google.com/search?q=java+obfuscator), but not many are actually free and up-to-date. After searching for a while, and testing a few, I came up with two "winners"; ProGuard and yGuard. They both did the job but were (in my opinion) tedious to configure. Also, I lacked the ability to _see_ directly in my code what was actually going to be visible and what was not, after obfuscation. Thirdly, but perhaps most important, I didn't (and I still don't) like having to use two applications for one project. It would be better to keep the ofuscation configuration within the project and to use one application for coding and obfuscation configuration, and avoid getting stale configurations due to refactoring/renaming of classes. That's what an _Integrated Development Environment_ is all about, right? These were enough reasons for me to start coding an IntelliJ IDEA plugin.

IntelliGuard is free and open source software. However, it is not a standalone program. In order benefit from this software it must be run as an IntelliJ IDEA plugin.

You have  the freedom to use the software for any purpose, to distribute it, to modify it, and to distribute modified versions of the software. It comes "as is" and without warranty of any kind. It is distributed under the terms of the Apache 2.0 license.


## About the Obfuscators ##

[yGuard](http://www.yworks.com/en/products_yguard_about.html) is distributed under the yGuard Software License which allows for free usage and linking, but not for redistribution. yGuard users can fully benefit from all IntelliGuard features, including executing the obfuscator from within IntelliJ IDEA. Since yGuard may not be distributed with the plugin, a convenient download link is provided in the 'Obfuscation Settings' form.

[ProGuard](http://proguard.sourceforge.net/) is distributed under the terms of the GNU General Public License. ProGuard is free but may not be linked with software that is not GPL-compliant. For ProGuard users IntelliGuards capabilities are limited to generating configuration files which can be used running ProGuard externally.