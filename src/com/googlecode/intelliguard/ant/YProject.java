package com.googlecode.intelliguard.ant;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.BuildEvent;
import com.googlecode.intelliguard.runner.RunProgress;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-08
 * Time: 12:06:48
 */
public class YProject extends Project
{
    public YProject(final RunProgress runProgress)
    {

        addBuildListener(new BuildListener()
        {
            public void buildStarted(BuildEvent buildEvent)
            {
            }

            public void buildFinished(BuildEvent buildEvent)
            {
            }

            public void targetStarted(BuildEvent buildEvent)
            {
            }

            public void targetFinished(BuildEvent buildEvent)
            {
            }

            public void taskStarted(BuildEvent buildEvent)
            {
            }

            public void taskFinished(BuildEvent buildEvent)
            {
            }

            public void messageLogged(BuildEvent buildEvent)
            {
                final String message = buildEvent.getMessage();
                if (message != null)
                {
                    runProgress.markMessage(message);
                }
            }
        });
    }
}

