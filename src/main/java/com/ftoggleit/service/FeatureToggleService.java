package com.ftoggleit.service;

import com.ftoggleit.domain.FeatureToggle;

import java.util.UUID;

public class FeatureToggleService {

    public FeatureToggle createToggle(String name) {
        FeatureToggle ft = new FeatureToggle(UUID.randomUUID(), name);
        //need to persist this sucker
        return ft;
    }
}
