package edu.uade.app;

import java.util.Arrays;

import edu.uade.merkletree.*;

public class App 
{
    public static void main( String[] args )
    {
    	
    	Node fullNode = new Node();
    	
    	fullNode.addTransaction(new Transaction("123", 2000.5));
    	fullNode.addTransaction(new Transaction("456", 2000.5));
    	fullNode.addTransaction(new Transaction("789", 2000.5));
    	System.out.println(fullNode.getTransactions());
    	
    	fullNode.buildMerkleRoot();
    	System.out.println(fullNode.getMerkleRoot());
    	
    	
    	String[] merkle_path = fullNode.getMerklePath("123");
    	System.out.println(Arrays.deepToString(merkle_path));
    }
}
