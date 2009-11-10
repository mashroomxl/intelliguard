package com.googlecode.intelliguard.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.googlecode.intelliguard.facet.GuardFacet;
import com.googlecode.intelliguard.GuardProjectComponent;
import com.googlecode.intelliguard.runner.ProgressInfoReceiver;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-10
 * Time: 20:55:33
 */
public abstract class AbstractExportAction extends AbstractGuardAction
{
    public void actionPerformed(AnActionEvent e)
    {
        final Module module = getModule(e);
        if (module == null)
        {
            return;
        }
        final GuardFacet guardFacet = getGuardFacet(module);
        if (guardFacet == null)
        {
            return;
        }

        final String config = generateConfiguration(guardFacet);
        final ProgressInfoReceiver receiver = module.getProject().getComponent(GuardProjectComponent.class).createProgressInfoReceiver();
        receiver.info(config);
    }

    protected abstract String generateConfiguration(@NotNull GuardFacet guardFacet);
}
