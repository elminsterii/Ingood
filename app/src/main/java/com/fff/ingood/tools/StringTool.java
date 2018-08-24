package com.fff.ingood.tools;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class StringTool {

    public static InputFilter getInputFilterForEditText() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                char[] chars = {'\'','"','\\'};
                for (int i = start; i < end; i++) {
                    if (new String(chars).contains(String.valueOf(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };
    }

    public static String strTagsToRegExp(String strTags) {
        if(strTags == null || strTags.isEmpty())
            return "";

        StringBuilder strBuilder = new StringBuilder();
        String[] arrString = strTags.split(",");

        if(arrString.length <= 0)
            return "";

        for(String str : arrString) {
            strBuilder.append("[[:<:]]").append(str).append("[[:>:]]");
            strBuilder.append("|");
        }

        if(strBuilder.length() > 0)
            strBuilder.deleteCharAt(strBuilder.length()-1);

        return strBuilder.toString();
    }

    public static boolean checkStringNotNull(String str) {
        boolean bRes = false;
        if (str != null && !str.isEmpty())
            bRes = true;
        return bRes;
    }

    public static String listStringToString(List<String> lsString, char splitChar) {
        if(lsString == null || lsString.isEmpty())
            return "";

        StringBuilder strBuilder = new StringBuilder();
        for(String str : lsString) {
            if(checkStringNotNull(str))
                strBuilder.append(str).append(splitChar);
        }

        if(strBuilder.length() > 0)
            strBuilder.deleteCharAt(strBuilder.length()-1);

        return strBuilder.toString();
    }

    public static String listEditTextToString(List<EditText> lsEditTexts, char splitChar) {
        if(lsEditTexts == null || lsEditTexts.isEmpty())
            return "";

        StringBuilder strBuilder = new StringBuilder();
        for(EditText editText : lsEditTexts) {
            strBuilder.append(editText.getText().toString()).append(splitChar);
        }

        if(strBuilder.length() > 0)
            strBuilder.deleteCharAt(strBuilder.length()-1);

        return strBuilder.toString();
    }

    public static String arrayStringToString(String[] arrString, char splitChar) {
        if(arrString == null || arrString.length <= 0)
            return "";

        StringBuilder strBuilder = new StringBuilder();
        for(String str : arrString) {
            if(checkStringNotNull(str))
                strBuilder.append(str).append(splitChar);
        }

        if(strBuilder.length() > 0)
            strBuilder.deleteCharAt(strBuilder.length()-1);

        return strBuilder.toString();
    }

    public static String addStatusCode(String strJson, int iCode) {
        if (strJson == null)
            return "";

        StringBuilder strBuilder = new StringBuilder(strJson);
        if (!strJson.isEmpty())
            strBuilder.insert(1, "\"statuscode\":" + iCode + ",");
        else
            strBuilder.insert(0, "\"statuscode\":" + iCode);

        strBuilder.trimToSize();

        return strBuilder.toString();
    }

    public static List<String> arrayStringToListString(String[] arrString) {
        return new ArrayList<>(Arrays.asList(arrString));
    }

    public static String cutStringBySymbolAndRange(String[] arrString, int startIndex, int endIndex, String strSplitSymbol) {
        StringBuilder bRes = new StringBuilder();

        for(int i=startIndex; i<=endIndex; i++) {
            bRes.append(arrString[i]);
            bRes.append(strSplitSymbol);
        }

        if(bRes.length() > 0)
            bRes.deleteCharAt(bRes.length()-1);

        return bRes.toString();
    }
}