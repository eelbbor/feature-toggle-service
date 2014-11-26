package com.featuretoggle.service;

import com.featuretoggle.db.dao.ToggleDAO;
import com.featuretoggle.domain.Toggle;

import java.sql.SQLException;

public class ToggleService {
    private ToggleDAO dao;

    public ToggleService() {
        this(null);
    }

    protected ToggleService(ToggleDAO dao) {
        this.dao = dao == null ? new ToggleDAO() : dao;
    }

    public Toggle createToggle(String name) throws SQLException {
        //need a way to get the AccountID although that should be part of the login
        return dao.insert(name);
    }
}
