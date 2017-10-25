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
public class Dashboard_Model extends Database {

    protected String[][] getTabulateMontylData(String year, String date) {
        String productName[][] = {};
        if (date.equalsIgnoreCase("Monthly")) {
            String column[] = {"MONTHNAME(transaction_date)",
                "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))",
                "if((sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0)))) <= 0,0,(sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))))) as 'balance'",
                "sum(ifnull(transaction.transaction_total,0))"};
            String fetch[] = {"MONTHNAME(transaction_date)",
                "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))",
                "balance",
                "sum(ifnull(transaction.transaction_total,0))"};

            productName = SELECT(column, "transaction", fetch, "", " WHERE YEAR(transaction_date) = '" + year + "' ", "GROUP BY MONTHNAME(transaction_date)  ORDER BY FIELD(MONTHNAME(transaction_date),'January','February','March','April','May','June','July','August','September','October','November','December')");

        }
        if (date.equalsIgnoreCase("Daily")) {
            String column[] = {"date_format(transaction_date, '%M %e, %Y') as 'date'",
                "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))",
                "if((sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0)))) <= 0,0,(sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))))) as 'balance'",
                "sum(ifnull(transaction.transaction_total,0))"};
            String fetch[] = {"date",
                "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))",
                "balance",
                "sum(ifnull(transaction.transaction_total,0))"};

            productName = SELECT(column, "transaction", fetch, "", " WHERE YEAR(transaction_date) = '" + year + "' ", "GROUP BY DAY(transaction_date)  ORDER BY date");

        }
        if (date.equalsIgnoreCase("Weekly")) {
            String column[] = {"date_format(transaction_date, '%M %e, %Y') as 'date'",
                "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))",
                "if((sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0)))) <= 0,0,(sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))))) as 'balance'",
                "sum(ifnull(transaction.transaction_total,0))"};
            String fetch[] = {"date",
                "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))",
                "balance",
                "sum(ifnull(transaction.transaction_total,0))"};

            productName = SELECT(column, "transaction", fetch, "", " WHERE YEAR(transaction_date) = '" + year + "' ", "GROUP BY WEEK(transaction_date)  ORDER BY date");

        }
        return productName;
    }

    protected String[][] getTotal(String year) {
        String total[][];
        String column[] = {
            "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0)) as 'Amount'",
            "sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))) as 'Balance' ",
            "sum(ifnull(transaction.transaction_total,0)) as 'Total'"};

        String fetch[] = {"Amount", "Balance", "Total"};

        total = SELECT(column, "transaction", fetch, "", " WHERE YEAR(transaction_date) = '" + year + "' ", "");

        return total;
    }

    protected String[][] getAccount() {
        String account[][];
        String column[] = {
            "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)",
            "account_types.account_type_name",
            "accounts.account_date",
            "account_logs.account_status"
        };

        account = SELECT(column, "accounts", column, "LEFT JOIN account_logs "
                + "ON accounts.account_id=account_logs.account_id "
                + "LEFT JOIN account_types "
                + "ON accounts.account_type_id=account_types.account_type_id", " WHERE account_logs.account_status = 'Available'", " GROUP BY accounts.account_id");

        return account;
    }

    protected String[][] viewSalesReport(String customer, String account_name, String fromDate, String toDate, String barangay, String city, String province, String type) {
        String result[][] = null;
        String column[] = {"customers.customer_id",
            "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)",
            "customers.customer_gender",
            "customers.customer_cellphone_no",
            "customers.customer_landline",
            "customers.customer_street",
            "customers.customer_barangay",
            "customers.customer_city_municipality",
            "customers.customer_province",
            "customers.customer_country",
            "sum(ifnull(transaction.transaction_borrowed_round, 0))",
            "sum(ifnull(transaction.transaction_borrowed_slim,0))",
            "sum(ifnull(transaction.transaction_returned_round, 0))",
            "sum(ifnull(transaction.transaction_returned_slim,0))",
            "sum(ifnull(transaction.transaction_borrowed_round, 0)) - sum(ifnull(transaction.transaction_returned_round, 0))",
            "sum(ifnull(transaction.transaction_borrowed_slim,0)) - sum(ifnull(transaction.transaction_returned_slim,0))",
            "sum(ifnull(transaction.transaction_others,0))",
            "sum(ifnull(transaction.transaction_total,0))",
            "sum(ifnull(transaction.transaction_amount,0)) as transaction_amount",
            "sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))) as transaction_balance",
            "sum(ifnull(transaction.transaction_change,0))",
            "ifnull(transaction.transaction_date,'0000-00-00') as 'date'",
            "ifnull(transaction.transaction_remarks,'N/A')",
            "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))"};

        String fetch[] = {"customers.customer_id",
            "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)",
            "customers.customer_gender",
            "customers.customer_cellphone_no",
            "customers.customer_landline",
            "customers.customer_street",
            "customers.customer_barangay",
            "customers.customer_city_municipality",
            "customers.customer_province",
            "customers.customer_country",
            "sum(ifnull(transaction.transaction_borrowed_round, 0))",
            "sum(ifnull(transaction.transaction_borrowed_slim,0))",
            "sum(ifnull(transaction.transaction_returned_round, 0))",
            "sum(ifnull(transaction.transaction_returned_slim,0))",
            "sum(ifnull(transaction.transaction_borrowed_round, 0)) - sum(ifnull(transaction.transaction_returned_round, 0))",
            "sum(ifnull(transaction.transaction_borrowed_slim,0)) - sum(ifnull(transaction.transaction_returned_slim,0))",
            "sum(ifnull(transaction.transaction_others,0))",
            "sum(ifnull(transaction.transaction_total,0))",
            "transaction_amount",
            "transaction_balance",
            "sum(ifnull(transaction.transaction_change,0))",
            "date",
            "ifnull(transaction.transaction_remarks,'N/A')",
            "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))"};

        result = SELECT(column, "customers", fetch,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE transaction.account_name ='" + account_name + "' "
                + customer + fromDate + toDate + barangay + city + province, " GROUP BY customers.customer_id " + type);

        return result;

    }

