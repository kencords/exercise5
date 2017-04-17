package ecc.cords;

import java.util.Collections;
import java.util.Comparator;
import static java.util.stream.Collectors.*;

public class TableManager{

	private Table table;

	public TableManager(){
		table = new Table();
	}

	public int createTable(){
		String txt = "";
		int duplicates = 0;

		txt = FileHandler.readFile();

		String[] lines = txt.split("\n");
		String delimiter = lines[0];

		for(int lineIndex = 1; lineIndex < lines.length; lineIndex++){
			String[] cellArray = lines[lineIndex].split(delimiter.trim());
			table.getRowList().add(new Row());
			int rowIndex = lineIndex - 1;
			int colIndex = 0;
			for(int index = 0; index < cellArray.length; index +=2, colIndex++){
				if(table.getKeys().contains(cellArray[index].trim())){
					duplicates++;
					continue;
				}
				addCell(rowIndex, cellArray[index].trim(), cellArray[index+1].trim(), false);
			}
		}
		return duplicates;
	}

	public void addCell(int rowIndex, String key, String value, boolean save) throws IndexOutOfBoundsException{
		if(save){
			ServiceUtils.isValidIndex(rowIndex, table.getSize()+1, "y");
			if(rowIndex == table.getSize()){
				table.getRowList().add(new Row());
			}
		}
		table.getKeys().add(key);
		table.getRowList().get(rowIndex).getCells().add(new Cell(key, value));
		if(save){
			ServiceUtils.saveToFile(table);
		}
	}

	public void editKey(int rowIndex, int colIndex, String key){
		Cell cell = table.getRowList().get(rowIndex).getCells().get(colIndex);
		table.getKeys().remove(cell.getKey());
		cell.setKey(key);
		ServiceUtils.saveToFile(table);
	}

	public void editValue(int rowIndex, int colIndex, String value){
		table.getRowList().get(rowIndex).getCells().get(colIndex).setValue(value);
		ServiceUtils.saveToFile(table);
	}

	public String search(String pattern){
		Row row;
		String key, value, keyMsg, valueMsg;
		StringBuilder result = new StringBuilder();
		int keyCount, valueCount;
		
		result.append("\n");
		for(int rowIndex = 0; rowIndex < table.getSize(); rowIndex++){
			row = table.getRowList().get(rowIndex);
			for(int colIndex = 0; colIndex < row.getSize(); colIndex++){
				key = row.getCells().get(colIndex).getKey();
				value = row.getCells().get(colIndex).getValue();
				keyCount = ServiceUtils.countPatternOccurence(key, pattern);
				valueCount = ServiceUtils.countPatternOccurence(value, pattern);
				keyMsg =  keyCount + (keyCount > 1? " instances" : " instance") + " in Key and ";
				valueMsg =  valueCount + (valueCount > 1? " instances" : " instance") + " in Value";
				result.append(keyCount + valueCount > 0 ? (rowIndex + ", " + colIndex + " with " + keyMsg + valueMsg + "\n") : "");
			}
		}
		return result.toString();
	}

	public void sort(boolean asc){
		Comparator<Cell> cellComparator = (cell1, cell2) -> {
											String cell1Concat = (cell1.getKey() + cell1.getValue()).toLowerCase();
											String cell2Concat = (cell2.getKey() + cell2.getValue()).toLowerCase();
											if(cell1Concat.equals(cell2Concat)){
									    		return cell1.getKey().compareTo(cell2.getKey());
											}	
											return cell1Concat.compareTo(cell2Concat);
										};
		table.getRowList().forEach(row -> Collections.sort(row.getCells() , (asc? cellComparator : cellComparator.reversed())));
	}

	public String getTableData(){
		StringBuilder data = new StringBuilder();
		table.getRowList().forEach(row -> {
							row.getCells().forEach(cell -> {
										 		data.append("  [" + cell.getKey() + " , " + cell.getValue() + "] ");
										 	});
							data.append("\n");
						 });
		return data.toString();
	}

	public int validateCoordinate(String var, int... vals) throws IndexOutOfBoundsException{
		ServiceUtils.isValidIndex(vals[0], (var.equals("y")? table.getSize() : table.getRowList().get(vals[1]).getSize()), var);
		return vals[0];
	}

	public String validateKey(String key) throws Exception{
		if(!table.getKeys().add(key)){
			if(table.getRowList().get(table.getSize()-1).getSize()==0){
				table.getRowList().remove(table.getSize()-1);
			}
			throw new Exception("Error: Key exist in Table!");
		}
		return key;
	}
}