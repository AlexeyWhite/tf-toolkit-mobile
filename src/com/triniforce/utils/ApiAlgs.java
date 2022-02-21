/* 
 * Copyright(C) Triniforce
 * All Rights Reserved.
 * 
 */
package com.triniforce.utils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

import com.triniforce.utils.IProfilerStack.PSI;

public class ApiAlgs {
	
    @SuppressWarnings("serial")
    public static class RethrownException extends RuntimeException{
        public RethrownException(Throwable t) {
            super(t);
            ApiAlgs.assertNotNull(t, "cause");
        }
        
        @Override
        public void printStackTrace(PrintStream s) {
        	getCause().printStackTrace(s);
        }
        
        public void printStackTrace(PrintWriter s) {
        	getCause().printStackTrace(s);
        }

    }
    public static void rethrowException(Exception e) throws RuntimeException{
        //writeExceptionInfo(e, "handleException"); gmp: is it not really needed, will
        //be reported at the top
        if( e instanceof RuntimeException)throw (RuntimeException)e;
        throw new RethrownException(e);
    }
    
    public static void assertNotNull(Object value, String name){
        TFUtils.assertNotNull(value, name);
    }   
    
    
    
    public static void assertEquals(Object expected, Object actual){
        TFUtils.assertEquals(expected, actual);
    }
    public static void assertTrue(boolean expr, String msg){
        TFUtils.assertTrue(expr, msg);
    }
    
    public static PSI getProfItem(String grp, String item){
        IProfilerStack stack = ApiStack.getApi().queryIntfImplementor(IProfilerStack.class);
        if( null == stack ){
            return null;
        }
        return stack.getStackItem(grp, item);
    }
    public static void closeProfItem(PSI psi){
        if( null == psi) return;
        psi.close();
    }   
}
