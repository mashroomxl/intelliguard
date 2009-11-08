package com.googlecode.intelliguard.ui;

import com.googlecode.intelliguard.facet.GuardFacet;
import com.intellij.openapi.ui.DialogBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-okt-30
 * Time: 11:56:51
 */
public class BuildObfuscatedJarDialog
{
    public static JarOptionsForm show(GuardFacet guardFacet)
    {
        DialogBuilder builder = new DialogBuilder(guardFacet.getModule().getProject());
        JarOptionsForm jarOptionsForm = new JarOptionsForm(guardFacet);
        builder.setCenterPanel(jarOptionsForm.getContentPane());
        builder.setTitle("Obfuscate Jar");
        builder.addOkAction().setText("Build");
        builder.addCancelAction().setText("Cancel");
        
        int res = builder.show();

        return res == 0 ? jarOptionsForm : null;
    }
}
