/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Console 
{
    private BufferedReader br;
    private PrintStream ps;

    public Console(){
        br = new BufferedReader(new InputStreamReader(System.in));
        ps = System.out;
    }

    public String readLine(String out){
        ps.format(out);
        try{
            return br.readLine();
        }catch(IOException e)
        {
            return null;
        }
    }
    public PrintStream format(String format, Object...objects){
        return ps.format(format, objects);
    }
}