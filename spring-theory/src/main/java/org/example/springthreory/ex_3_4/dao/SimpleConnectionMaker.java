package org.example.springthreory.ex_3_4.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface SimpleConnectionMaker {
    Connection makeNewConnection()throws ClassNotFoundException, SQLException;
}
