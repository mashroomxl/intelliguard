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

package com.googlecode.intelliguard.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.facet.GuardFacet;
import com.googlecode.intelliguard.gutter.GuardMarker;
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

    public static void alertGuardMarkers(PsiElement element)
    {
        final PsiFile psiFile = element.getContainingFile();
        final GuardMarker marker = GuardMarker.getGuardMarker(psiFile);
        if (marker != null)
        {
            marker.refresh();
        }
    }
}
