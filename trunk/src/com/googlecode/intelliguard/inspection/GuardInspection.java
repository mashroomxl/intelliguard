package com.googlecode.intelliguard.inspection;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.*;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.module.Module;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.model.Keeper;
import com.googlecode.intelliguard.util.PsiUtils;
import com.googlecode.intelliguard.fix.RemoveKeepFix;
import com.googlecode.intelliguard.fix.AddKeepFix;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-02
 * Time: 19:04:46
 */
public class GuardInspection extends GuardInspectionBase
{
    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly)
    {
        return new JavaElementVisitor()
        {
            public void visitReferenceExpression(PsiReferenceExpression expression)
            {
            }

            @Override
            public void visitClass(PsiClass aClass)
            {
                GuardFacetConfiguration configuration = getLocalConfiguration();
                if (configuration != null)
                {
                    final Keeper[] configuredGuardKeepers = configuration.findConfiguredGuardKeepers(aClass);
                    if (configuredGuardKeepers.length != 0)
                    {
                        holder.registerProblem(getNameIdentifierElement(aClass), "Class is not obfuscated", createRemoveKeeperFixes(configuration, configuredGuardKeepers));
                    }
                    else
                    {
                        holder.registerProblem(getNameIdentifierElement(aClass), "Class is obfuscated", createAddClassKeeperFixes(configuration, aClass));
                    }
                }

                super.visitClass(aClass);
            }

            @Override
            public void visitField(PsiField field)
            {
                GuardFacetConfiguration configuration = getLocalConfiguration();
                if (configuration != null)
                {
                    final Keeper[] configuredGuardKeepers = configuration.findConfiguredGuardKeepers(field);
                    if (configuredGuardKeepers.length != 0)
                    {
                        holder.registerProblem(getNameIdentifierElement(field), "Field is not obfuscated", createRemoveKeeperFixes(configuration, configuredGuardKeepers));
                    }
                    else
                    {
                        holder.registerProblem(getNameIdentifierElement(field), "Field is obfuscated", createAddFieldKeeperFixes(configuration, field));
                    }
                }

                super.visitField(field);
            }

            @Override
            public void visitMethod(PsiMethod method)
            {
                if (method.isConstructor())
                {
                    return;
                }

                final PsiMethod[] superMethods = method.findDeepestSuperMethods();
                if (superMethods.length != 0)
                {
                    for (PsiMethod superMethod : superMethods)
                    {
                        final Module superModule = ModuleUtil.findModuleForPsiElement(superMethod);
                        if (superModule == null || !superModule.equals(ModuleUtil.findModuleForPsiElement(method)))
                        {
                            return;
                        }
                    }
                }

                GuardFacetConfiguration configuration = getLocalConfiguration();
                if (configuration != null)
                {
                    final Keeper[] configuredGuardKeepers = configuration.findConfiguredGuardKeepers(method);
                    if (configuredGuardKeepers.length != 0)
                    {
                        holder.registerProblem(getNameIdentifierElement(method), "Method is not obfuscated", createRemoveKeeperFixes(configuration, configuredGuardKeepers));
                    }
                    else
                    {
                        holder.registerProblem(getNameIdentifierElement(method), "Method is obfuscated", createAddMethodKeeperFixes(configuration, method));
                    }
                }

                super.visitMethod(method);
            }
        };
    }

    private LocalQuickFix[] createAddClassKeeperFixes(final GuardFacetConfiguration configuration, final PsiClass aClass)
    {
        Collection<LocalQuickFix> fixes = new ArrayList<LocalQuickFix>();

        Keeper keeper = new Keeper();
        keeper.setType(Keeper.Type.CLASS);
        keeper.setName(PsiUtils.getKeeperName(aClass));

        fixes.add(new AddKeepFix(configuration, keeper));

        return fixes.toArray(new LocalQuickFix[fixes.size()]);
    }

    private LocalQuickFix[] createAddFieldKeeperFixes(final GuardFacetConfiguration configuration, final PsiField field)
    {
        Collection<LocalQuickFix> fixes = new ArrayList<LocalQuickFix>();
        String name = field.getName();

        Keeper keeper = new Keeper();
        keeper.setType(Keeper.Type.FIELD);
        keeper.setName(name);
        PsiClass containingClass = field.getContainingClass();
        if (containingClass != null)
        {
            keeper.setClazz(PsiUtils.getKeeperName(containingClass));
            fixes.add(new AddKeepFix(configuration, keeper));
        }

        keeper = new Keeper();
        keeper.setType(Keeper.Type.FIELD);
        keeper.setName(name);
        fixes.add(new AddKeepFix(configuration, keeper));

        return fixes.toArray(new LocalQuickFix[fixes.size()]);
    }

    private LocalQuickFix[] createAddMethodKeeperFixes(final GuardFacetConfiguration configuration, final PsiMethod method)
    {
        final PsiMethod[] superMethods = method.findDeepestSuperMethods();
        if (superMethods.length != 0)
        {
            return new LocalQuickFix[0];
        }

        Collection<LocalQuickFix> fixes = new ArrayList<LocalQuickFix>();
        String signature = PsiUtils.getSignatureString(method);

        Keeper keeper = new Keeper();
        keeper.setType(Keeper.Type.METHOD);
        keeper.setName(signature);
        PsiClass containingClass = method.getContainingClass();
        if (containingClass != null)
        {
            keeper.setClazz(PsiUtils.getKeeperName(containingClass));
            fixes.add(new AddKeepFix(configuration, keeper));
        }

        keeper = new Keeper();
        keeper.setType(Keeper.Type.METHOD);
        keeper.setName(signature);
        fixes.add(new AddKeepFix(configuration, keeper));

        return fixes.toArray(new LocalQuickFix[fixes.size()]);
    }

    private LocalQuickFix[] createRemoveKeeperFixes(final GuardFacetConfiguration configuration, final Keeper[] keepers)
    {
        Collection<LocalQuickFix> fixes = new ArrayList<LocalQuickFix>();
        for (Keeper keeper : keepers)
        {
            fixes.add(new RemoveKeepFix(configuration, keeper));
        }
        return fixes.toArray(new LocalQuickFix[fixes.size()]);
    }
}
