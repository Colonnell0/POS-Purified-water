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
public class Customer extends Database {

    protected String[][] customer() {
        String column[] = {"customer_id", "customer_firstname", "customer_lastname", "customer_cellphone_no", "customer_landline", "customer_street", "customer_barangay", "customer_city_municipality", "customer_province", "customer_country"};

        String res[][];

        res = SELECT(column, "customers", column, "", "", "");
        String result[][] = new String[res.length][column.length];

        for (int r = 0; r < res.length; r++) {
            for (int c = 0; c < column.length; c++) {
                result[r][c] = res[r][c];
            }
        }

        return result;

    }

    protected void updateCustomer(String column[], String values[], String customer) {

        UPDATE("customers", column, values, "WHERE customer_id = " + customer);

    }

    protected String[][] setProduct() {
        String productName[][];
        String column[] = {"product_id", "product_name", "product_price"};

        productName = SELECT(column, "products", column, "", "WHERE product_status = 'Enable'", "");

        return productName;
    }

    protected boolean customerAvail(String id) {
        String customer[][];
        boolean tf = false;
        String column[] = {"customer_id", "customer_firstname"};

        customer = SELECT(column, "customers", column, "", "WHERE customer_id = " + id, "");
        if (customer.length > 0) {
            tf = true;
        }
        if (customer.length <= 0) {
            tf = false;
        }

        return tf;
    }

    protected String[][] setProduct(String id) {
        String productName[][];
        String column[] = {"product_id", "product_name", "product_price"};

        productName = SELECT(column, "products", column, "", "WHERE product_id = " + id, "");

        return productName;
    }

    protected String[][] viewAllCustomerProfileGroup(String account_name, String customer) {

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
            "customers.customer_lastname",
            "ifnull(discounts.discount_date,'0000-00-00')"};

