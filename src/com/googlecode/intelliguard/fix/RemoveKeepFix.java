package com.googlecode.intelliguard.fix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.model.Keeper;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-02
 * Time: 21:26:45
 */
public class RemoveKeepFix implements LocalQuickFix
{
    private final GuardFacetConfiguration configuration;
    private final Keeper keeper;

    public RemoveKeepFix(final GuardFacetConfiguration configuration, final Keeper keeper)
    {
        this.configuration = configuration;
        this.keeper = keeper;
    }

    @NotNull
    public String getName()
    {
        return "Remove " + keeper.getType().getName() + " keeper" + (keeper.getType() == Keeper.Type.CLASS ? "" : (keeper.getClazz() == null ? " in all classes" : " in class " + keeper.getClazz()));
    }

    @NotNull
    public String getFamilyName()
    {
        return "Remove guard family";
    }

    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor)
    {
        configuration.keepers.remove(keeper);
    }
}
