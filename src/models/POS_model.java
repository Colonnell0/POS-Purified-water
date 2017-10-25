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
public class POS_model extends Database {

    private String cutomerName;

    protected String[] getProductCodeName() {

        String column[] = {"product_id", "product_name"};
        String res[][];

        res = SELECT(column, "products", column, "", "WHERE product_status = 'Enable'", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            for (int c = 0; c < column.length; c++) {
                result[r] = res[r][0] + " " + res[r][1];
            }

        }

        return result;
    }

    protected String[] getProductForTable(String id) {

        String column[] = {"product_id", "product_name", "product_price"};
        String res[][];

        res = SELECT(column, "products", column, "", "WHERE product_id = '" + id + "'", "");
        String result[] = new String[column.length];

        for (int c = 0; c < column.length; c++) {
            result[c] = res[0][c];

        }

        return result;
    }

    protected String[][] getCustomer(String customer) {

        String column[] = {"customers.customer_id", "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)", "customers.customer_street", "customers.customer_barangay", "customers.customer_city_municipality", "customers.customer_province", "transaction.transaction_borrowed_round", "transaction.transaction_borrowed_slim", "transaction.transaction_balance", "transaction.transaction_date", "transaction.transaction_remarks"};

        String res[][];

        res = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname) LIKE '%" + customer + "%'", "");
        String result[][] = new String[res.length][column.length];

        for (int r = 0; r < res.length; r++) {
            for (int c = 0; c < column.length; c++) {
                result[r][c] = res[r][c];
            }
        }

        return result;
    }

    protected void setTransaction(String tableName, String column[], String[] values) {

        INSERT(tableName, column, values);

    }

    protected void setAccountLogs(String tableName, String column[], String[] values) {

        INSERT(tableName, column, values);

    }

    protected String getTransactionLastID(String account_name) {

        String column[] = {"MAX(transaction_id)", "status"};

        String result = SELECT(column, "transaction", column, "", "WHERE account_name = '"+account_name+"'", "")[0][0];

        return result;
    }

    protected String getTransactionLastIDCustomer(String id) {

        String column[] = {"MAX(transaction_id)", "customer_id"};

        String result = SELECT(column, "transaction", column, "", "WHERE customer_id = '" + id + "'", "")[0][0];;

        return result;
    }

    protected void setDeleteTransaction(String table, String column, String id) {
        DELETE(table, column, id);
    }

    protected String[] setLoginDetails() throws UnknownHostException {

        String column[] = {"accounts.account_id",
            "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)",
            "account_logs.ipaddress",
            "account_logs.date_login", "accounts.account_type_id"};
        String res[][];

        res = SELECT(column, "accounts", column, "LEFT JOIN account_logs "
                + "ON accounts.account_id=account_logs.account_id ", "WHERE account_logs.account_logs_id=(SELECT max(account_logs.account_logs_id)) AND account_logs.ipaddress = '" + InetAddress.getLocalHost().toString() + "' ",
                " ORDER BY account_logs.account_logs_id DESC LIMIT 1");
        String result[] = new String[column.length];

        for (int c = 0; c < column.length; c++) {
            result[c] = res[0][c];

        }

        return result;
    }

    protected String[][] getAccountDetails(String accountName) {
        String result[][];
        String column[] = {
            "account_id",
            "CONCAT(account_firstname,' ',account_middle_initial,'. ',account_lastname)",
            "CONCAT(account_street,' ',account_barangay,' ',account_city_municipality,' ',account_province)",
            "account_contact"};

        result = SELECT(column, "accounts", column, "", "WHERE CONCAT(account_firstname,' ',account_middle_initial,'. ',account_lastname) ='" + accountName + "'", "");

        return result;
    }

    protected String[][] viewAllCustomerProfile(String account, String type) {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat datetime
                = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String column[] = {
            "sum(ifnull(transaction_borrowed_round, 0))",
            "sum(ifnull(transaction_borrowed_slim,0))",
            "sum(ifnull(transaction_others,0))",
            "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))",
            "sum(ifnull(transaction_total,0))",
            "sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0)))",
            "transaction_date",
            "transaction_type"};

        String res[][];

        res = SELECT(column, "transaction", column, "",
                " WHERE account_name = '" + account + "' "
                + "AND transaction_date = '" + datetime.format(dt) + "' "
                + "AND transaction_type = '" + type + "'",
                " GROUP BY account_name");
        String result[][] = new String[res.length][column.length];

        for (int r = 0; r < res.length; r++) {
            for (int c = 0; c < column.length; c++) {
                result[r][c] = res[r][c];
            }
        }

        return result;
    }

    protected void updateAccountLogs(String column[], String values[], String transactionID) {

        UPDATE("account_logs", column, values, "WHERE account_id = " + transactionID);

    }

    protected String[][] getDiscount(String id) {

        String column[] = {"product_id", "number_product", "discount"};
        String res[][];

        res = SELECT(column, "discounts", column, "", "WHERE customer_id = '" + id + "' "
                + "AND discount_status != 'Deleted'", "");

        return res;
    }

}
