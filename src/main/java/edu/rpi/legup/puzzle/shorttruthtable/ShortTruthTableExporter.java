package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.PuzzleExporter;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import org.w3c.dom.Document;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ShortTruthTableExporter extends PuzzleExporter {

    public ShortTruthTableExporter(@NotNull ShortTruthTable stt) {
        super(stt);
    }

    @Override
    @Contract(pure = true)
    protected @NotNull org.w3c.dom.Element createBoardElement(Document newDocument) {
        ShortTruthTableBoard board;
        if (puzzle.getTree() != null) {
            board = (ShortTruthTableBoard) puzzle.getTree().getRootNode().getBoard();
        } else {
            board = (ShortTruthTableBoard) puzzle.getBoardView().getBoard();
        }

        org.w3c.dom.Element boardElement = newDocument.createElement("board");

        org.w3c.dom.Element dataElement = newDocument.createElement("data");

        ShortTruthTableStatement[] statements = board.getStatements();
        for (int i = 0; i < statements.length; i++) {
            org.w3c.dom.Element statementElement = newDocument.createElement("statement");
            statementElement.setAttribute("representation", statements[i].getStringRep());
            statementElement.setAttribute("row_index", String.valueOf(i));
            dataElement.appendChild(statementElement);
        }

        for (PuzzleElement puzzleElement : board.getPuzzleElements()) {
            ShortTruthTableCell cell = board.getCellFromElement(puzzleElement);
            if (!cell.getType().isTrueOrFalse()) continue;

            org.w3c.dom.Element cellElement =
                    puzzle.getFactory().exportCell(newDocument, puzzleElement);
            dataElement.appendChild(cellElement);
        }

        boardElement.appendChild(dataElement);
        return boardElement;
    }
}
