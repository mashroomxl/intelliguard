package com.googlecode.intelliguard.facet;

import com.intellij.facet.*;
import com.intellij.openapi.module.Module;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-okt-21
 * Time: 19:03:44
 */
public class GuardFacet extends Facet<GuardFacetConfiguration>
{
    public static final FacetTypeId<GuardFacet> ID = new FacetTypeId<GuardFacet>("IntelliGuard");

    public GuardFacet(@NotNull Module module) {
      this(FacetTypeRegistry.getInstance().findFacetType(ID), module, "IntelliGuard", new GuardFacetConfiguration(), null);
    }

    public GuardFacet(@NotNull FacetType facetType, @NotNull Module module, String name, @NotNull GuardFacetConfiguration configuration, Facet underlyingFacet)
    {
        super(facetType, module, name, configuration, underlyingFacet);
    }

    public static GuardFacet getInstance(Module module)
    {
        return FacetManager.getInstance(module).getFacetByType(ID);
    }
}
