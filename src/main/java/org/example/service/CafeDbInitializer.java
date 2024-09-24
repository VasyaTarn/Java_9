package org.example.service;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.exception.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class CafeDbInitializer {
    private static final Random RANDOM_GENERATOR = new Random();
    private static final List<String> TABLES_NAME_ARRAY;
    private static final String SQL_SCRIPT_CREATE_TABLES;

    static
    {
        SQL_SCRIPT_CREATE_TABLES = PropertyFactory.getInstance().getProperty().getProperty("db.sqlScriptCreateTables");

        String tablesNames = PropertyFactory.getInstance().getProperty().getProperty("db.tablesNames");
        TABLES_NAME_ARRAY = Arrays.stream(tablesNames.split(",")).collect(Collectors.toList());
    }

    public static void createTables() {
        try {
            Connection conn = ConnectionFactory.getInstance().makeConnection();

            for (String tableName : TABLES_NAME_ARRAY)
            {
                if (!tableExists(tableName))
                {
                    try (Stream<String> lineStream = Files.lines(Paths.get(SQL_SCRIPT_CREATE_TABLES)))
                    {
                        StringBuilder createTablesQuery = new StringBuilder();

                        for (String currentString : lineStream.collect(Collectors.toList()))
                        {
                            createTablesQuery.append(currentString).append(" ");
                        }

                        try (PreparedStatement ps = conn.prepareStatement(createTablesQuery.toString()))
                        {
                            ps.execute();
                        }

                    }
                    catch (IOException exception)
                    {
                        throw new FileException("Error with createTables.sql");
                    }
                    catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }

        }
        catch (ConnectionDBException | FileException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static boolean tableExists(String tableName) throws ConnectionDBException
    {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection())
        {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
            return resultSet.next();
        }
        catch (SQLException exception)
        {
            throw new ConnectionDBException("error connection to DB");
        }
    }

}
