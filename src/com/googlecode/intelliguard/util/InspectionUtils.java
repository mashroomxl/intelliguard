package com.googlecode.intelliguard.util;

import org.jetbrains.annotations.NotNull;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiMethod;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-09
 * Time: 18:44:16
 */
public class InspectionUtils
{
    @NotNull
    public static PsiElement getNameIdentifierElement(@NotNull PsiElement owner)
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

    public static boolean isDefinedInLibrary(@NotNull PsiMethod method)
    {
        final PsiMethod[] superMethods = method.findDeepestSuperMethods();
        if (superMethods.length != 0)
        {
            for (PsiMethod superMethod : superMethods)
            {
                final Module superModule = ModuleUtil.findModuleForPsiElement(superMethod);
                if (superModule == null || !superModule.equals(ModuleUtil.findModuleForPsiElement(method)))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
