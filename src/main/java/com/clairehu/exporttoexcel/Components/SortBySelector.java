package com.clairehu.exporttoexcel.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

enum SortBy {
    ID, TITLE, ACCEPTANCE, DIFFICULTY;
}

@Component
public class SortBySelector {
    @Autowired
    private Environment env;

    public String getCssSelector(SortBy sortBy) {

        switch(sortBy){
            case ID: return env.getProperty("sortby.cssselector.id");
            case TITLE: return env.getProperty("sortby.cssselector.title");
            case ACCEPTANCE: return env.getProperty("sortby.cssselector.acceptance");
            case DIFFICULTY: return env.getProperty("sortby.cssselector.difficulty");
            default: return "";
        }
    }
}
