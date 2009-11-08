package com.googlecode.intelliguard.util;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import com.sun.istack.internal.NotNull;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.compiler.CompilerPaths;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-02
 * Time: 18:21:38
 */
public class ModuleUtils
{
    @Nullable
    public static File getModuleOutputDir(@NotNull Module module)
    {
        VirtualFile outputDirectory = CompilerPaths.getModuleOutputDirectory(module, false);
        if (outputDirectory != null)
        {
            return VfsUtil.virtualToIoFile(outputDirectory);
        }
        return null;
    }
}
