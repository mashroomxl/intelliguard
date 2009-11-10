package com.googlecode.intelliguard.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.module.Module;
import com.googlecode.intelliguard.facet.GuardFacet;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-10
 * Time: 21:22:59
 */
public class ExportGroup extends DefaultActionGroup
{
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

    @Override
    public void update(AnActionEvent e)
    {
        final Presentation presentation = e.getPresentation();
        final Module module = getModule(e);
        if (module != null)
        {
            final GuardFacet guardFacet = getGuardFacet(module);
            if (guardFacet != null)
            {
                presentation.setVisible(true);
                return;
            }
        }
        presentation.setVisible(false);
    }
}
