package org.vasileirimia;

import java.security.MessageDigest;

/**
 *
 */
public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timestamp;
    int nonce;
    public static int difficulty = 5;

    public Block(String previousHash, String data, long timestamp) {
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = timestamp;

        mineBlock(difficulty);
    }

    public String computeHash() {
        return applySha256(previousHash + Long.toString(timestamp) + Integer.toString(nonce) + data);
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        do{
            nonce++;
            hash = computeHash();
        }while(!hash.substring( 0, difficulty).equals(target));

        System.out.println("Block Mined!!! : " + hash);
    }
}
