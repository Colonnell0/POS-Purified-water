/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Colonnello
 */
public class FormValidation {

    public boolean maxLength(int data, int arg) {
        boolean tf = false;

        if (data <= arg) {
            tf = true;
        } else {
            tf = false;
        }
        return tf;
    }

    public boolean minLength(int data, int arg) {
        boolean tf = false;

        if (data >= arg) {
            tf = true;
        } else {
            tf = false;
        }
        return tf;
    }

    public boolean empty(String data) {
        boolean tf = false;
        if (data.isEmpty()) {
            tf = true;
        } else {
            tf = false;
        }

        return tf;
    }

    public long numberOfDays(String from, String to) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        long d1 = formater.parse(to).getTime();
        long d2 = formater.parse(from).getTime();

        return Math.abs((d1 - d2) / (1000 * 60 * 60 * 24));
    }

}
