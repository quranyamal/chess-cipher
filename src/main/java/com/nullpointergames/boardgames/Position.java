package com.nullpointergames.boardgames;

public class Position {

	private char col;
	private int row;

	public Position(char col, int row) {
		this.col = col;
		this.row = row;
	}

	public Position(int idx){
		idx = idx&63;
		col = (char) ('a'+(idx&7));
		row = idx>>3;
		++row;
	}
	
	public char col() {
		return col;
	}
	
	public int row() {
		return row;
	}

	public void setCol(char col) {
		this.col = col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void set(char col, int row){
		setCol(col);
		setRow(row);
	}

	public double getDistance(Position other){
		double dcol = Math.abs(col-other.col());
		double drow = Math.abs(row-other.row());
		return Math.sqrt(dcol*dcol+drow*drow);
	}

	@Override
	public boolean equals(Object obj) {
		Position anotherPosition = (Position) obj;
		return anotherPosition.col == col && anotherPosition.row == row;
	}

	@Override
	public String toString() {
		return "Position{" +
				"col=" + col +
				", row=" + row +
				'}';
	}
}
