package org.example.springthreory.ch03.ex_3_3.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface SimpleConnectionMaker {
    Connection makeNewConnection()throws ClassNotFoundException, SQLException;
}
