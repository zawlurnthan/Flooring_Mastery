package com.zthan.dao;

import com.zthan.dto.State;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StateDaoFileImpl implements StateDao {

    Map<String, State> tax_file = new LinkedHashMap<>();
    String DELIMITER = ",";
    String TAX_FILE = "Data/Taxes.txt";

    @Override
    public State getState(String name) throws PersistenceException {
        readTaxFile();
        return tax_file.get(name);
    }

    @Override
    public List<State> getAllStates() throws PersistenceException {
        readTaxFile();
        return tax_file.values().stream().toList();
    }

    // convert string into tax object
    private State unmarshallState(String text) {
        String[] tokens = text.split(DELIMITER);
        String abbreviation = tokens[0];
        String name = tokens[1];
        BigDecimal rate = new BigDecimal(tokens[2]);

        return new State(abbreviation, name, rate);
    }

    @Override
    public void readTaxFile() throws PersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load tax file into memory.", e);
        }

        // skip first line, convert texts to state objects and collect as Map
        tax_file = scanner
                .tokens()
                .skip(1)
                .map(this::unmarshallState)
                .collect(Collectors.toMap(State::getStateAbbreviation, Function.identity()));

        scanner.close();
    }
}
