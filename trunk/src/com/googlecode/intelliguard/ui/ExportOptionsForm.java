package com.googlecode.intelliguard.ui;

import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.module.Module;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.facet.GuardFacet;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-dec-03
 * Time: 19:49:59
 */
public class ExportOptionsForm
{
    private JPanel contentPane;
    private TextFieldWithBrowseButton mainClass;
    private TextFieldWithBrowseButton obfuscatedJarPath;
    private TextFieldWithBrowseButton jarPath;

    public ExportOptionsForm(@NotNull GuardFacet guardFacet)
    {
        final Module module = guardFacet.getModule();
        final GuardFacetConfiguration facetConfiguration = guardFacet.getConfiguration();

        mainClass.getTextField().setText(facetConfiguration.mainclass == null ? "" : facetConfiguration.mainclass);
        mainClass.addActionListener(new MainClassChooser(module, mainClass));

        jarPath.getTextField().setText(facetConfiguration.inFile == null ? "" : facetConfiguration.inFile);
        jarPath.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = FileChooserFactory.createSaveJarChooser(jarPath.getText(), module.getModuleFilePath());
                int res = chooser.showSaveDialog(contentPane);
                if (res == JFileChooser.APPROVE_OPTION && chooser.getFileFilter().accept(chooser.getSelectedFile()))
                {
                    jarPath.getTextField().setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        obfuscatedJarPath.getTextField().setText(facetConfiguration.outFile == null ? "" : facetConfiguration.outFile);
        obfuscatedJarPath.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = FileChooserFactory.createSaveJarChooser(obfuscatedJarPath.getText(), jarPath.getText(), module.getModuleFilePath());
                int res = chooser.showSaveDialog(contentPane);
                if (res == JFileChooser.APPROVE_OPTION && chooser.getFileFilter().accept(chooser.getSelectedFile()))
                {
                    obfuscatedJarPath.getTextField().setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    public JPanel getContentPane()
    {
        return contentPane;
    }

    public String getJarPath()
    {
        return jarPath.getText();
    }

    public String getObfuscatedJarPath()
    {
        return obfuscatedJarPath.getText();
    }

    public String getMainClass()
    {
        return mainClass.getText();
    }
}
