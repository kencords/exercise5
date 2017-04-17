package ecc.cords;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Table{

	private List<Row> table = new ArrayList<>();
	private Set<String> keys = new HashSet<>();

	public List<Row> getRowList(){
		return table;
	}

	public Set<String> getKeys(){
		return keys;
	}

	public int getSize(){
		return table.size();
	}

	public void setTable(List<Row> table){
		this.table = table;
	}

	public void setKeys(Set<String> keys){
		this.keys = keys;
	}
}