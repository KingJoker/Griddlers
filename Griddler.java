public class Griddler {


    Cell[][] grid;

    public Griddlers(int size){
        grid = new Cell[size][size]  ]
    }
    public Griddlers(int height, int length){
        grid = new Cell[height][length]
    }
    public Griddlers(String fileName, int size) {
        Griddlers(size);
        parseFile(fileName);
    }
    public Griddlers(String fileName, int height, int length){
        Griddlers(height, length)
        parseFile(fileName);
    }

    private void parseFile(String )

    private class Cell{

        final static byte UNKNOWN = -1;
        final static byte EMPTY = 0;
        final static byte FULL = 1;

        byte status

        public Cell(){
            status = UNKNOWN;
        }
    }
}