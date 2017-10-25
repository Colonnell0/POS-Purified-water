/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.InetAddress;
import java.net.UnknownHostException;
import libs.Database;

/**
 *
 * @author Colonnello
 */
public class Expenses_Modal extends Database {

    protected String[] getAccountName() {

        String column[] = {"expenses_id",
            "expenses_received"};
        String res[][];

        res = SELECT(column, "expenses", column, "", "", "GROUP BY expenses_received");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getExpense() {

        String column[] = {"expenses_id",
            "expenses"};
        String res[][];

        res = SELECT(column, "expenses", column, "", "", "GROUP BY expenses");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String getAccountID(String accountName) throws UnknownHostException {

        String column[] = {"account_logs.account_id",
            "account_logs.account_logs_id",
            "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)",};
        String result;

        result = SELECT(column, "account_logs", column, "LEFT JOIN accounts ON "
                + "account_logs.account_id=accounts.account_id ", "WHERE ipaddress = '" + InetAddress.getLocalHost().toString() + "' "
                + "AND account_status = 'login' "
                + "AND CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname) LIKE '%" + accountName + "%'", "")[0][0];

        return result;
    }

    protected void setInsertExpense(String tableName, String column[], String values[]) {

        INSERT(tableName, column, values);
    }

    protected String[][] getExpenses(String date) {

        String column[] = {"expenses.expenses_id",
            "expenses.expenses",
            "expenses.expenses_amount",
            "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)",
            "expenses.expenses_received",
            "expenses.expenses_date",
            "expenses.expenses_description"};

        String res[][];

        res = SELECT(column, "expenses", column,
                "LEFT JOIN accounts\n"
                + "ON expenses.account_id=accounts.account_id", " WHERE expenses.expenses_date = '" + date + "'", "");
        String result[][] = new String[res.length][column.length];

        for (int r = 0; r < res.length; r++) {
            for (int c = 0; c < column.length; c++) {
                result[r][c] = res[r][c];
            }
        }

        return result;
    }
    protected String[][] getExpenses(String date,String expenses,String limit) {

        String column[] = {"expenses.expenses_id",
            "expenses.expenses",
            "expenses.expenses_amount",
            "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)",
            "expenses.expenses_received",
            "expenses.expenses_date",
            "expenses.expenses_description"};

        String res[][];

        res = SELECT(column, "expenses", column,
                "LEFT JOIN accounts\n"
                + "ON expenses.account_id=accounts.account_id", " WHERE expenses.expenses LIKE '%" + expenses + "%' "+date+" ", limit);
        String result[][] = new String[res.length][column.length];

        for (int r = 0; r < res.length; r++) {
            for (int c = 0; c < column.length; c++) {
                result[r][c] = res[r][c];
            }
        }

        return result;
    }
    

}
