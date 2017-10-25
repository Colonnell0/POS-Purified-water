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
public class Login_Model extends Database {

    protected String[][] login_account(String username, String password) {
        String result[][];
        String column[] = {
            "account_id",
            "CONCAT(account_firstname,' ',account_middle_initial,'. ',account_lastname)",
            "account_username",
            "account_password", "account_type_id"};

        result = SELECT(column, "accounts", column, "", "WHERE account_username ='" + username + "' "
                + "AND account_password = '" + password + "' "
                + "AND account_status = 'Enable' "
                + "AND account_key = 'FMbJpSusGJYCxydXRezxjQ== - v99xUxa77O - fZqnJ7sWV0 - X98RXrsDTZ - oIftz76VYT - 7ZBt80ioNO - BjQzR5DTnA'", "");

        return result;
    }

    protected String time_date(String date) {

        String column[] = {"MAX(transaction_id)","DATEDIFF('"+date+"',transaction_date)"};

        String res[][];

        res = SELECT(column, "transaction", column, "", "", "");
        String result = "";

        result = res[0][1];

        return result;
    }

}
