package com.googlecode.intelliguard.inspection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nls;
import com.intellij.psi.*;
import com.intellij.codeInspection.ProblemsHolder;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-08
 * Time: 19:56:13
 */
public class ObfuscationProblemsInspection extends GuardInspectionBase
{
    private static final String JAVA_IO_SERIALIZABLE = "java.io.Serializable";
    private static final String SERIAL_VERSION_UID = "serialVersionUID";
    private static final String SERIAL_PERSISTENT_FIELDS = "serialPersistentFields";
    private static final String METHOD_WRITE_OBJECT = "void writeObject(java.io.ObjectOutputStream)";
    private static final String METHOD_READ_OBJECT = "void readObject(java.io.ObjectInputStream)";
    private static final String METHOD_WRITE_REPLACE = "java.lang.Object writeReplace()";
    private static final String METHOD_READ_RESOLVE = "java.lang.Object readResolve()";

    /*
    class * implements java.io.Serializable {
        static final long serialVersionUID;
        static final java.io.ObjectStreamField[] serialPersistentFields;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }
    */

    @Nls
    @NotNull
    public String getDisplayName()
    {
        return "Obfuscation Problems";
    }

    @NotNull
    public String getShortName()
    {
        return "Obfuscation Problems";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly)
    {
        return new JavaElementVisitor()
        {
            public void visitReferenceExpression(PsiReferenceExpression expression)
            {
            }

            @Override
            public void visitClass(PsiClass aClass)
            {
                super.visitClass(aClass);
            }

            @Override
            public void visitField(PsiField field)
            {
                super.visitField(field);
            }

            @Override
            public void visitMethod(PsiMethod method)
            {
                super.visitMethod(method);
            }
        };
    }

    private boolean implementsSerializable(PsiClass psiClass)
    {
        final PsiClass[] interfaces = psiClass.getInterfaces();
        for (PsiClass anInterface : interfaces)
        {
            if (JAVA_IO_SERIALIZABLE.equals(anInterface.getQualifiedName()))
            {
                return true;
            }
        }
        return false;
    }
}
