package com.googlecode.intelliguard;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.facet.FacetTypeRegistry;
import com.intellij.codeInspection.InspectionToolProvider;
import com.googlecode.intelliguard.facet.GuardFacetType;
import com.googlecode.intelliguard.inspection.GuardInspection;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-okt-21
 * Time: 19:27:37
 */
public class GuardComponent implements ApplicationComponent, InspectionToolProvider
{
    public GuardComponent()
    {
    }

    public void initComponent()
    {
        FacetTypeRegistry.getInstance().registerFacetType(GuardFacetType.getInstance());
    }

    public void disposeComponent()
    {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName()
    {
        return "GuardComponent";
    }

    public Class[] getInspectionClasses()
    {
        return new Class[] { GuardInspection.class };
    }
}
