package com.googlecode.intelliguard.model;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiClass;
import com.googlecode.intelliguard.util.PsiUtils;

import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-okt-27
 * Time: 20:49:32
 */
public class Keeper
{
    public enum Type
    {
        CLASS { public String getName() { return "class"; } },
        METHOD { public String getName() { return "method"; } },
        FIELD { public String getName() { return "field"; } };

        public abstract String getName();
    }

    public enum Visibility
    {
        PUBLIC { public String getName() { return "public"; } },
        PROTECTED { public String getName() { return "protected"; } },
        FRIENDLY { public String getName() { return "friendly"; } },
        PRIVATE { public String getName() { return "private"; } };

        public abstract String getName();
    }

    // mandatory
    private Type type;

    // optional in "class" only, otherwise mandatory
    private String name;

    // optional
    private String clazz;
    private Visibility classes;
    private Visibility methods;
    private Visibility fields;

    public String toAntElement()
    {
        switch (type)
        {
            case CLASS:
                StringBuilder sb = new StringBuilder(MessageFormat.format("<{0} name=\"{1}\"", getType().getName(), getName()));
                if (getClasses() != null)
                {
                    sb.append(" classes=\"").append(getClasses().getName()).append("\"");
                }
                if (getMethods() != null)
                {
                    sb.append(" methods=\"").append(getMethods().getName()).append("\"");
                }
                if (getFields() != null)
                {
                    sb.append(" fields=\"").append(getFields().getName()).append("\"");
                }
                sb.append(" />");
                return sb.toString();
            default:
                return getClazz() == null
                ? MessageFormat.format("<{0} name=\"{1}\" />", getType().getName(), getName())
                : MessageFormat.format("<{0} name=\"{1}\" class=\"{2}\" />", getType().getName(), getName(), getClazz());
        }
    }

    public boolean satisfies(PsiElement element)
    {
        switch (type)
        {
            case CLASS:
                if (element instanceof PsiClass)
                {
                    PsiClass psiClass = (PsiClass) element;
                    if (getName() != null)
                    {
                        return getName().equals(PsiUtils.getKeeperName(psiClass));
                    }
                }
                // TODO: deal with fields, methods...
                return false;
            case FIELD:
                if (element instanceof PsiField)
                {
                    PsiField psiField = (PsiField) element;
                    if (psiField.getName().equals(getName()))
                    {
                        return getClazz() == null || getClazz().equals(PsiUtils.getKeeperName(psiField.getContainingClass()));
                    }
                }
                return false;
            case METHOD:
                if (element instanceof PsiMethod)
                {
                    PsiMethod psiMethod = (PsiMethod) element;
                    String signature = PsiUtils.getSignatureString(psiMethod);
                    if (signature.equals(getName()))
                    {
                        if (getClazz() == null || getClazz().equals(PsiUtils.getKeeperName(psiMethod.getContainingClass())))
                        {
                            return true;
                        }
                        PsiMethod[] superMethods = psiMethod.findDeepestSuperMethods();
                        for (PsiMethod superMethod : superMethods)
                        {
                            if (getClazz().equals(PsiUtils.getKeeperName(superMethod.getContainingClass())))
                            {
                                return true;
                            }
                        }
                    }
                }
                return false;
        }
        return false;
    }

    public Visibility getClasses()
    {
        return classes;
    }

    public void setClasses(Visibility classes)
    {
        this.classes = classes;
    }

    public Visibility getMethods()
    {
        return methods;
    }

    public void setMethods(Visibility methods)
    {
        this.methods = methods;
    }

    public Visibility getFields()
    {
        return fields;
    }

    public void setFields(Visibility fields)
    {
        this.fields = fields;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String toText()
    {
        return toAntElement();
    }
}
