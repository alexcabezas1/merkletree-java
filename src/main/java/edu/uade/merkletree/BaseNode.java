package edu.uade.merkletree;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;


public abstract class BaseNode {
	
	public String getHash(String value) {
    	return Hashing.sha256()
  			  .hashString(value, StandardCharsets.UTF_8)
  			  .toString();
	}
	
	public String getTransactionHash(Transaction tx) {
		return this.getHash(tx.getTxid() + String.valueOf(tx.getValue()));
	}	
	
	public String[] concat(String[] array1, String[] array2) {
		int length = array1.length + array2.length;
	
		String[] result = new String[length];
		int pos = 0;
		for (String element : array1) {
		    result[pos] = element;
		    pos++;
		}
		
		for (String element : array2) {
		    result[pos] = element;
		    pos++;
		}
		return result;
	}
	
	public String[] addElement(String[] array1, String newElement) {
		int length = array1.length + 1;
	
		String[] result = new String[length];
		int pos = 0;
		for (String element : array1) {
		    result[pos] = element;
		    pos++;
		}
		result[length-1] = newElement; 
		return result;
	}
}
