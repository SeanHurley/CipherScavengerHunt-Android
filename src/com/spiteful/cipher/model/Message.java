package com.spiteful.cipher.model;

public class Message {
	private int id;
	private String encoded;
	private String decoded; 
	private int level;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEncoded() {
		return encoded;
	}
	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}
	public String getDecoded() {
		return decoded;
	}
	public void setDecoded(String decoded) {
		this.decoded = decoded;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
