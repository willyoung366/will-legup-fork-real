package edu.rpi.legup.puzzle.fillapix.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.CaseBoard;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.CaseRule;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.fillapix.FillapixBoard;
import edu.rpi.legup.puzzle.fillapix.FillapixCell;

import java.util.ArrayList;

public class BlackOrWhiteCaseRule extends CaseRule {
    public BlackOrWhiteCaseRule() {
        super("Black or White",
                "Each cell is either black or white.",
                "edu/rpi/legup/images/fillapix/cases/BlackOrWhite.png");
    }

    @Override
    public CaseBoard getCaseBoard(Board board) {
        FillapixBoard fillapixBoard = (FillapixBoard) board.copy();
        CaseBoard caseBoard = new CaseBoard(fillapixBoard, this);
        fillapixBoard.setModifiable(false);
        for (PuzzleElement data: fillapixBoard.getPuzzleElements()) {
            FillapixCell cell = (FillapixCell) data;
            if(FillapixCell.isUnknown(cell.getData())) {
                caseBoard.addPickableElement(data);
            }
        }
        return caseBoard;
    }

    @Override
    public ArrayList<Board> getCases(Board board, PuzzleElement puzzleElement){
        ArrayList<Board> cases = new ArrayList<>();

        Board case1 = board.copy();
        FillapixCell cell1 = (FillapixCell) case1.getPuzzleElement(puzzleElement);
        cell1.setData(cell1.getData() + FillapixCell.BLACK);
        case1.addModifiedData(cell1);
        cases.add(case1);

        Board case2 = board.copy();
        FillapixCell cell2 = (FillapixCell) case2.getPuzzleElement(puzzleElement);
        cell2.setData(cell2.getData() + FillapixCell.BLACK + FillapixCell.WHITE);
        case2.addModifiedData(cell2);
        cases.add(case2);

        return cases;
    }

    @Override
    public String checkRuleRaw(TreeTransition transition) { return null; }

    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) { return null; }

    @Override
    public boolean doDefaultApplication(TreeTransition transition) { return false; }

    @Override
    public boolean doDefaultApplicationAt(TreeTransition transition, PuzzleElement puzzleElement) { return false; }
}