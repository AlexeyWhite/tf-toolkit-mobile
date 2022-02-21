/* 
 * Copyright(C) Triniforce
 * All Rights Reserved.
 * 
 */
package com.triniforce.utils;

import java.text.MessageFormat;

import com.triniforce.utils.EUtils.EAssertionFailed;

public class EUtils {
    public static class EAssertionFailed  extends RuntimeException{
		private static final long serialVersionUID = 1L;
		public EAssertionFailed(String msg) {
			super(msg);
		}
    }
    
    public static class EAssertNotNullFailed extends EAssertionFailed {
        private static final long serialVersionUID = 1L;
        public EAssertNotNullFailed(String msg) {
            super(msg);
        }
    }   
    public static class EAssertEqualsFailed extends EAssertionFailed {
        private static final long serialVersionUID = 1L;
        public EAssertEqualsFailed(String msg) {
        	super(msg);
        }
        public EAssertEqualsFailed(String prefix, Object expected, Object actual) {
        	super(MessageFormat.format("{0}expected: <{1}> but: <{2}>"
        			, TFUtils.msgPrefix(prefix), expected, actual));
        }
        public EAssertEqualsFailed(Object expected, Object actual) {
            this("", expected, actual);
        }
    }
    public static class EAssertTrueFailed extends EAssertionFailed {
		private static final long serialVersionUID = 1L;
		public EAssertTrueFailed(String msg) {
            super(msg);
        }
    }
}
