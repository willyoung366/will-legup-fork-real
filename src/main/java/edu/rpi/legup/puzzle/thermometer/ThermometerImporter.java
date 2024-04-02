package edu.rpi.legup.puzzle.thermometer;

import edu.rpi.legup.model.PuzzleImporter;
import edu.rpi.legup.puzzle.thermometer.ThermometerVialFactory;
import edu.rpi.legup.save.InvalidFileFormatException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;

public class ThermometerImporter extends PuzzleImporter {
    public ThermometerImporter(Thermometer thermometer) {
        super(thermometer);
    }

    @Override
    public boolean acceptsRowsAndColumnsInput() {
        return false;
    }

    @Override
    public boolean acceptsTextInput() {
        return false;
    }

    @Override
    public void initializeBoard(int rows, int columns) {

    }

    @Override
    public void initializeBoard(Node node) throws InvalidFileFormatException {
        try {
            if (!node.getNodeName().equalsIgnoreCase("board")) {
                throw new InvalidFileFormatException("thermometer Importer: cannot find board puzzleElement");
            }
            Element boardElement = (Element) node;
            if (boardElement.getElementsByTagName("vials").getLength() == 0) {
                throw new InvalidFileFormatException("thermometer Importer: no puzzleElement found for board");
            }
            Element dataElement = (Element) boardElement.getElementsByTagName("vials").item(0);
            NodeList elementDataList = dataElement.getElementsByTagName("vial");

            ThermometerBoard thermometerBoard = null;
            if (!boardElement.getAttribute("width").isEmpty() && !boardElement.getAttribute("height").isEmpty()) {
                int width = Integer.parseInt(boardElement.getAttribute("width"));
                int height = Integer.parseInt(boardElement.getAttribute("height"));

                Element rowElement = (Element) boardElement.getElementsByTagName("rowNumbers").item(0);
                NodeList rowNodeList = rowElement.getElementsByTagName("row");

                Element colElement = (Element) boardElement.getElementsByTagName("colNumbers").item(0);
                NodeList colNodeList = colElement.getElementsByTagName("col");

                if (colNodeList.getLength() != width) {
                    throw new InvalidFileFormatException("Mismatch between width and number of colNums.\n colNodeList.length:" + colNodeList.getLength() + " width:" + width);
                }
                if (rowNodeList.getLength() != height) {
                    throw new InvalidFileFormatException("thermometer Importer: no rowNumbers found for board");
                }
                //TODO: potentially have to deal with size issues and non interactable cells
                thermometerBoard = new ThermometerBoard(width, height);
            }

            if (thermometerBoard == null) {
                throw new InvalidFileFormatException("thermometer Importer: invalid board dimensions");
            }

            int width = thermometerBoard.getWidth();
            int height = thermometerBoard.getHeight();

            for (int i = 0; i < elementDataList.getLength(); i++) {
                ThermometerVialFactory.importThermometerVial(elementDataList.item(i), thermometerBoard);
            }

            //verifying all vials were used
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (thermometerBoard.getCell(x, y) == null) {
                        throw new InvalidFileFormatException("Thermometer importer Undefined tile at (" + x + "," + y + ")");
                    }
                }
            }

            puzzle.setCurrentBoard(thermometerBoard);
        } catch (NumberFormatException e) {
            throw new InvalidFileFormatException("thermometer Importer: unknown value where integer expected");
        }
    }

    @Override
    public void initializeBoard(String[] statements) throws UnsupportedOperationException, IllegalArgumentException {

    }
}