package org.base.example.entity;

import java.util.List;

/**
 * Created by 80005494 on 3/9/18.
 */

public class DayEntity {

    private String title;

    private List<LandscapeEntity> landscapeList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LandscapeEntity> getLandscapeList() {
        return landscapeList;
    }

    public void setLandscapeList(List<LandscapeEntity> landscapeList) {
        this.landscapeList = landscapeList;
    }
}
