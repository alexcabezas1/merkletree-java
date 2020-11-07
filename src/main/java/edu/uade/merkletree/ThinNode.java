package edu.uade.merkletree;

public class ThinNode extends BaseNode {
	private Node fullNode;
	
	public ThinNode(Node fullNode) {
		super();
		this.fullNode = fullNode;
	}
	
	public String verifyTransaction(Transaction tx) {
		String[] merkle_path = this.fullNode.getMerklePath(tx.getTxid());
		String accum_hash = this.getTransactionHash(tx);
		
		for (String hash_op : merkle_path) {
			char op = hash_op.charAt(0);
			String hash = hash_op.substring(1, hash_op.length());
			if (op == 'L') {
				accum_hash = this.getHash(hash + accum_hash);
			} else if (op == 'R') {
				accum_hash = this.getHash(accum_hash + hash);
			}
		}
		
		return accum_hash;
	}
	
	public Boolean isValidTransaction(Transaction tx) {
		String result = this.verifyTransaction(tx);
		return this.fullNode.getMerkleRoot().equals(result);
	}
	
}
