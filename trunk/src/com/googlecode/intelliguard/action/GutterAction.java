package com.googlecode.intelliguard.action;

import com.googlecode.intelliguard.facet.GuardFacet;
import com.googlecode.intelliguard.gutter.GuardMarker;
import com.googlecode.intelliguard.ui.Icons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

/**
 * User: ronnie
 * Date: 2009-nov-09
 * Time: 16:28:07
 */
public class GutterAction extends AnAction
{
    @Override
    public void update(AnActionEvent e)
    {
        final Presentation presentation = e.getPresentation();
        final GuardFacet guardFacet = getGuardFacet(e);
        if (guardFacet == null)
        {
            presentation.setVisible(false);
        }
        else
        {
            final Document document = getDocument(e);
            if (document == null)
            {
                presentation.setVisible(false);
            }
            else
            {
                presentation.setVisible(true);
                presentation.setIcon(Icons.OBFUSCATION_NODE_ICON);
                final GuardMarker marker = document.getUserData(GuardMarker.KEY);
                if (marker == null)
                {
                    presentation.setText("Show Obfuscated symbols");
                }
                else
                {
                    presentation.setText("Hide Obfuscated symbols");
                }
            }
        }
    }

    public void actionPerformed(AnActionEvent e)
    {
        final Document document = getDocument(e);
        if (document == null)
        {
            return;
        }
        final Project project = getProject(e);
        if (project == null)
        {
            return;
        }
        final PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        final GuardMarker marker = document.getUserData(GuardMarker.KEY);
        if (marker == null)
        {
            GuardMarker.createMarkers(psiFile);
        }
        else
        {
            GuardMarker.clearMarkers(psiFile);
        }

        System.out.println("GutterAction.actionPerformed");
    }

    @Nullable
    private Module getModule(AnActionEvent e)
    {
        return DataKeys.MODULE.getData(e.getDataContext());
    }

    @Nullable
    private GuardFacet getGuardFacet(AnActionEvent e)
    {
        final Module module = getModule(e);
        return module == null ? null : GuardFacet.getInstance(module);
    }

    @Nullable
    private Project getProject(AnActionEvent e)
    {
        return DataKeys.PROJECT.getData(e.getDataContext());
    }

    @Nullable
    private Document getDocument(AnActionEvent e)
    {
        final Editor editor = getEditor(e);
        return editor == null ? null : editor.getDocument();
    }

    @Nullable
    private Editor getEditor(AnActionEvent e)
    {
        return DataKeys.EDITOR.getData(e.getDataContext());
    }
}