    protected String[][] getAddress(String id) {
        String result[][] = null;
        String column[] = {"barangay.barangay_id", "barangay", "cities.city_id", "cities.city", "provinces.province_id", "provinces.province", "countries.country_id", "countries.country"};

        if (id.isEmpty()) {
            result = SELECT(column, "barangay", column, "LEFT JOIN cities "
                    + "ON barangay.city_id=cities.city_id "
                    + "LEFT JOIN provinces ON cities.province_id=provinces.province_id "
                    + "LEFT JOIN countries ON provinces.country_id=countries.country_id", "", "");
        }
        if (!id.isEmpty()) {
            result = SELECT(column, "barangay", column, "LEFT JOIN cities "
                    + "ON barangay.city_id=cities.city_id "
                    + "LEFT JOIN provinces ON cities.province_id=provinces.province_id "
                    + "LEFT JOIN countries ON provinces.country_id=countries.country_id", "", " GROUP BY " + id);
        }

        return result;
    }

    protected String[][] getAccountAll() {
        String account[][];
        String column[] = {
            "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)",
            "account_types.account_type_name",
            "accounts.account_date",
            "accounts.account_status",
            "accounts.account_id",
            "CONCAT(accounts.account_street,', ',accounts.account_barangay,', ',accounts.account_city_municipality,', ',accounts.account_province )",
            "accounts.account_gender",
            "accounts.account_contact"};

        account = SELECT(column, "accounts", column, "LEFT JOIN account_logs "
                + "ON accounts.account_id=account_logs.account_id "
                + "LEFT JOIN account_types "
                + "ON accounts.account_type_id=account_types.account_type_id", " WHERE account_logs.account_status = 'Available' "
                + " AND accounts.account_status != 'Deleted'", " GROUP BY accounts.account_id");

        return account;
    }

    protected String[][] getAccountAll(String name) {
        String account[][];
        String column[] = {
            "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)",
            "account_types.account_type_name",
            "accounts.account_date",
            "accounts.account_status",
            "accounts.account_id",
            "CONCAT(accounts.account_street,', ',accounts.account_barangay,', ',accounts.account_city_municipality,', ',accounts.account_province )",
            "accounts.account_gender",
            "accounts.account_contact"};

        account = SELECT(column, "accounts", column, "LEFT JOIN account_logs "
                + "ON accounts.account_id=account_logs.account_id "
                + "LEFT JOIN account_types "
                + "ON accounts.account_type_id=account_types.account_type_id", " WHERE CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname) LIKE '%" + name + "%'"
                + " AND accounts.account_status != 'Deleted'", " GROUP BY accounts.account_id");

        return account;
    }

    protected String[][] getAccountType() {

        String column[] = {"account_type_id", "account_type_name"};

        String result[][] = SELECT(column, "account_types", column, "", "", "");

        return result;
    }

    protected void setInsetAccount(String tableName, String column[], String values[]) {

        INSERT(tableName, column, values);
    }

    protected String[][] getAccountTabulate(String id) {
        String account[][];
        String column[] = {
            "accounts.account_status",
            "accounts.account_firstname", "accounts.account_middle_initial", "accounts.account_lastname",
            "accounts.account_street", "accounts.account_barangay", "accounts.account_city_municipality", "accounts.account_province",
            "accounts.account_gender",
            "accounts.account_contact",
            "account_types.account_type_name",
            "accounts.account_username",
            "accounts.account_password",
            "accounts.account_type_id",
            "accounts.account_id"
        };

        account = SELECT(column, "accounts", column, "LEFT JOIN account_logs "
                + "ON accounts.account_id=account_logs.account_id "
                + "LEFT JOIN account_types "
                + "ON accounts.account_type_id=account_types.account_type_id", " WHERE accounts.account_id ='" + id + "'", "");

        return account;
    }

    protected void updateAccount(String column[], String values[], String transactionID) {

        UPDATE("accounts", column, values, "WHERE account_id = " + transactionID);

    }

    protected void deleteAccount(String table, String column, String id) {
        DELETE(table, column, id);
    }

