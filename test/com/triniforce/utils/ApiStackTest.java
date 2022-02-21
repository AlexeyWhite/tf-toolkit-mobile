/* 
 * Copyright(C) Triniforce 
 * All Rights Reserved. 
 * 
 */ 

package com.triniforce.utils;


import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;

public class ApiStackTest extends TestCase {
	
	public static interface MyIntf1{};
	public static interface MyIntf2{};
	public static interface MyIntf3{};
	
	public static class MyImplementor1{};
	public static class MyImplementor2{};
	public static class MyImplementor3{};
	
	static class Test1{}
	
	public void testPushInterface(){
		Class<Test1> testCls = Test1.class;
	    assertNull(ApiStack.queryInterface(testCls));
	    assertNull(ApiStack.queryInterface(testCls));
	    ApiStack.pushInterface(testCls, this);
	    ApiStack.pushInterface(ApiStackTest.class, this);
	    assertEquals(this, ApiStack.getInterface(ApiStackTest.class));
	    assertEquals(this, ApiStack.getInterface(testCls));
	    ApiStack.popInterface(1);
        assertEquals(null, ApiStack.queryInterface(ApiStackTest.class));
        assertEquals(this, ApiStack.getInterface(testCls));
        ApiStack.popInterface(1);
        assertEquals(null, ApiStack.queryInterface(ApiStackTest.class));
        assertEquals(null, ApiStack.queryInterface(testCls));
	}
	
	public void test(){
		ApiStack as = new ApiStack();
		assertEquals(0, as.getImplementors().size());
		
		Api api1 = new Api();
		api1.setIntfImplementor(MyIntf2.class, new MyImplementor1());
		api1.setIntfImplementor(MyIntf1.class, new MyImplementor2());
		as.getStack().push(api1);
		assertEquals(2, as.getImplementors().size());
		
		assertTrue( as.getImplementors().containsKey(MyIntf1.class));
		assertTrue( as.getImplementors().containsKey(MyIntf2.class));
		assertFalse(as.getImplementors().containsKey(MyIntf3.class));
		
		Api api2 = new Api();
		api2.setIntfImplementor(MyIntf3.class, new MyImplementor3());
		as.getStack().push(api2);
		assertEquals(3, as.getImplementors().size());
		assertTrue( as.getImplementors().containsKey(MyIntf1.class));
		assertTrue( as.getImplementors().containsKey(MyIntf2.class));
		assertTrue( as.getImplementors().containsKey(MyIntf3.class));
		
		trace(as.toString());
		
				
	}

	private void trace(String string) {
		Logger.getLogger(getClass().getName()).log(Level.FINE, string);
		
	}

}
