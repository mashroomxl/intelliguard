package com.googlecode.intelliguard.ui;

import com.googlecode.intelliguard.runner.ProgressInfoReceiver;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-10
 * Time: 19:30:30
 */
public class ToolWindowPanel implements ProgressInfoReceiver
{
    private static final String NL = System.getProperty("line.separator", "\n");
    private JPanel panel;
    private JTextArea textArea;

    public ToolWindowPanel()
    {
        clear();
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public void info(String info)
    {
        textArea.append(info + NL);
    }

    public void clear()
    {
        textArea.setText("");
    }
}
