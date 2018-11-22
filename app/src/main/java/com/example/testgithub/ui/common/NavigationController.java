

package com.example.testgithub.ui.common;



import com.example.testgithub.MainActivity;
import com.example.testgithub.R;
import com.example.testgithub.ui.OrgReposFragment;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }


    public void navigateToOrgRepos() {
        String tag = "orgrepos";
        OrgReposFragment userFragment = new OrgReposFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, userFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
