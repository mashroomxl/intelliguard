package com.googlecode.intelliguard.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.facet.GuardFacet;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-02
 * Time: 21:58:10
 */
public abstract class GuardInspectionBase extends LocalInspectionTool
{
    private GuardFacetConfiguration localConfiguration;

    @Nls
    @NotNull
    public String getGroupDisplayName()
    {
        return "IntelliGuard";
    }

    @Nls
    @NotNull
    public String getDisplayName()
    {
        return "Guard Inspection";
    }

    @NotNull
    public String getShortName()
    {
        return "GuardInspection";
    }

    @Override
    public boolean isEnabledByDefault()
    {
        return true;
    }

    @Override
    public void inspectionStarted(LocalInspectionToolSession session)
    {
        this.localConfiguration = getConfiguration(session.getFile());

        super.inspectionStarted(session);
    }

    @Override
    public void inspectionFinished(LocalInspectionToolSession session)
    {
        this.localConfiguration = null;

        super.inspectionFinished(session);
    }

    @Nullable
    public static GuardFacetConfiguration getConfiguration(PsiElement element)
    {
        Module module = ModuleUtil.findModuleForPsiElement(element);
        if (module == null)
        {
            return null;
        }
        GuardFacet guardFacet = GuardFacet.getInstance(module);
        return guardFacet != null ? guardFacet.getConfiguration() : null;
    }

    @Nullable
    protected GuardFacetConfiguration getLocalConfiguration()
    {
        return localConfiguration;
    }

    @NotNull
    protected PsiElement getNameIdentifierElement(@NotNull PsiElement owner)
    {
        if (owner instanceof PsiNameIdentifierOwner)
        {
            PsiElement identifierElement = ((PsiNameIdentifierOwner) owner).getNameIdentifier();
            if (identifierElement != null)
            {
                return identifierElement;
            }
        }
        return owner;
    }
}