        String result[][] = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id "
                + "LEFT JOIN discounts"
                + " ON customers.customer_id=discounts.customer_id ", " WHERE CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname) LIKE '%" + customer + "%' "
                + "AND customers.customer_id >0 "
                + "AND customers.customer_status != 'Deleted' "
                + "AND customers.account_name ='" + account_name + "' ", " GROUP BY customers.customer_id ");

        return result;

    }

    protected String[][] getCustomerTransactionNotYetReg(String transactionID, String status) {

        String column[] = {"customers.customer_id",
            "ifnull(CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname),'N/A')",
            "ifnull(CONCAT(customers.customer_street,', ',customers.customer_barangay,', ', customers.customer_city_municipality,', ', customers.customer_province),'N/A')",
            "ifnull(transaction.transaction_borrowed_round, 0)",
            "ifnull(transaction.transaction_borrowed_slim,0)",
            "ifnull(transaction.transaction_total,0)",
            "transaction.status",
            "ifnull(transaction.transaction_date,'0000-00-00')",
            "transaction.account_name",
            "transaction.transaction_basket",
            "transaction.transaction_id"};

        String result[][] = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE  transaction.transaction_id = '" + transactionID + "' AND transaction.status= '" + status + "' AND customers.customer_id = '0'", "GROUP BY customers.customer_id");

        return result;
    }

    protected String[][] getCustomerTransactionReg(String customerID, String transactionID, String status) {

        String column[] = {"customers.customer_id",
            "ifnull(CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname),'N/A')",
            "ifnull(CONCAT(customers.customer_street,', ',customers.customer_barangay,', ', customers.customer_city_municipality,', ', customers.customer_province),'N/A')",
            "ifnull(transaction.transaction_borrowed_round, 0)",
            "ifnull(transaction.transaction_borrowed_slim,0)",
            "ifnull(transaction.transaction_total,0)",
            "transaction.status",
            "ifnull(transaction.transaction_date,'0000-00-00')",
            "transaction.account_name",
            "transaction.transaction_basket",
            "transaction.transaction_id"};

        String result[][] = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE  customers.customer_id = '" + customerID + "' AND transaction.transaction_id = '" + transactionID + "' AND transaction.status =  '" + status + "' ", "GROUP BY customers.customer_id");

        return result;
    }

    protected String[][] getCustomerTransaction(String transactionID, String status) {

        String column[] = {"customers.customer_id",
            "ifnull(CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname),'N/A')",
            "ifnull(CONCAT(customers.customer_street,', ',customers.customer_barangay,', ', customers.customer_city_municipality,', ', customers.customer_province),'N/A')",
            "ifnull(transaction.transaction_borrowed_round, 0)",
            "ifnull(transaction.transaction_borrowed_slim,0)",
            "ifnull(transaction.transaction_total,0)",
            "transaction.status",
            "ifnull(transaction.transaction_date,'0000-00-00')",
            "transaction.account_name",
            "transaction.transaction_basket",
            "transaction.transaction_id",
            "ifnull(discounts.discount_date,'0000-00-00')"};

        String result[][] = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id "
                + "LEFT JOIN discounts "
                + "ON customers.customer_id=discounts.customer_id ", " WHERE transaction.transaction_id = '" + transactionID + "' AND transaction.status =  '" + status + "' ", "");

        return result;
    }

    protected void setInsertCustomer(String tableName, String column[], String values[]) {

        INSERT(tableName, column, values);
    }

    protected void setDeleteTransaction(String table, String column, String id) {
        DELETE(table, column, id);
    }

    protected String[][] getCustomerBalance(String customerID, String transaction_id) {

        String column[] = {"customers.customer_id",
            "transaction.transaction_id",
            "sum(ifnull(transaction.transaction_borrowed_round, 0)) - sum(ifnull(transaction.transaction_returned_round, 0))",
            "sum(ifnull(transaction.transaction_borrowed_slim,0)) - sum(ifnull(transaction.transaction_returned_slim,0))",
            "sum(ifnull(transaction.transaction_total,0)) - (sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0)))"
        };

        String result[][] = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE  customers.customer_id = '" + customerID + "' AND  transaction_id <> " + transaction_id + " ", "GROUP BY customers.customer_id");

        return result;
    }

    protected String getCustomerBalanceCount(String customerID, String transaction_id) {

        String column[] = {"ifnull(COUNT(transaction.transaction_id),0)", "transaction.transaction_id", "customers.customer_id"};

        String res[][];

        res = SELECT(column, "customers", column,
                "LEFT JOIN transaction\n"
                + "ON customers.customer_id=transaction.customer_id", " WHERE  customers.customer_id = '" + customerID + "' AND  transaction_id <> " + transaction_id + " ", "");
        String result = "";

        result = res[0][0];

        return result;
    }

    protected void setUpdateTransactionReg(String column[], String values[], String transactionID) {

        UPDATE("transaction", column, values, "WHERE transaction_id = " + transactionID);

    }

    protected void setTransaction(String tableName, String column[], String[] values) {

        INSERT(tableName, column, values);

    }

    protected String[] getCustomerLastID() {

        String column[] = {"customer_id", "customer_firstname"};
        String res[];

        res = SELECT_MAX_ID("customers", column, column, "customer_id");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r];
        }

        return result;
    }

    protected String[] getTransactionLastID(String account_name) {

        String column[] = {"transaction_id", "customer_id"};
        String res[];

        res = SELECT_MAX_ID("transaction", column, column, "transaction_id AND account_name = '" + account_name + "'");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r];
        }

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

    //---------------------------------------------------------------------
    protected String[][] viewCollectionCollectibles(String account_name, String customer, String fromDate, String toDate, String barangay, String city, String province, String type) {
        String result[][] = null;
        if (type.equalsIgnoreCase("collection")) {

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
                "ifnull(transaction.transaction_borrowed_round, 0)",
                "ifnull(transaction.transaction_borrowed_slim,0)",
                "ifnull(transaction.transaction_returned_round, 0)",
                "ifnull(transaction.transaction_returned_slim,0)",
                "if(ifnull(transaction.transaction_borrowed_round, 0) -ifnull(transaction.transaction_returned_round, 0) < 0, 0,ifnull(transaction.transaction_borrowed_round, 0) -ifnull(transaction.transaction_returned_round, 0))",
                "if(ifnull(transaction.transaction_borrowed_slim,0) - ifnull(transaction.transaction_returned_slim,0)< 0,0 ,ifnull(transaction.transaction_borrowed_slim,0) - ifnull(transaction.transaction_returned_slim,0))",
                "ifnull(transaction.transaction_others,0)",
                "ifnull(transaction.transaction_total,0)",
                "ifnull(transaction.transaction_amount,0)",
                "(transaction.transaction_balance)*-1",
                "ifnull(transaction.transaction_change,0)",
                "ifnull(transaction.transaction_date,'0000-00-00')",
                "ifnull(transaction.transaction_remarks,'N/A')",
                "ifnull(transaction.transaction_amount,0) - ifnull(transaction.transaction_change,0)"};

            result = SELECT(column, "customers", column,
                    "LEFT JOIN transaction\n"
                    + "ON customers.customer_id=transaction.customer_id", " WHERE transaction.account_name ='" + account_name + "' "
                    + "AND transaction.transaction_amount > 0 "
                    + "AND transaction.transaction_balance >= 0 "
                    + "AND transaction.status = 'confirm' "
                    + customer + fromDate + toDate + barangay + city + province, "");

        }

        if (type.equalsIgnoreCase("collectibles")) {
            String column[] = {"customers.customer_id",
                "ifnull(CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname),'NONE')",
                "ifnull(customers.customer_gender,'NONE')",
                "ifnull(customers.customer_cellphone_no,'NONE')",
                "ifnull(customers.customer_landline,'NONE')",
                "ifnull(customers.customer_street,'NONE')",
                "ifnull(customers.customer_barangay,'NONE')",
                "ifnull(customers.customer_city_municipality,'NONE')",
                "ifnull(customers.customer_province,'NONE')",
                "ifnull(customers.customer_country,'NONE')",
                "ifnull(transaction.transaction_borrowed_round, 0)",
                "ifnull(transaction.transaction_borrowed_slim,0)",
                "ifnull(transaction.transaction_returned_round, 0)",
                "ifnull(transaction.transaction_returned_slim,0)",
                "if(ifnull(transaction.transaction_borrowed_round, 0) -ifnull(transaction.transaction_returned_round, 0) < 0, 0,ifnull(transaction.transaction_borrowed_round, 0) -ifnull(transaction.transaction_returned_round, 0))",
                "if(ifnull(transaction.transaction_borrowed_slim,0) - ifnull(transaction.transaction_returned_slim,0)< 0,0 ,ifnull(transaction.transaction_borrowed_slim,0) - ifnull(transaction.transaction_returned_slim,0))",
                "ifnull(transaction.transaction_others,0)",
                "ifnull(transaction.transaction_total,0)",
                "ifnull(transaction.transaction_amount,0)",
                "(transaction.transaction_balance)*-1",
                "ifnull(transaction.transaction_change,0)",
                "ifnull(transaction.transaction_date,'0000-00-00')",
                "ifnull(transaction.transaction_remarks,'N/A')",
                "ifnull(transaction.transaction_amount,0) - ifnull(transaction.transaction_change,0)",};

            result = SELECT(column, "customers", column,
                    "LEFT JOIN transaction\n"
                    + "ON customers.customer_id=transaction.customer_id", " WHERE  transaction.account_name ='" + account_name + "' "
                    + "AND transaction.transaction_balance <=0 "
                    + "AND customers.customer_id  <>0 "
                    + "AND transaction.status = 'confirm' "
                    + customer + fromDate + toDate + barangay + city + province, "");

        }
        if (!type.equalsIgnoreCase("collection") && !type.equalsIgnoreCase("collectibles")) {
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
                "ifnull(transaction.transaction_borrowed_round, 0)",
                "ifnull(transaction.transaction_borrowed_slim,0)",
                "ifnull(transaction.transaction_returned_round, 0)",
                "ifnull(transaction.transaction_returned_slim,0)",
                "if(ifnull(transaction.transaction_borrowed_round, 0) -ifnull(transaction.transaction_returned_round, 0) < 0, 0,ifnull(transaction.transaction_borrowed_round, 0) -ifnull(transaction.transaction_returned_round, 0))",
                "if(ifnull(transaction.transaction_borrowed_slim,0) - ifnull(transaction.transaction_returned_slim,0)< 0,0 ,ifnull(transaction.transaction_borrowed_slim,0) - ifnull(transaction.transaction_returned_slim,0))",
                "ifnull(transaction.transaction_others,0)",
                "ifnull(transaction.transaction_total,0)",
                "ifnull(transaction.transaction_amount,0)",
                "(transaction.transaction_balance)*-1",
                "ifnull(transaction.transaction_change,0)",
                "ifnull(transaction.transaction_date,'0000-00-00')",
                "ifnull(transaction.transaction_remarks,'N/A')",
                "ifnull(transaction.transaction_amount,0) - ifnull(transaction.transaction_change,0)"};

            result = SELECT(column, "customers", column,
                    "LEFT JOIN transaction\n"
                    + "ON customers.customer_id=transaction.customer_id", " WHERE  transaction.account_name ='" + account_name + "' "
                    + "AND transaction.status = 'confirm' "
                    + customer + fromDate + toDate + barangay + city + province, "");

        }

        return result;
    }

    protected String[][] viewCollectionCollectiblesGroup(String account_name, String customer, String fromDate, String toDate, String barangay, String city, String province, String type) {
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
            "ifnull(transaction.transaction_date,'0000-00-00')",
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
            "ifnull(transaction.transaction_date,'0000-00-00')",
            "ifnull(transaction.transaction_remarks,'N/A')",
            "sum(ifnull(transaction.transaction_amount,0)) - sum(ifnull(transaction.transaction_change,0))"};

        if (type.equalsIgnoreCase("collection")) {

            result = SELECT(column, "customers", fetch,
                    "LEFT JOIN transaction\n"
                    + "ON customers.customer_id=transaction.customer_id", " WHERE  transaction.account_name ='" + account_name + "' "
                    + "AND transaction_balance =0 "
                    + "AND transaction.status = 'confirm' "
                    + "AND transaction.status = 'confirm' "
                    + customer + fromDate + toDate + barangay + city + province,
                    " GROUP BY customers.customer_id ");

        }

        if (type.equalsIgnoreCase("collectibles")) {

            result = SELECT(column, "customers", fetch,
                    "LEFT JOIN transaction\n"
                    + "ON customers.customer_id=transaction.customer_id", " WHERE  transaction.account_name ='" + account_name + "' "
                    + "AND transaction_balance <= 0 "
                    + "AND customers.customer_id  <>0 "
                    + "AND transaction.status = 'confirm' "
                    + customer + fromDate + toDate + barangay + city + province,
                    " GROUP BY customers.customer_id ");

        }
        if (!type.equalsIgnoreCase("collection") && !type.equalsIgnoreCase("collectibles")) {

            result = SELECT(column, "customers", fetch,
                    "LEFT JOIN transaction\n"
                    + "ON customers.customer_id=transaction.customer_id", " WHERE  transaction.account_name ='" + account_name + "' "
                    + customer + fromDate + toDate + barangay + city + province, " GROUP BY customers.customer_id ");

        }

        return result;
    }
