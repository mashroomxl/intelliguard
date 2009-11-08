package com.googlecode.intelliguard.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Ronnie
 * Date: 2009-nov-01
 * Time: 09:21:17
 */
public class JarConfig
{
    private String linkLibraries;

    private List<String> jarEntries = new ArrayList<String>();

    public String getLinkLibraries()
    {
        return linkLibraries;
    }

    public void setLinkLibraries(String linkLibraries)
    {
        this.linkLibraries = linkLibraries;
    }

    public List<String> getJarEntries()
    {
        return jarEntries;
    }

    public void setJarEntries(List<String> jarEntries)
    {
        this.jarEntries = jarEntries;
    }

    public void addEntry(String entry)
    {
        if (!jarEntries.contains(entry))
        {
            jarEntries.add(entry);
        }
    }

    public void removeEntry(String entry)
    {
        jarEntries.remove(entry);
    }
}
