package com.zthan.dao;

import com.zthan.dto.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateDaoTest {

    StateDao sDao;
    State tx, ca;

    @BeforeEach
    void setUp() {
        sDao = new StateDaoFileImpl();

        tx = new State("TX", "Texas", new BigDecimal("4.45"));
        ca = new State("CA", "California", new BigDecimal("25.00"));
    }

    @Test
    void getState() throws PersistenceException {
        State retrievedCali = sDao.getState("CA");
        State retrievedTx = sDao.getState("TX");

        assertEquals(tx.getStateAbbreviation(), retrievedTx.getStateAbbreviation(), "Check state Abbreviation.");
        assertEquals(tx.getStateName(), retrievedTx.getStateName(), "Check Name");
        assertEquals(tx.getTaxRate(), retrievedTx.getTaxRate(), "Check Tax");

        assertEquals(ca.getStateAbbreviation(), retrievedCali.getStateAbbreviation(), "Check state Abbreviation.");
        assertEquals(ca.getStateName(), retrievedCali.getStateName(), "Check Name");
        assertEquals(ca.getTaxRate(), retrievedCali.getTaxRate(), "Check Tax");
    }

    @Test
    void getAllStates() throws PersistenceException {
        List<State> states = sDao.getAllStates();

        assertNotNull(states, "Check empty or not");
        assertEquals(4, states.size(), "Check the size of list");
        assertTrue(states.contains(tx), "Check if it has Texas");
        assertTrue(states.contains(ca), "Check if it has California");
    }
}