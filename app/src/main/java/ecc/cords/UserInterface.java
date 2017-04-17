package ecc.cords;

public class UserInterface{

	private TableManager tableMgr;
	private int colIndex, rowIndex;

	public UserInterface(String[] args){
		initApplication(args);
		startApplication();
	}

	private void addCell() throws IndexOutOfBoundsException, Exception{
		tableMgr.addCell(InputHelper.askNumericInput("y"),inputKey("Enter Key: "),InputHelper.askString("Enter Value: "), true);
		System.out.println("Sucessfully Added Cell!");
	}

	private void askCoordinates() throws IndexOutOfBoundsException, Exception{
		rowIndex = tableMgr.validateCoordinate("y",InputHelper.askNumericInput("y"));
		colIndex = tableMgr.validateCoordinate("x",InputHelper.askNumericInput("x"), rowIndex);
		System.out.println("(" + rowIndex + "," + colIndex + ")");
	}

	private void displayTable(){
		System.out.println("\nTABLE DATA:\n" + tableMgr.getTableData());
	}

	private void editKey() throws IndexOutOfBoundsException, Exception{
		askCoordinates();
		tableMgr.editKey(rowIndex, colIndex, inputKey("Enter New Key: "));
		System.out.println("Sucessfully Edited Key!");
	}

	private void editValue() throws IndexOutOfBoundsException, Exception{
		askCoordinates();
		tableMgr.editValue(rowIndex, colIndex, InputHelper.askString("Enter New Value: "));
		System.out.println("Sucessfully Edited Value!");
	}

	private String inputKey(String msg) throws Exception{
		return tableMgr.validateKey(InputHelper.askString(msg));
	}
	
	private void initApplication(String[] args){
		FileHandler.setFileName(args.length > 0? args[0] : "Default.txt");

		tableMgr = new TableManager();
		System.out.println("Duplicate keys removed from file: " + tableMgr.createTable());
		displayTable();
	}

	private void startApplication(){
		boolean isRunning = true;

		while(isRunning){
			System.out.println("CHOICES:");
			System.out.println("1. PRINT        5. EDIT VALUE");
			System.out.println("2. SEARCH       6. SORT ASCENDING");
			System.out.println("3. ADD CELL     7. SORT DESCENDING");
			System.out.println("4. EDIT KEY     8. EXIT");
			
			String choice = InputHelper.askString("What do you want to do? (Enter choice number): ");
			
			try{
				switch(choice){
					case "1":
						displayTable();
						break;
					case "2":
						System.out.println(tableMgr.search(InputHelper.askString("Enter pattern to be searched: ")));
						break;
					case "3":
						addCell();
						break;
					case "4":
						editKey();
						break;
					case "5":
						editValue();
						break;
					case "6":
						tableMgr.sort(true);
						displayTable();
						break;
					case "7":
						tableMgr.sort(false);
						displayTable();
						break;
					case "8":
						System.exit(0);
					default:
						System.out.println("Invalid choice!\n");
				}
			}catch(Exception exception){
				System.out.println(exception.getMessage() + "\n");
			}
		}
	}
}