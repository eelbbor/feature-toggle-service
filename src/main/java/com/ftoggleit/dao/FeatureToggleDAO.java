package com.ftoggleit.dao;

import com.ftoggleit.domain.FeatureToggle;

public class FeatureToggleDAO extends AbstractDAO {
    public static final String INSERT_STATEMENT = "INSERT_STATEMENT INTO %s.%s (id ,name) VALUES (? , ?)";

    public void insert(FeatureToggle ft) {
//        String insert = String.format(INSERT_STATEMENT, )
//        PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_STATEMENT);
//        preparedStatement.setString(1, person.getName());
    }


}
