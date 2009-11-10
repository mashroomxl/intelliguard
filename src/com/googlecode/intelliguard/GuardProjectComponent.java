package com.googlecode.intelliguard;

import com.googlecode.intelliguard.refactor.RenameListenerProvider;
import com.googlecode.intelliguard.ui.Icons;
import com.googlecode.intelliguard.ui.ToolWindowPanel;
import com.googlecode.intelliguard.gutter.GuardMarkerEditorListener;
import com.googlecode.intelliguard.runner.ProgressInfoReceiver;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.refactoring.listeners.RefactoringListenerManager;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-03
 * Time: 18:47:48
 */
public class GuardProjectComponent implements ProjectComponent
{
    public static final String TOOLWINDOW_ID = "IntelliGuard";

    private Project project;
    private ToolWindow toolWindow;
    private ToolWindowPanel toolWindowPanel;
    private RenameListenerProvider renameListenerProvider;
    private MessageBusConnection messageBusConnection;

    public GuardProjectComponent(Project project)
    {
        this.project = project;
    }

    public void initComponent()
    {
        // TODO: insert component initialization logic here
    }

    public void disposeComponent()
    {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName()
    {
        return "GuardProjectComponent";
    }

    public ProgressInfoReceiver createProgressInfoReceiver()
    {
        toolWindowPanel.clear();
        return toolWindowPanel;
    }

    public void projectOpened()
    {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        toolWindow = toolWindowManager.registerToolWindow(TOOLWINDOW_ID, true, ToolWindowAnchor.BOTTOM);
        toolWindowPanel = new ToolWindowPanel();

        final ContentFactory contentFactory = toolWindow.getContentManager().getFactory();
        final Content content = contentFactory.createContent(toolWindowPanel.getPanel(), "", true);

        toolWindow.getContentManager().addContent(content);

        toolWindow.setIcon(Icons.OBFUSCATION_NODE_ICON);
        toolWindow.setAvailable(true, new Runnable()
        {
            public void run()
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        final RefactoringListenerManager manager = RefactoringListenerManager.getInstance(project);
        renameListenerProvider = new RenameListenerProvider();
        manager.addListenerProvider(renameListenerProvider);

        messageBusConnection = project.getMessageBus().connect();
        messageBusConnection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new GuardMarkerEditorListener(project));
    }

    public void projectClosed()
    {
        ToolWindowManager.getInstance(project).unregisterToolWindow(TOOLWINDOW_ID);

        final RefactoringListenerManager manager = RefactoringListenerManager.getInstance(project);
        manager.removeListenerProvider(renameListenerProvider);

        messageBusConnection.disconnect();
    }
}
