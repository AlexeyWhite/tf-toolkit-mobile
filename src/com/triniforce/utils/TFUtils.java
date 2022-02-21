/* 
 * Copyright(C) Triniforce
 * All Rights Reserved.
 * 
 */
package com.triniforce.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;

public class TFUtils {
	public static void copyStream(InputStream in, OutputStream out) {
	    try {
	        byte[] buffer = new byte[1024];
            while (true) {
                int readCount = in.read(buffer);
                if (readCount < 0) {
                    break;
                }
                out.write(buffer, 0, readCount);
            }
        } catch (Exception e) {
            ApiAlgs.rethrowException(e);
        }
    }

	 public static void copyStream(File file, OutputStream out) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                copyStream(in, out);
            } finally {
                in.close();
            }
        } catch (Exception e) {
            ApiAlgs.rethrowException(e);
        }
    }

    public static void copyStream(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            try {
                copyStream(in, out);
            } finally {
                out.close();
            }
        } catch (Exception e) {
            ApiAlgs.rethrowException(e);
        }
    }
    

    public static void assertNotNull(Object value, String name){
        if(null != value) return;
        throw new EUtils.EAssertNotNullFailed(MessageFormat.format("Unexpected null value for \"{0}\"",name)); //$NON-NLS-1$
    }
    
    
    public static void assertEquals(Object expected, Object actual){
    	assertEquals("", expected, actual);
    }

    public static void assertTrue(boolean expr, String msg){
        if(!expr){
            throw new EUtils.EAssertTrueFailed(msg);
        }
    }
    
    public static void assertEquals(String prefix, Object expected, Object actual){
        if( expected == actual ) return;
        if( expected == null || actual == null){
            throw new EUtils.EAssertEqualsFailed(prefix, expected, actual);
        }
        if(expected instanceof List){
        	expected = ((List)expected).toArray();
        }
        if(actual instanceof List){
        	actual  = ((List)actual).toArray();
        }        
        if(expected instanceof Object[]){
        	if(! (actual instanceof Object[])){
        		throw new EUtils.EAssertEqualsFailed("Array vs not array", expected.getClass(), actual.getClass());        		
        	}
        	assertEqualArrays(prefix, (Object[])expected, (Object[])actual);
        	return;
        }
        if(TFUtils.equals(expected, actual))return;
        throw new EUtils.EAssertEqualsFailed(prefix, expected, actual);
    }
    
    public static void assertEqualArrays(String prefix, Object expected[], Object actual[]){
    	if(expected.length != actual.length){
    		throw new EUtils.EAssertEqualsFailed(msgPrefix(prefix) + "different size", expected.length, actual.length);    		
    	}
		for (int idx = 0; idx < expected.length; idx++) {
			assertEquals(prefix + "[" + idx+"]", expected[idx], actual[idx]);
		}
    }
	public static boolean equals(Object expected, Object actual) {
	    if (expected == actual)
	        return true;
	    if (expected == null || actual == null)
	        return false;
	    
	    if(expected instanceof Number){
	        if(! (actual instanceof Number)){
	            if(! (actual instanceof String)){
	                return false;
	            }else{
	                try{
	                    actual = Long.parseLong((String) actual);
	                }catch(Throwable t){
	                	Logger.getLogger(TFUtils.class.getName()).info("parseLong() error: " + actual);
	                    return false;
	                }
	            }
	        }
	        return TFUtils.asLong(expected).equals(TFUtils.asLong(actual));
	    }
	    
	    return expected.equals(actual);
	}
	public static Long asLong(Object value){
	    if( null == value )return null;
	    if( value instanceof Number){
            return ((Number)value).longValue();
        }
	    if( value instanceof String){
            return Long.parseLong((String) value);
        }
	    TFUtils.assertTrue(false, "Unknown type for asLong " + value.getClass());
	    return 0L;
	}
	public static boolean isEmptyString(String s) {
	    if (null == s)
	        return true;
	    if (s.length() == 0)
	        return true;
	    return false;
	}
    public static String msgPrefix(String prefix){
    	return TFUtils.isEmptyString(prefix)?"" :prefix +": ";
    }
}
