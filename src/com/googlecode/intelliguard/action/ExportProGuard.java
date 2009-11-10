package com.googlecode.intelliguard.action;

import org.jetbrains.annotations.NotNull;
import com.googlecode.intelliguard.facet.GuardFacet;
import com.googlecode.intelliguard.generator.ProGuardGenerator;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-10
 * Time: 20:54:51
 */
public class ExportProGuard extends AbstractExportAction
{
    protected String generateConfiguration(@NotNull GuardFacet guardFacet)
    {
        return ProGuardGenerator.generatePro(guardFacet);
    }
}