    protected String[][] viewAllCustomerProfileGroup(String customer, String accounts) {

        String column[] = {"customers.customer_id",
            "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)",
            "customers.customer_gender",
            "customers.customer_cellphone_no",
            "customers.customer_landline",
            "customers.customer_street",
            "customers.customer_barangay",
            "customers.customer_city_municipality",
            "customers.customer_province",
            "customers.customer_country",
            "sum(ifnull(transaction.transaction_borrowed_round, 0))",
            "sum(ifnull(transaction.transaction_borrowed_slim,0))",
            "sum(ifnull(transaction.transaction_returned_round, 0))",
            "sum(ifnull(transaction.transaction_returned_slim,0))",
            "sum(ifnull(transaction.transaction_borrowed_round, 0)) - sum(ifnull(transaction.transaction_returned_round, 0))",
            "sum(ifnull(transaction.transaction_borrowed_slim,0)) - sum(ifnull(transaction.transaction_returned_slim,0))",
            "sum(ifnull(transaction.transaction_others,0))",
            "sum(ifnull(transaction.transaction_total,0))",
            "sum(ifnull(transaction.transaction_amount,0))",
            "sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0)))",
            "ifnull(customers.customer_date,'0000-00-00')",
            "ifnull(transaction.transaction_remarks,'N/A')",
            "customers.customer_firstname",
            "customers.customer_middle_initial",
            "customers.customer_lastname"};

        String result[][] = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname) LIKE '%" + customer + "%' "
                + "AND customers.customer_id >0 "
                + "AND customers.customer_status != 'Deleted' "
                + "AND customers.account_name ='" + accounts + "' ", " GROUP BY customers.customer_id ");

        return result;
    }

    protected String[][] getCustomer(String id) {

        String column[] = {"customer_firstname",
            "customer_middle_initial",
            "customer_lastname",
            "customer_gender",
            "customer_street",
            "customer_barangay",
            "customer_city_municipality",
            "customer_province",
            "customer_country",
            "customer_cellphone_no",
            "customer_landline", "customer_id"};

        String result[][] = SELECT(column, "customers", column, "", " WHERE customer_id = '" + id + "'", "");

        return result;
    }

    protected String getUsername(String username) {
        String result = "";
        String column[] = {"account_username", "account_id"};

        String res[][];

        res = SELECT(column, "accounts", column, "", " WHERE account_username = '" + username + "'", "");
        if (res.length > 0) {
            result = res[0][0];
        }

        return result;
    }

    protected String[] getProduct() {

        String column[] = {"product_id", "product_name"};
        String res[][];

        res = SELECT(column, "products", column, "", "WHERE product_status = 'Enable'", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][0] + " " + res[r][1];
        }

        return result;
    }

    protected void setDiscount(String tableName, String column[], String values[]) {

        INSERT(tableName, column, values);
    }

    protected String getUnitPrice(String product_ID) {

        String column[] = {"product_id", "product_price"};

        String res[][];

        res = SELECT(column, "products", column, "", "WHERE product_id = '" + product_ID + "'", "");
        String result = "";

        result = res[0][1];

        return result;
    }

    protected String[] getAccountList() {

        String column[] = {"account_id", "CONCAT(accounts.account_firstname,' ',accounts.account_middle_initial,'. ',accounts.account_lastname)"};
        String res[][];

        res = SELECT(column, "accounts", column, "", "WHERE account_status = 'Enable'", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];
        }

        return result;
    }

    protected String[][] viewAllCustomerDiscount(String customer, String accounts) {

        String column[] = {"discounts.discount_id",
            "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)",
            "customers.customer_gender",
            "customers.customer_cellphone_no",
            "customers.customer_landline",
            "customers.customer_street",
            "customers.customer_barangay",
            "customers.customer_city_municipality",
            "customers.customer_province",
            "customers.customer_country",
            "products.product_name",
            "discounts.number_product",
            "discounts.discount",
            "ifnull(discounts.discount_date,'0000-00-00')"
        };

        String res[][] = SELECT(column, "discounts", column,
                "LEFT JOIN customers\n"
                + "ON discounts.customer_id=customers.customer_id "
                + "LEFT JOIN products "
                + "ON discounts.product_id=products.product_id ", " WHERE CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname) LIKE '%" + customer + "%' "
                + "AND customers.customer_status != 'Deleted' "
                + "AND discounts.discount_status != 'Deleted' "
                + "AND customers.account_name ='" + accounts + "' ", "");

        return res;
    }

    protected void updateDiscount(String column[], String values[], String transactionID) {

        UPDATE("discounts", column, values, "WHERE discount_id = " + transactionID);

    }

    protected boolean getDiscountedCustomer(String customerID, String product_id) {

        String column[] = {"customer_id", "product_id"};
        String res[][];

        boolean tf = false;

        res = SELECT(column, "discounts", column, "", "WHERE customer_id = '" + customerID + "' "
                + "AND product_id = '" + product_id + "' "
                + "AND discount_status != 'Deleted'", "");
        if (res.length > 0) {
            tf = true;
        } else {
            tf = false;
        }

        return tf;
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

}
