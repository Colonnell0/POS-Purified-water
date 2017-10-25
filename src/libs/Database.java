/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Colonnello
 */
public class Database extends Model {

    private Statement stmt;
    private ResultSet rs;

    /**
     *
     * @param columnName column name of the table
     * @param tableName table name
     * @param fetch rs.getString()
     * @param leftJoin join table
     * @param whereClause
     * @param OrderBy
     * @return
     */
    protected String[][] SELECT(String[] columnName, String tableName, String fetch[], String leftJoin, String whereClause, String OrderBy) {
        int maxSizeArrayRow = 0;
        int counter2D = 0;
        String select = "SELECT ";

        db_connect();
        for (String hfinalTest : columnName) {
            select += hfinalTest + ",";
        }

        select = select.substring(0, select.length() - 1);
        select = select + " FROM " + tableName + " " + leftJoin + whereClause + OrderBy;
        System.out.println(select);

        try {
            stmt = conn.createStatement();
            stmt.execute(select);
            rs = stmt.getResultSet();
            rs.last();
            maxSizeArrayRow = rs.getRow();

        } catch (Exception e) {
            System.out.println(e);
        }
        String result[][] = new String[maxSizeArrayRow][fetch.length];
        //----------------------------------------------------------------------

        try {
            stmt = conn.createStatement();
            stmt.execute(select);
            rs = stmt.getResultSet();
            while (rs.next()) {

                for (int i = 0; i < fetch.length; i++) {
                    result[counter2D][i] = rs.getString(fetch[i]);
                }

                counter2D++;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    /**
     *
     * @param tableName
     * @param column
     * @param values
     */
    protected void INSERT(String tableName, String column[], String values[]) {
        db_connect();
        String insert = "INSERT INTO " + tableName + " (";

        for (String hfinalTest : column) {
            insert += hfinalTest + ",";
        }

        insert = insert.substring(0, insert.length() - 1);

        insert = insert + ") VALUES (";

        for (String hfinalTest : values) {
            insert += "'" + hfinalTest + "'" + ",";
        }
        insert = insert.substring(0, insert.length() - 1);

        insert = insert + ")";
        System.out.println(insert);

        try {
            stmt = conn.createStatement();
            stmt.execute(insert);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void DELETE(String table, String column, String id) {
        db_connect();
        String delete = "DELETE FROM " + table + " WHERE " + column + " = '" + id + "'";
        System.out.println(delete);
        try {
            stmt = conn.createStatement();
            stmt.execute(delete);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void UPDATE(String table, String[] column, String[] value, String whereClause) {
        db_connect();
        String update = "UPDATE " + table + " SET ";

        for (int i = 0; i < column.length; i++) {
            update += column[i] + " = '" + value[i] + "',";
        }
        update = update.substring(0, update.length() - 1);
        update += " " + whereClause;

        System.out.println(update);
        try {
            stmt = conn.createStatement();
            stmt.execute(update);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     *
     * @param length random number length()
     * @param alias as mysql
     * @param table from tableName
     * @param column not in column name
     * @return
     */
    protected int SELECT_UNIQUE_RANDOM(int length, String alias, String table, String column) {
        int num = 0;
        db_connect();
        String select = "SELECT FLOOR(RAND() * " + length + ") as " + alias + "\n"
                + "FROM " + table + " \n"
                + "WHERE \"" + alias + "\" NOT IN (SELECT " + column + " FROM " + table + ")\n"
                + "LIMIT 1\n"
                + "";
        System.out.println(select);
        try {
            stmt = conn.createStatement();
            stmt.execute(select);

            rs = stmt.getResultSet();
            while (rs.next()) {
                num = rs.getInt(alias);

            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return num;
    }

    protected String[] SELECT_MAX_ID(String table, String column[], String fetch[], String columnID) {

        db_connect();

        String select = "SELECT ";

        for (int i = 0; i < column.length; i++) {
            select += column[i] + ",";
        }
        select = select.substring(0, select.length() - 1);
        select = select + " FROM " + table + " WHERE " + columnID + " = (SELECT MAX(" + columnID + ") FROM " + table + ")";
        System.out.println(select);
        String result[] = new String[column.length];
        //----------------------------------------------------------------------
        try {
            stmt = conn.createStatement();
            stmt.execute(select);
            rs = stmt.getResultSet();
            while (rs.next()) {

                for (int i = 0; i < fetch.length; i++) {
                    result[i] = rs.getString(fetch[i]);
                }

            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

}
