package com.googlecode.intelliguard.gutter;

import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiFile;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-09
 * Time: 20:30:20
 */
public class GuardMarkerEditorListener implements FileEditorManagerListener
{
    private final Project project;

    public GuardMarkerEditorListener(Project project)
    {
        this.project = project;
    }

    public void fileOpened(FileEditorManager source, VirtualFile file)
    {
    }

    public void fileClosed(FileEditorManager source, VirtualFile file)
    {
    }

    public void selectionChanged(FileEditorManagerEvent event)
    {
        // Refresh the GuardMarker gutter
        final VirtualFile virtualFile = event.getNewFile();
        final PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        final GuardMarker marker = GuardMarker.getGuardMarker(psiFile);
        if (marker != null)
        {
            marker.refresh();
        }
    }
}
