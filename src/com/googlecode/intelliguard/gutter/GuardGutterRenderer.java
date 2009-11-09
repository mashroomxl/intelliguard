package com.googlecode.intelliguard.gutter;

import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.util.TextRange;
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
    private TextRange range;

    public GuardGutterRenderer(@NotNull Icon icon, @Nullable String tooltip, TextRange range)
    {
        this.icon = icon;
        this.tooltip = tooltip;
        this.range = range;
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
        return markupModel.addRangeHighlighter(range.getStartOffset(), range.getEndOffset(), HighlighterLayer.LAST, null, HighlighterTargetArea.LINES_IN_RANGE);
    }
}
