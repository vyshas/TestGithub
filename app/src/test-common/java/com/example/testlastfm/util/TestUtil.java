package com.example.testlastfm.util;

import com.example.testgithub.model.OrgRepos;

public class TestUtil {
    /**
     *
     * @param name of the repos item
     * @return
     */
    public static OrgRepos createOrgRepos(String name){
        return new OrgRepos(1,name);
    }
}
