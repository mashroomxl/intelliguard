package com.googlecode.intelliguard.gutter;

import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: ronnie
 * Date: 2009-nov-09
 * Time: 12:51:43
 */
public class GuardGutterRenderer extends GutterIconRenderer
{
    private Icon icon;
    @Nullable
    private String tooltip;
    private int line;

    public GuardGutterRenderer(@NotNull Icon icon, @Nullable String tooltip, int line)
    {
        this.icon = icon;
        this.tooltip = tooltip;
        this.line = line;
    }

    @Override
    public String getTooltipText()
    {
        return tooltip != null ? tooltip : super.getTooltipText();
    }

    @NotNull
    public Icon getIcon()
    {
        return icon;
    }

    public RangeHighlighter addLineHighlighter(MarkupModel markupModel)
    {
        return markupModel.addLineHighlighter(line, HighlighterLayer.LAST, null);
    }
}
