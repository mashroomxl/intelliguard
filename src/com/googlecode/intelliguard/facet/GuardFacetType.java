package com.googlecode.intelliguard.facet;

import com.intellij.facet.FacetType;
import com.intellij.facet.Facet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.googlecode.intelliguard.ui.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-okt-21
 * Time: 19:06:57
 */
public class GuardFacetType extends FacetType<GuardFacet, GuardFacetConfiguration>
{
    private static final GuardFacetType instance = new GuardFacetType();

    private GuardFacetType()
    {
        super(GuardFacet.ID, "IntelliGuard", "Obfuscation");
    }

    public static GuardFacetType getInstance()
    {
        return instance;
    }

    @Override
    public Icon getIcon()
    {
        return Icons.OBFUSCATION_NODE_ICON;
    }

    public GuardFacetConfiguration createDefaultConfiguration()
    {
        return new GuardFacetConfiguration();
    }

    public GuardFacet createFacet(@NotNull Module module, String name, @NotNull GuardFacetConfiguration configuration, @Nullable Facet underlyingFacet)
    {
        return new GuardFacet(getInstance(), module, name, configuration, underlyingFacet);
    }

    public boolean isSuitableModuleType(ModuleType moduleType)
    {
        ModuleType[] registeredTypes = ModuleTypeManager.getInstance().getRegisteredTypes();

        for (ModuleType registeredType : registeredTypes)
        {
            if ("JAVA_MODULE".equals(registeredType.getId()))
            {
                return true;
            }
        }

        return false;
    }
}
