package com.googlecode.intelliguard.gutter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * User: ronnie
 * Date: 2009-nov-09
 * Time: 13:11:58
 */
public class GuardMarker extends PsiTreeChangeAdapter implements EditorFactoryListener
{
    // EditorFactoryListener

    public void editorCreated(EditorFactoryEvent editorFactoryEvent)
    {
        final Editor editor = editorFactoryEvent.getEditor();
        final Project project = editor.getProject();

        if (project == null) return;

        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());

        if (psiFile != null)
        {
            updateMarkers(psiFile);
        }

    }

    public void editorReleased(EditorFactoryEvent editorFactoryEvent)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    // PsiTreeChangeListener
    @Override
    public void childAdded(PsiTreeChangeEvent psiTreeChangeEvent)
    {
        updateMarkers(psiTreeChangeEvent.getFile());
    }

    @Override
    public void childRemoved(PsiTreeChangeEvent psiTreeChangeEvent)
    {
        updateMarkers(psiTreeChangeEvent.getFile());
    }

    @Override
    public void childReplaced(PsiTreeChangeEvent psiTreeChangeEvent)
    {
        updateMarkers(psiTreeChangeEvent.getFile());
    }

    @Override
    public void childMoved(PsiTreeChangeEvent psiTreeChangeEvent)
    {
        updateMarkers(psiTreeChangeEvent.getFile());
    }

    public void updateMarkers(@Nullable final PsiFile psiFile)
    {
        if (psiFile == null) return;

        final Document document = psiFile.getViewProvider().getDocument();
        Project project = psiFile.getProject();
        if (document != null)
        {
            final MarkupModel markupModel = document.getMarkupModel(project);
            final List<GuardGutterRenderer> guardGutterRenderers = ApplicationManager.getApplication().runReadAction(new GuardGutterRendererComputation(psiFile));

            ApplicationManager.getApplication().invokeLater(new Runnable()
            {
                public void run()
                {
                    applyRenderers(markupModel, guardGutterRenderers);
                }
            }, ModalityState.NON_MODAL);
        }
    }

    private void applyRenderers(MarkupModel markupModel, List<GuardGutterRenderer> guardGutterRenderers)
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
