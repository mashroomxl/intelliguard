/*
 * Copyright 2009 Ronnie Kolehmainen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
