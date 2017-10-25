/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import libs.Database;

/**
 *
 * @author Colonnello
 */
public class Transaction_History_Model extends Database {

    protected String[][] viewAllCustomerProfile(String limit, String Date) {

        String column[] = {"transaction.transaction_id",
            "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)",
            "ifnull(transaction.transaction_total,0)",
            "ifnull(transaction.transaction_amount,0)",
            "(transaction.transaction_balance)*-1",
            "transaction.transaction_type",
            "transaction.account_name",
            "transaction.status",
            "ifnull(transaction.transaction_date,'0000-00-00')"
        };

        String res[][];

        res = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE transaction.transaction_date = '" + Date + "'",
                limit);
        String result[][] = new String[res.length][column.length];

        for (int r = 0; r < res.length; r++) {
            for (int c = 0; c < column.length; c++) {
                result[r][c] = res[r][c];
            }
        }

        return result;
    }

}
