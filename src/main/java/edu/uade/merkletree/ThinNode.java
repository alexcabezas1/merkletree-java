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
		
		for (String hash : merkle_path) {
			if (hash.charAt(0) == 'L') {
				System.out.println(hash.substring(1, hash.length()));
				accum_hash = this.getHash(hash.substring(1, hash.length()) + accum_hash);
			} else if (hash.charAt(0) == 'R') {
				System.out.println(hash.substring(1, hash.length()));
				accum_hash = this.getHash(accum_hash + hash.substring(1, hash.length()));
			}
		}
		
		return accum_hash;
	}
	
	public Boolean isValidTransaction(Transaction tx) {
		String result = this.verifyTransaction(tx);
		return this.fullNode.getMerkleRoot().equals(result);
	}
	
}
