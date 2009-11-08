package com.googlecode.intelliguard.fix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.googlecode.intelliguard.model.Keeper;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-02
 * Time: 21:32:04
 */
public class AddKeepFix implements LocalQuickFix
{
    private final GuardFacetConfiguration configuration;
    private final Keeper keeper;

    public AddKeepFix(final GuardFacetConfiguration configuration, final Keeper keeper)
    {
        this.configuration = configuration;
        this.keeper = keeper;
    }

    @NotNull
    public String getName()
    {
        return "Keep " + keeper.getType().getName() + " from obfuscation" + (keeper.getType() == Keeper.Type.CLASS ? "" : (keeper.getClazz() == null ? " in all classes" : " in class " + keeper.getClazz()));
    }

    @NotNull
    public String getFamilyName()
    {
        return "Add guard family";
    }

    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor)
    {
        configuration.keepers.add(keeper);
    }
}
