package org.example.springthreory.ex_5.ex_5_4.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface SimpleConnectionMaker {
    Connection makeNewConnection()throws ClassNotFoundException, SQLException;
}
