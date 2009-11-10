package com.googlecode.intelliguard.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;
import com.googlecode.intelliguard.facet.GuardFacet;
import com.googlecode.intelliguard.GuardProjectComponent;
import com.googlecode.intelliguard.ui.FileChooserFactory;
import com.googlecode.intelliguard.util.UiUtils;
import com.googlecode.intelliguard.runner.ProgressInfoReceiver;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-10
 * Time: 20:55:33
 */
public abstract class AbstractExportAction extends AbstractGuardAction
{
    private static final String NL = "\n";
    private static final String LINE_SEP = System.getProperty("line.separator");

    public void actionPerformed(AnActionEvent e)
    {
        final Module module = getModule(e);
        if (module == null)
        {
            return;
        }
        final GuardFacet guardFacet = getGuardFacet(module);
        if (guardFacet == null)
        {
            return;
        }

        final String config = generateConfiguration(guardFacet);
        final ProgressInfoReceiver receiver = module.getProject().getComponent(GuardProjectComponent.class).createProgressInfoReceiver();
        receiver.info(config);

        final Component component = DataKeys.CONTEXT_COMPONENT.getData(e.getDataContext());
        final JFileChooser jFileChooser = FileChooserFactory.createPreferredDirectoryFileChooser("Save '" + module.getName() + "' obfuscation settings",
                module.getModuleFilePath());
        int res = jFileChooser.showSaveDialog(component);
        if (res == JFileChooser.APPROVE_OPTION)
        {
            final File selectedFile = jFileChooser.getSelectedFile();
            if (!selectedFile.exists() || selectedFile.canWrite())
            {
                dumpFile(config, selectedFile);
            }
        }

        UiUtils.showInfoBallon(module.getProject(), "Generated obfuscation settings");
    }

    private void dumpFile(String content, File file)
    {
        if (!NL.equals(LINE_SEP))
        {
            content = content.replace(NL, LINE_SEP);
        }
        OutputStream os = null;
        try
        {
            os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(content.getBytes("utf-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (os != null)
        {
            try
            {
                os.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected abstract String generateConfiguration(@NotNull GuardFacet guardFacet);
}
