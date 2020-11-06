package edu.uade.merkletree;

public class Transaction {
	
	private String txid;
	private double value;
	
	public Transaction(String txid, double value) {
		this.txid = txid;
		this.value = value;
	}

	public String getTxid() {
		return txid;
	}

	public double getValue() {
		return value;
	}
	
	public String toString() {
		return "(txid: " + this.txid + ", value: " + this.value + ")";
	}
}
