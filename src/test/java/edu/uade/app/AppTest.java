package edu.uade.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.uade.merkletree.Node;
import edu.uade.merkletree.ThinNode;
import edu.uade.merkletree.Transaction;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	private Node prepareFullNode() {
    	Node fullNode = new Node();
    	
    	Transaction tx1 = new Transaction("1", 78.95);
    	Transaction tx2 = new Transaction("2", 61.79);
    	Transaction tx3 = new Transaction("3", 66.16);
    	Transaction tx4 = new Transaction("4", 93.1);
    	Transaction tx5 = new Transaction("5", 4.07);
    	Transaction tx6 = new Transaction("6", 47.26);
    	Transaction tx7 = new Transaction("7", 22.66);
    	Transaction tx8 = new Transaction("8", 89.13);
    	Transaction tx9 = new Transaction("9", 17.07);
    	Transaction tx10 = new Transaction("10", 10.77);
    	Transaction tx11 = new Transaction("11", 9.55);
    	Transaction tx12 = new Transaction("12", 12.28);
    	
    	fullNode.addTransaction(tx1);
    	fullNode.addTransaction(tx2);
    	fullNode.addTransaction(tx3);
    	fullNode.addTransaction(tx4);
    	fullNode.addTransaction(tx5);
    	fullNode.addTransaction(tx6);
    	fullNode.addTransaction(tx7);
    	fullNode.addTransaction(tx8);
    	fullNode.addTransaction(tx9);
    	fullNode.addTransaction(tx10);
    	fullNode.addTransaction(tx11);
    	fullNode.addTransaction(tx12);
    	
    	fullNode.buildMerkleRoot();
  
    	
    	return fullNode;
	} 
	
    @Test
    public void shouldMerkleRootBeEqual()
    {
    	Node fullNode = this.prepareFullNode();
    	
    	assertTrue(fullNode.getMerkleRoot().equals("773ee70617080bf75f520f41c30c21c12c4ce5ef2420567b08f8e876e614fec4"));
    }
    
    @Test
    public void shouldMerklePathBeEqual() {
    	String[] validMerklePath = new String[] {
    		"Lbe0f4a7e17d50f8ede3812bb683fddc09f5c1b0d48cc21e4a487870528aca3c1", 
    		"L5beafde4c70f0e7965bdfa7b9845c5a459546f0dff5e254c372df702f9782d20", 
    		"Le6efb5bb5b42c9ed151253de209cd56a5aa3b22252bf6e6a921d7283a28ea489", 
    		"L9d54450bce23cc16775136b363ea2dbf3d9e9a423b71452cf3f7dd53553a005a"
    	};
    	
    	Node fullNode = this.prepareFullNode();
    	List<Transaction> txs = fullNode.getTransactions();
    	Transaction tx12 = txs.get(txs.size()-1);
    	
    	String[] merkle_path = fullNode.getMerklePath(tx12.getTxid());
    	
    	assertArrayEquals(validMerklePath, merkle_path);
    }
    
    @Test
    public void shouldTransactionBeValid() {
    	Node fullNode = this.prepareFullNode();
    	ThinNode thinNode = new ThinNode(fullNode);
    	
    	Transaction tx12 = new Transaction("12", 12.28);
    	
    	String verified_root = thinNode.verifyTransaction(tx12);
    	
    	assertTrue(fullNode.getMerkleRoot().equals(verified_root));
    }
    
    @Test
    public void shouldTransactionBeInvalid() {
    	Node fullNode = this.prepareFullNode();
    	ThinNode thinNode = new ThinNode(fullNode);
    	
    	Transaction invalidTx = new Transaction("9999", 9999.0);
    	
    	String verified_root = thinNode.verifyTransaction(invalidTx);
    	
    	assertFalse(fullNode.getMerkleRoot().equals(verified_root));
    }
}
