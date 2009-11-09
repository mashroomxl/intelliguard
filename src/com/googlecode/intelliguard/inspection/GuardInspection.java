package com.googlecode.intelliguard.inspection;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.psi.*;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.model.Keeper;
import com.googlecode.intelliguard.util.PsiUtils;
import com.googlecode.intelliguard.util.InspectionUtils;
import com.googlecode.intelliguard.fix.RemoveKeepFix;
import com.googlecode.intelliguard.fix.AddKeepFix;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nls;

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

    
    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly)
    {
        return new JavaElementVisitor()
        {
            public void visitReferenceExpression(PsiReferenceExpression expression)
            {
            }

            @Override
            public void visitClass(PsiClass aClass)
            {
                if (!isOnTheFly)
                {
                    return;
                }
                
                GuardFacetConfiguration configuration = getLocalConfiguration();
                if (configuration != null)
                {
                    final Keeper[] configuredGuardKeepers = configuration.findConfiguredGuardKeepers(aClass);
                    if (configuredGuardKeepers.length != 0)
                    {
                        holder.registerProblem(InspectionUtils.getNameIdentifierElement(aClass), "Class is not obfuscated", ProblemHighlightType.INFORMATION, createRemoveKeeperFixes(configuration, configuredGuardKeepers, aClass));
                    }
                    else
                    {
                        holder.registerProblem(InspectionUtils.getNameIdentifierElement(aClass), "Class is obfuscated", ProblemHighlightType.INFORMATION, createAddClassKeeperFixes(configuration, aClass));
                    }
                }

                super.visitClass(aClass);
            }

            @Override
            public void visitField(PsiField field)
            {
                if (!isOnTheFly)
                {
                    return;
                }

                GuardFacetConfiguration configuration = getLocalConfiguration();
                if (configuration != null)
                {
                    final Keeper[] configuredGuardKeepers = configuration.findConfiguredGuardKeepers(field);
                    if (configuredGuardKeepers.length != 0)
                    {
                        holder.registerProblem(InspectionUtils.getNameIdentifierElement(field), "Field is not obfuscated", ProblemHighlightType.INFORMATION, createRemoveKeeperFixes(configuration, configuredGuardKeepers, field));
                    }
                    else
                    {
                        holder.registerProblem(InspectionUtils.getNameIdentifierElement(field), "Field is obfuscated", ProblemHighlightType.INFORMATION, createAddFieldKeeperFixes(configuration, field));
                    }
                }

                super.visitField(field);
            }

            @Override
            public void visitMethod(PsiMethod method)
            {
                if (!isOnTheFly)
                {
                    return;
                }

                GuardFacetConfiguration configuration = getLocalConfiguration();
                if (configuration != null && !InspectionUtils.isDefinedInLibrary(method))
                {
                    final Keeper[] configuredGuardKeepers = configuration.findConfiguredGuardKeepers(method);
                    if (configuredGuardKeepers.length != 0)
                    {
                        holder.registerProblem(InspectionUtils.getNameIdentifierElement(method), "Method is not obfuscated", ProblemHighlightType.INFORMATION, createRemoveKeeperFixes(configuration, configuredGuardKeepers, method));
                    }
                    else
                    {
                        holder.registerProblem(InspectionUtils.getNameIdentifierElement(method), "Method is obfuscated", ProblemHighlightType.INFORMATION, createAddMethodKeeperFixes(configuration, method));
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

        fixes.add(new AddKeepFix(configuration, keeper, aClass));

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
            fixes.add(new AddKeepFix(configuration, keeper, field));
        }

        keeper = new Keeper();
        keeper.setType(Keeper.Type.FIELD);
        keeper.setName(name);
        fixes.add(new AddKeepFix(configuration, keeper, field));

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
            fixes.add(new AddKeepFix(configuration, keeper, method));
        }

        keeper = new Keeper();
        keeper.setType(Keeper.Type.METHOD);
        keeper.setName(signature);
        fixes.add(new AddKeepFix(configuration, keeper, method));

        return fixes.toArray(new LocalQuickFix[fixes.size()]);
    }

    private LocalQuickFix[] createRemoveKeeperFixes(final GuardFacetConfiguration configuration, final Keeper[] keepers, final PsiElement element)
    {
        Collection<LocalQuickFix> fixes = new ArrayList<LocalQuickFix>();
        for (Keeper keeper : keepers)
        {
            fixes.add(new RemoveKeepFix(configuration, keeper, element));
        }
        return fixes.toArray(new LocalQuickFix[fixes.size()]);
    }
}
