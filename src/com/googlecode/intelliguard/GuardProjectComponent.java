package com.googlecode.intelliguard;

import com.googlecode.intelliguard.gutter.GuardMarker;
import com.googlecode.intelliguard.refactor.RenameListenerProvider;
import com.googlecode.intelliguard.ui.Icons;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiManager;
import com.intellij.refactoring.listeners.RefactoringListenerManager;
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
    private RenameListenerProvider renameListenerProvider;

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

    public void projectOpened()
    {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        toolWindow = toolWindowManager.registerToolWindow(TOOLWINDOW_ID, true, ToolWindowAnchor.BOTTOM);
        toolWindow.setIcon(Icons.OBFUSCATION_NODE_ICON);
        toolWindow.setAvailable(false, new Runnable()
        {
            public void run()
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        final RefactoringListenerManager manager = RefactoringListenerManager.getInstance(project);
        renameListenerProvider = new RenameListenerProvider();
        manager.addListenerProvider(renameListenerProvider);

        GuardMarker guardMarker = new GuardMarker();
        PsiManager.getInstance(project).addPsiTreeChangeListener(guardMarker);
    }

    public void projectClosed()
    {
        ToolWindowManager.getInstance(project).unregisterToolWindow(TOOLWINDOW_ID);

        final RefactoringListenerManager manager = RefactoringListenerManager.getInstance(project);
        manager.removeListenerProvider(renameListenerProvider);        
    }
}
