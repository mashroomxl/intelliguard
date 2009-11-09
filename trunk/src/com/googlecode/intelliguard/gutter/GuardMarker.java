package com.googlecode.intelliguard.gutter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * User: ronnie
 * Date: 2009-nov-09
 * Time: 13:11:58
 */
public class GuardMarker implements DocumentListener
{
    public static final Key<GuardMarker> KEY = Key.create("com.googlecode.intelliguard.gutter.GuardMarker");

    private final PsiFile psiFile;

    public GuardMarker(PsiFile psiFile)
    {
        this.psiFile = psiFile;
    }

    public void beforeDocumentChange(DocumentEvent documentEvent)
    {
    }

    public void documentChanged(DocumentEvent documentEvent)
    {
        createMarkers(psiFile);
    }

    public static void clearMarkers(@Nullable final PsiFile psiFile)
    {
        final MarkupModel markupModel = getMarkupModel(psiFile);
        if (markupModel == null)
        {
            return;
        }

        final List<GuardGutterRenderer> guardGutterRenderers = Collections.emptyList();

        ApplicationManager.getApplication().invokeLater(new Runnable()
        {
            public void run()
            {
                applyRenderers(markupModel, guardGutterRenderers);
                final GuardMarker marker = markupModel.getDocument().getUserData(KEY);
                if (marker != null)
                {
                    markupModel.getDocument().removeDocumentListener(marker);
                    markupModel.getDocument().putUserData(KEY, null);
                }
            }
        }, ModalityState.NON_MODAL);
    }

    public static void createMarkers(@Nullable final PsiFile psiFile)
    {
        final MarkupModel markupModel = getMarkupModel(psiFile);
        if (markupModel == null)
        {
            return;
        }

        final List<GuardGutterRenderer> guardGutterRenderers = ApplicationManager.getApplication().runReadAction(new GuardGutterRendererComputation(psiFile));

        ApplicationManager.getApplication().invokeLater(new Runnable()
        {
            public void run()
            {
                applyRenderers(markupModel, guardGutterRenderers);
                final GuardMarker marker = new GuardMarker(psiFile);
                markupModel.getDocument().putUserData(KEY, marker);
                markupModel.getDocument().addDocumentListener(marker);
            }
        }, ModalityState.NON_MODAL);
    }

    @Nullable
    public static MarkupModel getMarkupModel(@Nullable final PsiFile psiFile)
    {
        if (psiFile == null) return null;

        final Document document = psiFile.getViewProvider().getDocument();
        final Project project = psiFile.getProject();
        if (document != null)
        {
            return document.getMarkupModel(project);
        }
        return null;
    }

    private static void applyRenderers(MarkupModel markupModel, List<GuardGutterRenderer> guardGutterRenderers)
    {
        RangeHighlighter[] allHighlighters = markupModel.getAllHighlighters();

        for (RangeHighlighter highlighter : allHighlighters)
        {
            GutterIconRenderer gutterIconRenderer = highlighter.getGutterIconRenderer();
            if (gutterIconRenderer instanceof GuardGutterRenderer)
            {
                markupModel.removeHighlighter(highlighter);
            }
        }

        for (GuardGutterRenderer guardGutterRenderer : guardGutterRenderers)
        {
            RangeHighlighter rangeHighlighter = guardGutterRenderer.addLineHighlighter(markupModel);
            rangeHighlighter.setGutterIconRenderer(guardGutterRenderer);
        }
    }
}
