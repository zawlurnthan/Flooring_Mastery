package com.zthan.dao;

import com.zthan.dto.State;

import java.util.List;

public interface StateDao {
    State getState(String name) throws PersistenceException;
    List<State> getAllStates() throws PersistenceException;
    void readTaxFile() throws PersistenceException;
}
