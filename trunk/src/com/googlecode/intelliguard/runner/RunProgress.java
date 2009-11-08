package com.googlecode.intelliguard.runner;

import com.intellij.openapi.progress.ProgressManager;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-02
 * Time: 18:40:18
 */
public class RunProgress
{
    private int errors;

    public void markError()
    {
        errors++;
    }

    public boolean lookingGood()
    {
        return errors == 0;
    }

    public void messageReceived(String text)
    {
        ProgressManager.getInstance().getProgressIndicator().setText2(text);
    }
}
