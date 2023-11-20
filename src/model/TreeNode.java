package model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private GameBoard board;
    private List<TreeNode> children;
    private int[] lastMove = new int[2];

    public TreeNode(GameBoard board) {
        this.board = board;
        this.children = new ArrayList<>();
    }

    public GameBoard getBoard() {
        return board;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void addChild(TreeNode childNode) {
        children.add(childNode);
    }

    public int[] getLastMove() {
        return lastMove;
    }

    public void setLastMove(int[] lastMove) {
        this.lastMove = lastMove;
    }
}
