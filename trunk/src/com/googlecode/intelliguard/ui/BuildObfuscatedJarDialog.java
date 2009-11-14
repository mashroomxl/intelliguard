/*
 * Copyright 2009 Ronnie Kolehmainen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
