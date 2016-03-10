  * [Installation](Usage#Setting_up_IntelliGuard.md)
  * [Configuring](Usage#Configure_Obfuscation.md)
  * [Obfuscate Jar](Usage#Obfuscate_Jar.md)
  * [Export Settings](Usage#Export_Settings.md)


First install IntelliGuard with _IntelliJ IDEA plugin manager_. Search for **IntelliGuard** among the available plugins, right-click on it and choose 'Download and install'. Then restart Intellij IDEA.


---


## Setting up IntelliGuard ##

Add Obfuscation facet.

![http://intelliguard.googlecode.com/svn/images/01_add_facet.png](http://intelliguard.googlecode.com/svn/images/01_add_facet.png)

Accept or edit main settings.

![http://intelliguard.googlecode.com/svn/images/02_facet_settings.png](http://intelliguard.googlecode.com/svn/images/02_facet_settings.png)

If you want obfuscate jars with IntelliGuard this is the place to specify your yGuard installation path. You can enter the jar path manually or you can browse the filesystem by clicking the small button with three dots.

If you haven't got yGuard there is also a button which redirects to yGuard download page.

![http://intelliguard.googlecode.com/svn/images/04_yguard.png](http://intelliguard.googlecode.com/svn/images/04_yguard.png)

You will still able to use IntelliGuard for generating and exporting obfuscation settings regardless if you have yGuard installed.

If your obfuscated jar file should be executable you may also specify the class which should be used as the _Main-Class_ attribute in the jar manifest. Type the qualified class name or click the small button with three dots for a convenient class browser.
The Main-Class can also be specified later (when running the 'Obfuscate module' action).

![http://intelliguard.googlecode.com/svn/images/03_main_class.png](http://intelliguard.googlecode.com/svn/images/03_main_class.png)

Finally, click the **OK** button in the facet settings form and you are done.


---


## Configure Obfuscation ##

By default all class, field and method names will be obfuscated unless specified not to, or, in the case of a method, it extends or implements a method from a class defined outside of the module (for example in a library class).

Right-click in the editor gutter and choose 'Show Obfuscated symbols' in order to see which symbols will be obfuscated with current configuration. _Note: the gutter icons are toggled on a per-file basis._

![http://intelliguard.googlecode.com/svn/images/05_toggle_gutter.png](http://intelliguard.googlecode.com/svn/images/05_toggle_gutter.png)

Now you can start configuring and see what happens with teh gutter icons. Place the caret on any class, method or field symbol, hit **ALT+ENTER** and choose action.

![http://intelliguard.googlecode.com/svn/images/06_alt_enter_class.png](http://intelliguard.googlecode.com/svn/images/06_alt_enter_class.png)

or

![http://intelliguard.googlecode.com/svn/images/07_alt_enter_field.png](http://intelliguard.googlecode.com/svn/images/07_alt_enter_field.png)

That's all there is to configuring your obfuscation settings!


---


## Obfuscate Jar ##

This step explains how to actually build an obfuscated jar file. In order to do this you must have yGuard installed on your computer and have the yguard.jar archive path set up in the Obfuscation facet settings (see the [Installation](Usage#Setting_up_IntelliGuard.md) chapter above).

You can find the 'Obfuscate module' action located in the _Build_ menu.

![http://intelliguard.googlecode.com/svn/images/08_ofuscate_menu_action.png](http://intelliguard.googlecode.com/svn/images/08_ofuscate_menu_action.png)

This brings up an 'Obfuscate Jar' dialog.

![http://intelliguard.googlecode.com/svn/images/09_build_jar_dialog.png](http://intelliguard.googlecode.com/svn/images/09_build_jar_dialog.png)

To be continued...


---


## Export Settings ##