package com.googlecode.intelliguard.ui;

import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.psi.PsiClass;
import com.googlecode.intelliguard.facet.GuardFacet;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.model.JarConfig;
import com.googlecode.intelliguard.util.ModuleUtils;
import com.googlecode.intelliguard.util.PsiUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-okt-31
 * Time: 12:10:49
 */
public class JarOptionsForm
{
    private JCheckBox executeMakeCheckBox;
    private JButton addButton;
    private JButton removeButton;
    private JTree jarContent;
    private JCheckBox linkLibrariesInManifestCheckBox;
    private JTextField librariesRelativePath;
    private JPanel contentPane;
    private TextFieldWithBrowseButton jarPath;
    private TextFieldWithBrowseButton obfuscatedJarPath;
    private TextFieldWithBrowseButton mainClass;
    private static final Icon FOLDER_ICON = IconLoader.getIcon("/nodes/folder.png");
    private static final Icon MODULE_ICON = IconLoader.getIcon("/nodes/ModuleClosed.png");
    private static final Icon JAR_ICON = IconLoader.getIcon("/fileTypes/archive.png");

    public JarOptionsForm(GuardFacet guardFacet)
    {
        final Module module = guardFacet.getModule();
        final GuardFacetConfiguration facetConfiguration = guardFacet.getConfiguration();

        mainClass.getTextField().setText(facetConfiguration.mainclass == null ? "" : facetConfiguration.mainclass);
        mainClass.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                TreeClassChooser classChooser = TreeClassChooserFactory.getInstance(module.getProject()).createProjectScopeChooser("Choose Main-Class");
                classChooser.showDialog();
                PsiClass psiClass = classChooser.getSelectedClass();
                if (psiClass != null)
                {
                    String className = PsiUtils.getKeeperName(psiClass);
                    // state.mainclass = className;
                    mainClass.getTextField().setText(className);
                }
            }
        });

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

        linkLibrariesInManifestCheckBox.setSelected(facetConfiguration.jarConfig.getLinkLibraries() != null);
        librariesRelativePath.setText(facetConfiguration.jarConfig.getLinkLibraries() == null ? "/" : facetConfiguration.jarConfig.getLinkLibraries());
        librariesRelativePath.setEnabled(linkLibrariesInManifestCheckBox.isSelected());
        linkLibrariesInManifestCheckBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                librariesRelativePath.setEnabled(linkLibrariesInManifestCheckBox.isSelected());
            }
        });

        executeMakeCheckBox.setSelected(true); // TODO: read from settings

        final MyTreeModel myTreeModel = new MyTreeModel(guardFacet);
        jarContent.setModel(myTreeModel);
        jarContent.setCellRenderer(new MyCellRenderer(guardFacet));

        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = FileChooserFactory.createModuleFileChooser(module);
                int res = chooser.showOpenDialog(contentPane);
                if (res == JFileChooser.APPROVE_OPTION)
                {
                    myTreeModel.addEntry(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        removeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                TreePath selectionPath = jarContent.getSelectionPath();
                if (selectionPath.getParentPath() == null)
                {
                    return;
                }
                Object path = selectionPath.getLastPathComponent();
                if (path != null)
                {
                    myTreeModel.removeEntry(String.valueOf(path));
                }
            }
        });

        jarContent.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                TreePath selectionPath = e.getNewLeadSelectionPath();
                removeButton.setEnabled(selectionPath != null && selectionPath.getParentPath() != null);
            }
        });
        removeButton.setEnabled(false);
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

    public String getLibrariesManifestPath()
    {
        return linkLibrariesInManifestCheckBox.isSelected()
                ? librariesRelativePath.getText()
                : null;
    }

    public boolean getExecuteMake()
    {
        return executeMakeCheckBox.isSelected();
    }

    private static class MyTreeModel extends DefaultTreeModel
    {
        private JarConfig myConfig;

        public MyTreeModel(GuardFacet guardFacet)
        {
            super(new MyTreeRoot());

            myConfig = guardFacet.getConfiguration().jarConfig;
        }

        public Object getChild(Object parent, int index)
        {
            if (parent instanceof MyTreeRoot)
            {
                if (index >= 0 && index < myConfig.getJarEntries().size())
                {
                    return myConfig.getJarEntries().get(index);
                }
            }
            return null;
        }

        public int getChildCount(Object parent)
        {
            if (parent instanceof MyTreeRoot)
            {
                return myConfig.getJarEntries().size();
            }
            return 0;
        }

        public boolean isLeaf(Object node)
        {
            return !(node instanceof MyTreeRoot);
        }

        public int getIndexOfChild(Object parent, Object child)
        {
            if (parent instanceof MyTreeRoot)
            {
                for (int i = 0; i < myConfig.getJarEntries().size(); i++)
                {
                    Object o = myConfig.getJarEntries().get(i);
                    if (o.equals(child))
                    {
                        return i;
                    }
                }
            }
            return -1;
        }

        public void removeEntry(String path)
        {
            myConfig.removeEntry(path);
            fireTreeStructureChanged(path, new String[] {path}, new int[0], new String[] {path});
        }

        public void addEntry(String path)
        {
            myConfig.addEntry(path);
            fireTreeStructureChanged(path, new String[] {path}, new int[0], new String[] {path});
        }
    }

    private static class MyCellRenderer extends DefaultTreeCellRenderer
    {
        private File moduleOutputDir;

        public MyCellRenderer(GuardFacet guardFacet)
        {
            moduleOutputDir = ModuleUtils.getModuleOutputDir(guardFacet.getModule());
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
        {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            if (value instanceof MyTreeRoot)
            {
                setIcon(JAR_ICON);
            }
            else if (value instanceof String)
            {
                File file = new File(String.valueOf(value));
                String filename = file.getName();
                if (file.isDirectory())
                {
                    if (file.equals(moduleOutputDir))
                    {
                        setIcon(MODULE_ICON);
                        setText("Module compile output");
                    }
                    else
                    {
                        setIcon(FOLDER_ICON);
                        setText("Folder '" + filename + "'");
                    }
                }
                else if (file.isFile())
                {
                    Icon icon = FileTypeManager.getInstance().getFileTypeByExtension(filename.substring(filename.lastIndexOf('.') + 1)).getIcon();
                    setIcon(icon);
                    setText("File '" + filename + "'");
                }
            }

            return this;
        }
    }

    private static class MyTreeRoot extends DefaultMutableTreeNode
    {
        @Override
        public boolean isLeaf()
        {
            return false;
        }
    }
}