//---------------------------------------------------------------

    protected String[] getFirstname() {

        String column[] = {"customer_id", "customer_firstname"};
        String res[][];

        res = SELECT(column, "customers", column, "", "", "GROUP BY customer_firstname");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getLastname() {

        String column[] = {"customer_id", "customer_lastname"};
        String res[][];

        res = SELECT(column, "customers", column, "", "", "GROUP BY customer_lastname");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getStreet() {

        String column[] = {"customer_id", "customer_street"};
        String res[][];

        res = SELECT(column, "customers", column, "", "", "GROUP BY customer_street");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getCellphone() {

        String column[] = {"customer_id", "customer_cellphone_no"};
        String res[][];

        res = SELECT(column, "customers", column, "", "", "GROUP BY customer_cellphone_no");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getLandline() {

        String column[] = {"customer_id", "customer_landline"};
        String res[][];

        res = SELECT(column, "customers", column, "", "", "GROUP BY customer_landline");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getBarangay() {

        String column[] = {"barangay_id", "barangay"};
        String res[][];

        res = SELECT(column, "barangay", column, "", "", "GROUP BY barangay");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getCity() {

        String column[] = {"city_id", "city"};
        String res[][];

        res = SELECT(column, "cities", column, "", "", "GROUP BY  city");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getProvince() {

        String column[] = {"province_id", "province"};
        String res[][];

        res = SELECT(column, "provinces", column, "", "", "GROUP BY province");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getCountry() {

        String column[] = {"country_id", "country"};
        String res[][];

        res = SELECT(column, "countries", column, "", "", "GROUP BY  country");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[][] viewDeliveryTransaction(String date, String id, String name) {
        String column[] = {
            "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)",
            "transaction.transaction_id",
            "CONCAT(customers.customer_street,' ',customers.customer_barangay,' ',customers.customer_city_municipality,', ',customers.customer_province,' ',customers.customer_country)",
            "customers.customer_cellphone_no",
            "customers.customer_landline",
            "transaction.transaction_basket",
            "ifnull(transaction.transaction_borrowed_round,0) + ifnull(transaction.transaction_borrowed_slim,0)",
            "ifnull(transaction.transaction_total,0)",
            "ifnull(transaction.transaction_amount,0)",
            "(ifnull(transaction.transaction_balance,0)*-1)",
            "ifnull(transaction.transaction_change,0)",
            "ifnull(transaction.transaction_returned_round,0)",
            "ifnull(transaction.transaction_returned_slim,0)",
            "ifnull(transaction.transaction_date,0000-00-00)",
            "transaction.transaction_remarks"};

        String result[][] = SELECT(column, "customers", column, "LEFT JOIN transaction "
                + "ON transaction.customer_id=customers.customer_id", " WHERE transaction.transaction_date = '" + date + "' "
                + "AND transaction.transaction_id = '" + id + "' "
                + "AND transaction.transaction_type = 'Delivery' "
                + "AND transaction.status = 'confirm' "
                + "AND transaction.account_name = '" + name + "'", "");

        return result;
    }

    protected String[][] viewDeliveryTransactionView(String date, String name) {
        String column[] = {
            "CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname)",
            "transaction.transaction_id",
            "CONCAT(customers.customer_street,' ',customers.customer_barangay,' ',customers.customer_city_municipality,', ',customers.customer_province,' ',customers.customer_country)",
            "customers.customer_cellphone_no",
            "customers.customer_landline",
            "transaction.transaction_basket",
            "ifnull(transaction.transaction_borrowed_round,0) + ifnull(transaction.transaction_borrowed_slim,0)",
            "ifnull(transaction.transaction_total,0)",
            "ifnull(transaction.transaction_amount,0)",
            "(ifnull(transaction.transaction_balance,0)*-1)",
            "ifnull(transaction.transaction_change,0)",
            "ifnull(transaction.transaction_returned_round,0)",
            "ifnull(transaction.transaction_returned_slim,0)",
            "ifnull(transaction.transaction_date,0000-00-00)",
            "transaction.transaction_remarks"};

        String result[][] = SELECT(column, "customers", column, "LEFT JOIN transaction "
                + "ON transaction.customer_id=customers.customer_id", " WHERE transaction.transaction_date = '" + date + "' "
                + "AND transaction.transaction_type = 'Delivery' "
                + "AND transaction.status = 'confirm' "
                + "AND transaction.account_name = '" + name + "'", "");

        return result;
    }

    protected String[][] getDiscount(String id) {

        String column[] = {"product_id", "number_product", "discount"};
        String res[][];

        res = SELECT(column, "discounts", column, "", "WHERE customer_id = '" + id + "'"
                + "AND discount_status != 'Deleted'", "");

        return res;
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
