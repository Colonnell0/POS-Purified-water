/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Colonnello
 */
public class FormatNumber {

    public String formatNumbers(String input) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        NumberFormat nf = NumberFormat.getInstance();
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String g = m.group();
            m.appendReplacement(sb, nf.format(Double.parseDouble(g)));
        }
        return m.appendTail(sb).toString();
    }

   
}
