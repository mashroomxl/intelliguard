package com.googlecode.intelliguard.action;

import com.googlecode.intelliguard.facet.GuardFacet;
import com.googlecode.intelliguard.generator.YGuardGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-10
 * Time: 20:48:09
 */
public class ExportYGuard extends AbstractExportAction
{
    protected String generateConfiguration(@NotNull GuardFacet guardFacet)
    {
        return YGuardGenerator.generateBuildXml(guardFacet);
    }
}
