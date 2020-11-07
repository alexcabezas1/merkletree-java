package edu.uade.merkletree;

import java.util.List;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Triplet;


public class Node extends BaseNode {
	
	private String merkleRoot;
	private List<Transaction> transactions;
	
	public Node() {
		super();
		this.merkleRoot = null;
		this.transactions = new ArrayList<Transaction>();
	}
	
	public void addTransaction(Transaction tx) {
		this.transactions.add(tx);
	}
	
	public void buildMerkleRoot() {
		Pair<String, String> hashes = buildMerkleRoot(this.transactions);
		this.merkleRoot = this.getHash(hashes.getValue0() + hashes.getValue1());
	}
	
	private Pair<String, String> buildMerkleRoot(List<Transaction> txs) {
		if (txs.size() == 0) {
			return Pair.with("", "");
		} else if (txs.size() == 1) {
			String left_hash = this.getTransactionHash(txs.get(0));
			return Pair.with(left_hash, left_hash);
		} else if (txs.size() == 2) {
			String left_hash = this.getTransactionHash(txs.get(0));
			String right_hash = this.getTransactionHash(txs.get(1));
			return Pair.with(left_hash, right_hash);
		} else {
			int half_pointer = (int) txs.size() / 2;
			Pair<String, String> left_hashes = this.buildMerkleRoot(txs.subList(0, half_pointer));
			Pair<String, String> right_hashes = this.buildMerkleRoot(txs.subList(half_pointer, txs.size()));
			
			String left_hash = this.getHash(left_hashes.getValue0() + left_hashes.getValue1());
			String right_hash = this.getHash(right_hashes.getValue0() + right_hashes.getValue1());
			return Pair.with(left_hash, right_hash);
		}
	}

	public String[] getMerklePath(String txid) {
		Triplet<String, Boolean, String[]> result = this.getMerklePath(txid, this.transactions);
		return result.getValue2();
	} 
	
	private Triplet<String, Boolean, String[]> getMerklePath(String txid, List<Transaction> txs) {
		Triplet<String, Boolean, String[]> result;
		if (txs.size() == 0) {
			result = Triplet.with("", false, new String[]{});
		}else if (txs.size() == 1) {
			String left_hash = this.getTransactionHash(txs.get(0));
			String root_hash = this.getHash(left_hash + left_hash);
			if (txs.get(0).getTxid() == txid) {
				result = Triplet.with(root_hash, true, new String[]{"L" + left_hash});
			} else {
				result = Triplet.with(root_hash, false, new String[]{});
			}
		}else if (txs.size() == 2) {
			String left_hash = this.getTransactionHash(txs.get(0));
			String right_hash = this.getTransactionHash(txs.get(1));
			String root_hash = this.getHash(left_hash + right_hash);
			if (txs.get(0).getTxid() == txid) {
				result = Triplet.with(root_hash, true, new String[]{"R" + right_hash});
			} else if (txs.get(1).getTxid() == txid) {
				result = Triplet.with(root_hash, true, new String[]{"L" + left_hash});
			} else {
				result = Triplet.with(root_hash, false, new String[]{});
			}
		}else {
			int half_pointer = (int) txs.size() / 2;
			Triplet<String, Boolean, String[]> left_result = this.getMerklePath(txid, txs.subList(0, half_pointer));
			Triplet<String, Boolean, String[]> right_result = this.getMerklePath(txid, txs.subList(half_pointer, txs.size()));
			String root_hash = this.getHash(left_result.getValue0() + right_result.getValue0());
			
			if (left_result.getValue1()) {
				String[] left_path = this.addElement(left_result.getValue2(), "R" + right_result.getValue0());
				result = Triplet.with(root_hash, true, left_path);
				return result;
			} else if (right_result.getValue1()) {
				String[] right_path = this.addElement(right_result.getValue2(), "L" + left_result.getValue0());
				result = Triplet.with(root_hash, true, right_path);
				return result;
			}
			
			result = Triplet.with(root_hash, false, new String[]{});
		}
		return result;
	}
	
	public String getMerkleRoot() {
		return merkleRoot;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	
}
