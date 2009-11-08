package com.googlecode.intelliguard.facet;

import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.options.ConfigurationException;
import com.googlecode.intelliguard.ui.YFacetConfigurationForm;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-okt-21
 * Time: 19:08:51
 */
public class GuardFacetEditorTab extends FacetEditorTab
{
    private GuardFacetConfiguration originalState;
    private GuardFacetConfiguration currentState;
    private YFacetConfigurationForm yFacetConfigurationForm;

    public GuardFacetEditorTab(GuardFacetConfiguration originalState, FacetEditorContext editorContext, FacetValidatorsManager validatorsManager)
    {
        this.originalState = originalState;
        this.currentState = new GuardFacetConfiguration();
        this.currentState.loadState(originalState);
        yFacetConfigurationForm = new YFacetConfigurationForm(editorContext, validatorsManager, currentState);
    }

    @Nls
    public String getDisplayName()
    {
        return "IntelliGuard Configuration";
    }

    public JComponent createComponent()
    {
        return yFacetConfigurationForm.getPanel();
    }

    public boolean isModified()
    {
        return !currentState.equalsGlobalSettings(originalState);
    }

    public void apply() throws ConfigurationException
    {
        originalState.applyGlobalSettings(currentState);
    }

    public void reset()
    {
    }

    public void disposeUIResources()
    {
    }
}
