package com.googlecode.intelliguard.refactor;

import com.intellij.refactoring.listeners.RefactoringElementListenerProvider;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiField;
import com.googlecode.intelliguard.inspection.GuardInspectionBase;
import com.googlecode.intelliguard.facet.GuardFacetConfiguration;
import com.googlecode.intelliguard.model.Keeper;
import com.googlecode.intelliguard.util.PsiUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-03
 * Time: 19:31:35
 */
public class RenameListenerProvider implements RefactoringElementListenerProvider
{
    public RefactoringElementListener getListener(final PsiElement element)
    {
        if (element instanceof PsiClass || element instanceof PsiMethod || element instanceof PsiField)
        {
            final String oldName = PsiUtils.getKeeperName(element);
            if (oldName == null)
            {
                return null;
            }

            final GuardFacetConfiguration configuration = GuardInspectionBase.getConfiguration(element);
            if (configuration == null)
            {
                return null;
            }

            return new RefactoringElementListener()
            {
                public void elementMoved(@NotNull PsiElement newElement)
                {
                    elementRenamed(newElement);
                }

                public void elementRenamed(@NotNull PsiElement newElement)
                {
                    if (newElement instanceof PsiClass || newElement instanceof PsiMethod || newElement instanceof PsiField)
                    {
                        final String newName = PsiUtils.getKeeperName(newElement);
                        if (newName == null)
                        {
                            return;
                        }

                        for (Keeper keeper : configuration.keepers)
                        {
                            if (newElement instanceof PsiClass)
                            {
                                if (keeper.getType() == Keeper.Type.CLASS)
                                {
                                    if (oldName.equals(keeper.getName()))
                                    {
                                        keeper.setName(newName);
                                    }
                                }
                                else
                                {
                                    if (oldName.equals(keeper.getClazz()))
                                    {
                                        keeper.setClazz(newName);
                                    }
                                }
                            }
                            else
                            {
                                if (oldName.equals(keeper.getName()))
                                {
                                    keeper.setName(newName);
                                }
                            }
                        }
                    }
                }
            };
        }
        return null;
    }
}
