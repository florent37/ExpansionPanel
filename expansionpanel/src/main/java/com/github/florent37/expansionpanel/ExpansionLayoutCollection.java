package com.github.florent37.expansionpanel;

import java.util.Collection;
import java.util.HashSet;

public class ExpansionLayoutCollection {

    private final Collection<ExpansionLayout> expansions = new HashSet<>();
    private boolean openOnlyOne = false;

    private final ExpansionLayout.Listener expansionListener = new ExpansionLayout.Listener() {
        @Override
        public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
            if(expanded){
                for (ExpansionLayout view : expansions) {
                    if(view != expansionLayout){
                        view.collapse(true);
                    }
                }
            }
        }
    };

    public ExpansionLayoutCollection add(ExpansionLayout expansionLayout) {
        expansions.add(expansionLayout);
        expansionLayout.addListener(expansionListener);
        return this;
    }

    public ExpansionLayoutCollection addAll(ExpansionLayout...expansionLayouts) {
        for (ExpansionLayout expansionLayout : expansionLayouts) {
            add(expansionLayout);
        }
        return this;
    }

    public ExpansionLayoutCollection addAll(Collection<ExpansionLayout> expansionLayouts) {
        for (ExpansionLayout expansionLayout : expansionLayouts) {
            add(expansionLayout);
        }
        return this;
    }

    public ExpansionLayoutCollection remove(ExpansionLayout expansionLayout) {
        if (expansionLayout != null) {
            expansions.remove(expansionLayout);
            expansionLayout.removeListener(expansionListener);
        }
        return this;
    }

    public ExpansionLayoutCollection openOnlyOne(boolean openOnlyOne){
        this.openOnlyOne = openOnlyOne;
        return this;
    }
}
