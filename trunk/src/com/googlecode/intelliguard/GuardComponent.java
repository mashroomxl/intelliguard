package com.googlecode.intelliguard;

import com.googlecode.intelliguard.facet.GuardFacetType;
import com.googlecode.intelliguard.gutter.GuardMarker;
import com.googlecode.intelliguard.inspection.GuardInspection;
import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.facet.FacetTypeRegistry;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
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
        EditorFactory.getInstance().addEditorFactoryListener(new GuardMarker());
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
