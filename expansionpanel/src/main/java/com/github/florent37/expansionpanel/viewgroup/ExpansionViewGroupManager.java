package com.github.florent37.expansionpanel.viewgroup;

import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.expansionpanel.ExpansionLayout;

class ExpansionViewGroupManager {
    private final ViewGroup viewGroup;
    private ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();

    public ExpansionViewGroupManager(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public void onViewAdded(){
        findExpansionsViews(viewGroup);
    }

    private void findExpansionsViews(View view){
        if(view instanceof ExpansionLayout){
            expansionLayoutCollection.add((ExpansionLayout) view);
        } else if(view instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                findExpansionsViews(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    public void setOpenOnlyOne(boolean openOnlyOne) {
        this.expansionLayoutCollection.openOnlyOne(openOnlyOne);
    }
}
