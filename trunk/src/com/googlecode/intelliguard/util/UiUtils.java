package com.googlecode.intelliguard.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.googlecode.intelliguard.GuardProjectComponent;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-01
 * Time: 16:42:23
 */
public class UiUtils
{
    public static void setStatusMessage(final Project project, final String message)
    {
        final WindowManager windowManager = WindowManager.getInstance();
        final StatusBar statusBar = windowManager.getStatusBar(project);
        Runnable r = new Runnable()
        {
            public void run()
            {
                statusBar.setInfo(message);
            }
        };

        ApplicationManager.getApplication().invokeLater(r);
    }

    public static void showInfoBallon(final Project project, final String text)
    {
        Runnable r = new Runnable()
        {
            public void run()
            {
                ToolWindowManager.getInstance(project).notifyByBalloon(GuardProjectComponent.TOOLWINDOW_ID, MessageType.INFO, text);
            }
        };
        ApplicationManager.getApplication().invokeLater(r);
    }

    public static void showErrorBallon(final Project project, final String text)
    {
        Runnable r = new Runnable()
        {
            public void run()
            {
                ToolWindowManager.getInstance(project).notifyByBalloon(GuardProjectComponent.TOOLWINDOW_ID, MessageType.ERROR, text);
            }
        };
        ApplicationManager.getApplication().invokeLater(r);
    }
}
