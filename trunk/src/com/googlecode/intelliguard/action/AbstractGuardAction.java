package com.googlecode.intelliguard.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.googlecode.intelliguard.facet.GuardFacet;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-10
 * Time: 21:09:18
 */
public abstract class AbstractGuardAction extends AnAction
{
    // just a bunch of helpers
    
    @Nullable
    protected Module getModule(AnActionEvent e)
    {
        return DataKeys.MODULE.getData(e.getDataContext());
    }

    @Nullable
    protected GuardFacet getGuardFacet(@Nullable Module module)
    {
        return module == null ? null : GuardFacet.getInstance(module);
    }

    @Nullable
    protected GuardFacet getGuardFacet(AnActionEvent e)
    {
        final Module module = getModule(e);
        return module == null ? null : GuardFacet.getInstance(module);
    }

    @Nullable
    protected Project getProject(AnActionEvent e)
    {
        return DataKeys.PROJECT.getData(e.getDataContext());
    }

    @Nullable
    protected Document getDocument(AnActionEvent e)
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
