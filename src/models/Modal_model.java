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
public class Modal_model extends Database {

    protected String getBackgroundColor(String color) {
        return "-fx-background-radius:5em;\n"
                + "	-fx-border-width:2px;\n"
                + "	-fx-border-radius:5em ;\n"
                + "	-fx-border-color:" + color + ";";
    }

    /**
     *
     * @param border
     * @param background
     * @return
     */
    protected String getBackgroundColorBorderBottom(String color) {
        return "-fx-background-color: white;\n"
                + "	-fx-border-width:0 0 2 0;\n"
                + "	-fx-border-color:" + color + ";";
    }

    protected String getTextFillColor(String color) {
        return " -fx-text-fill:" + color + ";";
    }

    protected String[] getBarangay() {

        String column[] = {"barangay_id", "barangay"};
        String res[][];

        res = SELECT(column, "barangay", column, "", "", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getCity() {

        String column[] = {"city_id", "city"};
        String res[][];

        res = SELECT(column, "cities", column, "", "", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getCity(String barangay) {

        String column[] = {"barangay.barangay", "cities.city"};
        String res[][];

        res = SELECT(column, "barangay", column,
                "LEFT JOIN cities\n"
                + "ON barangay.city_id=cities.city_id", " WHERE barangay = '" + barangay + "'", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getProvince() {

        String column[] = {"province_id", "province"};
        String res[][];

        res = SELECT(column, "provinces", column, "", "", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getProvince(String City) {

        String column[] = {"cities.city", "provinces.province"};
        String res[][];

        res = SELECT(column, "cities", column,
                "LEFT JOIN provinces\n"
                + "ON cities.province_id=provinces.province_id", " WHERE city = '" + City + "'", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getCountry() {

        String column[] = {"country_id", "country"};
        String res[][];

        res = SELECT(column, "countries", column, "", "", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }

        return result;
    }

    protected String[] getCountry(String Province) {

        String column[] = {"provinces.province", "countries.country"};
        String res[][];

        res = SELECT(column, "provinces", column,
                "LEFT JOIN countries\n"
                + "ON provinces.country_id=countries.country_id", " WHERE province = '" + Province + "'", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

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

    protected String[] getLandlineAreaCode(String city) {
        String column[] = {"city", "area_code_landline"};
        String res[][];

        res = SELECT(column, "cities", column, "", " WHERE city = '" + city + "'", "");
        String result[] = new String[res.length];

        for (int r = 0; r < res.length; r++) {
            result[r] = res[r][1];

        }
        return result;

    }

    protected String getInputPrice(String p) {
        String price = "";

        return price;
    }

    /**
     *
     * @param tableName
     * @param column
     * @param values
     */
    protected void setInsertCustomer(String tableName, String column[], String values[]) {

        INSERT(tableName, column, values);
    }
     protected void setUpdateCustomer(String column[], String values[], String id) {

        UPDATE("customers", column, values, "WHERE customer_id = " + id);

    }
     
    protected void setDeleteCustomer(String table, String column, String id) {
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

}
