package com.googlecode.intelliguard.runner;

import com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-02
 * Time: 18:40:18
 */
public class RunProgress
{
    private ProgressInfoReceiver infoReceiver;
    private int errors;

    public RunProgress(@Nullable ProgressInfoReceiver infoReceiver)
    {
        this.infoReceiver = infoReceiver;
    }

    public void markError(@Nullable String errorMessage)
    {
        errors++;
        if (errorMessage != null)
        {
            markMessage("[ERROR] " + errorMessage);
        }
    }

    public boolean lookingGood()
    {
        return errors == 0;
    }

    public void markMessage(String text)
    {
        if (text == null)
        {
            return;
        }
        if (infoReceiver != null)
        {
            infoReceiver.info(text);
        }
        ProgressManager.getInstance().getProgressIndicator().setText2(text);
    }
}
