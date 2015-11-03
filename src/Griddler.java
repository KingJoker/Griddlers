
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Griddler {


    Cell[][] grid;
    ArrayList[] rowNums;
    ArrayList[] colNums;

    public Griddler(int size){
        grid = new Cell[size][size];
        rowNums = new ArrayList[size];
        colNums = new ArrayList[size];
        initiateGrid();
    }
    public Griddler(int height, int length){
        grid = new Cell[height][length];

        rowNums = new ArrayList[height];
        colNums = new ArrayList[length];
        initiateGrid();
    }
    public Griddler(String fileName, int size) throws FileNotFoundException {
        this(size);
        parseFile(fileName);
    }
    public Griddler(String fileName, int height, int length) throws FileNotFoundException {
        this(height, length);
        parseFile(fileName);
        initiateGrid();
    }
    public Griddler(String fileName) throws FileNotFoundException {
        parseFile(fileName);
        initiateGrid();
    }

    private void parseFile(String fileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            switch (line) {
                case "size": {
                    int height = scan.nextInt();
                    int length = scan.nextInt();
                    grid = new Cell[height][length];

                    rowNums = new ArrayList[height];
                    colNums = new ArrayList[length];
                    break;
                }
                case "rows":{
                    for (int i = 0; i < rowNums.length; i++) {
                        Scanner number = new Scanner(scan.nextLine());
                        ArrayList<Byte>  list = new ArrayList<>();
                        while (number.hasNextByte()) { list.add(number.nextByte());}
                        rowNums[i] = list;
                    }
                    break;
                }
                case "cols":{
                    for (int i = 0; i < colNums.length; i++) {
                        Scanner number = new Scanner(scan.nextLine());
                        ArrayList<Byte>  list = new ArrayList<>();
                        while (number.hasNextByte()) { list.add(number.nextByte());}
                        colNums[i] = list;
                    }
                    break;
                }
            }
        }


    }

    public void sweep (Cell[] row, ArrayList<Byte> nums){
        Cell[] startingRow = row.clone();

    }



    public Cell[] clone (Cell[] row){
        Cell[] clone = new Cell[row.length];
        for (int i = 0; i < row.length; i++) {
            clone[i] = row[i].clone();
        }
        return clone;
    }
    public Cell[] getRow(int index) {
        return grid[index];
    }
    public Cell[] getCol(int index){
        Cell[] col = new Cell[grid.length];
        for (int i = 0; i < grid.length; i++) {
            col[i] = grid[i][index];
        }
        return col;
    }

    private boolean isLineSolved(Cell[] line, ArrayList<Integer> num){
        int lineIndex = 0;
        int numIndex = 0;
        boolean solved = true;
        while (numIndex < num.size()) {
            while((line[lineIndex].equals(Cell.FULL)) && num.get(numIndex)>0){
                lineIndex++;
                num.set(numIndex,num.get(numIndex)-1);
            }
            if(!line[lineIndex].equals(Cell.FULL)) {
                numIndex++;
            }
            if(numIndex > num.size()){
                solved = true;
            }

            while((line[lineIndex].equals(Cell.EMPTY)) || (line[lineIndex].equals(Cell.UNKNOWN))){
                lineIndex ++;
                if(lineIndex>line.length) {
                    return false;
                }
            }
        }
        return false;
    }

    public ArrayList<Cell[]> permutate(Cell[] line, ArrayList<Byte> nums){
        ArrayList<Cell[]> permutations = new ArrayList<>();
        int numLength = nums.size()-1;
        byte[] numsArray = new byte[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            currentNums[i] = nums.get(i);
        }
        for (Byte num : nums) {
            numLength+= num;
        }

        Cell[] newLine = new Cell[line.length];
        int numIndex = 0;
        int lineIndex = 0;
        byte[] currentNums = numsArray.clone();
        while(lineIndex){
            if(line[lineIndex].equals(Cell.EMPTY)){
                lineIndex++;
                continue;
            }
            if(numIndex >= currentNums.length){
                while(lineIndex < newLine.length) {
                    newLine[lineIndex++] = new Cell(Cell.EMPTY);
                }
                break;
            }
            int emptySpaces = 0;
            int emptyIndex = lineIndex;
            while(emptyIndex < newLine.length){
                //current working location
            }
        }
        permutations.add(newLine);

        return permutations;
    }

    private boolean isRowSolved(int row){
        return isLineSolved(getRow(row),rowNums[row]);
    }

    private boolean isColSoved(int col){
        return isLineSolved(getCol(col),colNums[col]);
    }

    public boolean isSolved(){
        boolean solved = true;

        for (int r = 0; r < grid.length && solved; r++) {
            solved &= isRowSolved(r);
        }

        for (int c = 0; c < grid[0].length && solved; c++) {
            solved &= isColSoved(c);
        }

        return solved;
    }

    public String toString(){
        String out = "";
        for (int i = 0; i < grid.length; i++) {
            for (int i1 = 0; i1 < grid[i].length; i1++){
                out+=grid[i][i1].toString();
            }
            out+="\n";
        }
        return out;
    }

    private void initiateGrid(){
        for (int i = 0; i < grid.length; i++) {
            for (int i1 = 0; i1 < grid[i].length; i1++) {
                grid[i][i1] = new Cell();
            }
        }
    }
    private class Cell{

        final static byte UNKNOWN = -1;
        final static byte EMPTY = 0;
        final static byte FULL = 1;

        byte status;

        public Cell(){
            status = UNKNOWN;
        }

        public Cell(byte status){
            this.status = status;
        }

        public Cell clone(){
            return new Cell(status);
        }

        public boolean equals(byte type){
            return status == type;
        }

        public boolean equals(Object o){
            Cell other = (Cell) o;
            return status == other.status;
        }

        public String toString(){
            if(status == UNKNOWN) return ".";
            if(status == EMPTY) return "X";
            return "&";
        }
    }
}