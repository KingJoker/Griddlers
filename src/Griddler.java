
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Griddler {


    Cell[][] grid;
    ArrayList<Integer>[] rowNums;
    ArrayList<Integer>[] colNums;

    public Griddler(int size){
        this(size,size);
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
                case "size":
                    int height = scan.nextInt();
                    int length = scan.nextInt();
                    grid = new Cell[height][length];

                    rowNums = new ArrayList[height];
                    colNums = new ArrayList[length];
                    break;

                case "rows":
                    for (int i = 0; i < rowNums.length; i++) {
                        Scanner numbers = new Scanner(scan.nextLine());
                        ArrayList<Integer>  list = new ArrayList<>();
                        while (numbers.hasNextByte()) { list.add(numbers.nextInt());}
                        rowNums[i] = list;
                    }
                    break;

                case "cols":
                    for (int i = 0; i < colNums.length; i++) {
                        Scanner numbers = new Scanner(scan.nextLine());
                        ArrayList<Integer>  list = new ArrayList<>();
                        while (numbers.hasNextByte()) { list.add(numbers.nextInt());}
                        colNums[i] = list;
                    }
                    break;

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
    public Cell[] getRow(int rowIndex) {
        return grid[rowIndex];
    }
    public void setRow(int rowIndex, Cell[] row){
        for(int c = 0; c < grid[rowIndex].length; c++){
            grid[rowIndex][c] = row[c];
        }
    }
    public Cell[] getCol(int colIndex){
        Cell[] col = new Cell[grid.length];
        for (int i = 0; i < grid.length; i++) {
            col[i] = grid[i][colIndex];
        }
        return col;
    }
    public void setCol(int colIndex, Cell[] col){
        for(int r = 0; r < grid.length; r++){
            grid[r][colIndex] = col[r];
        }
    }

    private boolean isLineSolved_old(Cell[] line, ArrayList<Integer> num){
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

    private static boolean isLineSolved(Cell[] line, ArrayList<Integer> nums){
        int lineIndex = 0;
        // for each number we have to match
        for(int numIndex = 0; numIndex < nums.size(); numIndex++){
            // If we've reached the end of the line before we have checked all numbers
            if(lineIndex >= line.length){
                return false;
            }

            // Find left boundary
            // Move past empty cells until we find a filled one,
            // which will be the start location to match with for this number
            while(line[lineIndex].status != Cell.FULL) {
                if(line[lineIndex].status == Cell.UNKNOWN){
                    return false;
                }
                lineIndex++;
                //if there are not enough remaining spaces in line to be able to match number,
                //  then the line is definitely not solved
                if(lineIndex + nums.get(numIndex) > line.length)
                    return false;
            }
            //check for specified number of filled spaces
            for(int i = 0; i < nums.get(numIndex); i++){
                if(line[lineIndex++].status != Cell.FULL){
                    return false;
                }
            }
            // lineIndex points to the cell after the expected number of filled spaces
            // if we are not at the end of the line, it should not be filled
            if((lineIndex < line.length) && line[lineIndex].status != Cell.EMPTY){
                return false;
            }
        }
        // The rest of the line should not have filled cells
        for(int i = lineIndex; i < line.length; i++){
            if(line[lineIndex].status != Cell.EMPTY){
                return false;
            }
        }
        // If we've made it this far, line is valid
        return true;
    }

   /* public ArrayList<Cell[]> permutate(Cell[] line, ArrayList<Byte> nums){
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
    }*/

    public static Set<Cell[]> permutateLine(Cell[] previousPerm, List<Integer> numbers, int lineStartIndex, int numberIndex){
        // Calculate minimum number of cells required to fit the numbers after the current number
        // Each remaining number requires a blank cell before it
        int remainingCellsRequired = numbers.size() - (numberIndex + 1);
        // Add the remaining numbers
        for(int i = numberIndex + 1; i < numbers.size(); i++){
            remainingCellsRequired += numbers.get(i);
        }
        HashSet<Cell[]> permutations = new HashSet<>();
        //Once we've seen a filled space, it becomes the right boundary of the startIndex for this number
        boolean seenStartFilled = false;
        nextStartIndex:for(int i = lineStartIndex; ((i + numbers.get(numberIndex) + remainingCellsRequired) <= previousPerm.length) & (!seenStartFilled); i++){
            Cell[] permutation = previousPerm.clone();
            for(int j = lineStartIndex; j < i; j++){
                permutation[j] = new Cell(Cell.EMPTY);
            }
            for(int j = 0; j < numbers.get(numberIndex); j++){
                switch(permutation[i+j].status){
                    case(Cell.UNKNOWN):
                        permutation[i+j] = new Cell(Cell.FULL);
                        break;
                    case(Cell.EMPTY):
                        continue nextStartIndex;
                    case(Cell.FULL):
                        if(j == 0) {
                            seenStartFilled = true;
                        }
                }
            }
            // If we require remaining cells, we must have an empty space after us
            if(remainingCellsRequired > 0){
                switch(permutation[i+numbers.get(numberIndex)].status){
                    case(Cell.UNKNOWN):
                        permutation[i+numbers.get(numberIndex)] = new Cell(Cell.EMPTY);
                        break;
                    case(Cell.FULL):
                        continue nextStartIndex;
                }
            }
            // If we are on the last number, the remaining cells in line must be empty
            if(numberIndex == numbers.size() - 1){
                for(int j = i + numbers.get(numberIndex); j < previousPerm.length; j++){
                    switch(permutation[j].status){
                        case(Cell.UNKNOWN):
                            permutation[j] = new Cell(Cell.EMPTY);
                            break;
                        case(Cell.FULL):
                            continue nextStartIndex;
                    }
                }
                permutations.add(permutation);
            }
            else{
                permutations.addAll(permutateLine(permutation,numbers,numbers.get(numberIndex) + 1 + i,numberIndex + 1));
            }
        }
        return permutations;
    }

    private static Cell[] reduce(Set<Cell[]> lines, ArrayList<Integer> numbers){
        Cell[] intersection = intersection(lines);
        if(intersection != null && isLineSolved(intersection,numbers)){
            for(Cell cell : intersection){
                if(cell.equals(Cell.UNKNOWN)){
                    cell.set(Cell.EMPTY);
                }
            }
        }
        return intersection;
    }

    private static Cell[] intersection(Set<Cell[]> lines){
        Optional<Cell[]> reduced = lines.parallelStream().reduce((line1, line2) ->{
            Cell[] ret = new Cell[line1.length];
            for(int i = 0; i < ret.length; i++){
                if(line1[i].equals(line2[i])){
                    ret[i] = line1[i];
                }
                else{
                    ret[i] = new Cell(Cell.UNKNOWN);
                }
            }
            return ret;
        });
        return reduced.isPresent()? reduced.get() : null;
    }

    public void solve(){
        boolean modified = true;
        while(modified){
            modified = false;
            for(int r = 0; r < grid.length; r++){
                if(!isRowSolved(r)){
                    Cell[] row = getRow(r);
                    Set<Cell[]> permutations = permutateLine(row,rowNums[r],0,0);
                    Cell[] solvedCells = reduce(permutations,rowNums[r]);
                    if(solvedCells != null && !Arrays.equals(row,solvedCells)){
                        setRow(r,solvedCells);
                        modified |= true;
                    }
                }
            }
            for(int c = 0; c < grid[0].length; c++){
                if(!isColSoved(c)){
                    Cell[] col = getCol(c);
                    Set<Cell[]> permutations = permutateLine(col,colNums[c],0,0);
                    Cell[] solvedCells = reduce(permutations,colNums[c]);
                    if(solvedCells != null && !Arrays.equals(col,solvedCells)){
                        setCol(c,solvedCells);
                        modified |= true;
                    }
                }
            }
        }
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
    private static class Cell{

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

        public void set(byte status){this.status = status;}

        public Cell clone(){
            return new Cell(status);
        }

        public boolean equals(byte type){
            return status == type;
        }

        public boolean equals(Object o){
            if(! (o instanceof Cell))
                return false;
            Cell other = (Cell) o;
            return status == other.status;
        }

        public String toString(){
            if(status == UNKNOWN) return "\u25af";//\u2001";
            if(status == EMPTY) return "\u2591";
            return "\u2593";
        }
    }
}