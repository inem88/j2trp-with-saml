/*
   Copyright 2015 Daniel Roig

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.j2trp.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public final class Toolbox {
	
	private Toolbox() { }
	
	public static <T> T getValue (Object orgValue, Object overriddenValue, Class<T> clazz) {
		return getValue(orgValue, overriddenValue, null, clazz);
	}
	
	public static <T> T getValue (Object orgValue, Object overriddenValue, Object defaultValue, Class<T> clazz) {
		if (overriddenValue == null) {
			
			if (orgValue != null) {
				return clazz.cast(orgValue);
			}
			else {
				return clazz.cast(defaultValue);
			}
			
		}
		
		return clazz.cast(overriddenValue);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T merge (Object[] orgValue, Object[] overriddenValue, Class<T> clazz) {

		if (overriddenValue == null) {
			return clazz.cast(orgValue);
		}
		
		int overriddenLength = overriddenValue.length;
		T[] result = (T[]) Array.newInstance(clazz.getComponentType(), orgValue.length + overriddenLength);

		System.arraycopy(orgValue, 0, result, 0, orgValue.length);
		System.arraycopy(overriddenValue, 0, result, orgValue.length, overriddenLength);
		
		return clazz.cast(result);
	}
	
	public static <T> Enumeration<T> safeEnumerator (Collection<T> coll) {
		
		if (coll == null) {
			return new EmptyEnumeration<T>();
		}
		
		return Collections.enumeration(coll);
	}
	
	public static <T> Enumeration<T> mergeCollection (Enumeration<T> orgValue, Enumeration<T> overriddenValue) {

		if (overriddenValue == null) {
			return orgValue;
		}
		
		List<T> result = new ArrayList<T>();
		
		while (orgValue != null && orgValue.hasMoreElements()) {
			result.add(orgValue.nextElement());
		}
		
		while (overriddenValue.hasMoreElements()) {
			result.add(overriddenValue.nextElement());
		}
		
		return Collections.enumeration(result);
	}
	
}
