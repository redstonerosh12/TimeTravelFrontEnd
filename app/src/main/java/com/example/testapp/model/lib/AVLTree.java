package com.example.testapp.model.lib;

import java.time.LocalTime;

public class AVLTree {
    AVLNode root;

    // Get height of the node
    int height(AVLNode N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Update height of the node
    void updateHeight(AVLNode N) {
        if (N != null)
            N.height = Math.max(height(N.left), height(N.right)) + 1;
    }

    // Right rotate subtree rooted with y
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update parents
        if (T2 != null)
            T2.parent = y;
        x.parent = y.parent;
        y.parent = x;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left rotate subtree rooted with x
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update parents
        if (T2 != null)
            T2.parent = x;
        y.parent = x.parent;
        x.parent = y;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Get balance factor of node N
    int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Insert a key in the AVL tree
    AVLNode insert(AVLNode node, LocalTime startTime, LocalTime endTime) {
        // Perform the normal BST insertion
        if (node == null)
            return (new AVLNode(startTime, endTime));
        int cmp = 0;
        cmp = startTime.compareTo(node.startTime);
        if (cmp < 0) {
            node.left = insert(node.left, startTime, endTime);
            node.left.parent = node; // Update parent
        } else if (cmp > 0) {
            node.right = insert(node.right, startTime, endTime);
            node.right.parent = node; // Update parent
        } else // Duplicate keys not allowed
            return node;

        // Update height of this ancestor node
        updateHeight(node);

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && startTime.compareTo(node.left.startTime) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && startTime.compareTo(node.right.startTime) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && startTime.compareTo(node.left.startTime) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }


        // Right Left Case
        if (balance < -1 && startTime.compareTo(node.right.startTime) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        // return the (unchanged) node pointer
        return node;
    }

    AVLNode successorEnd(LocalTime key) {
        AVLNode parent = null;
        AVLNode node = null;
        AVLNode z = root;
        while (z != null) {
            node = z;
            if (key.compareTo(z.endTime) == -1) {
                z = z.left;
            } else {
                z = z.right;
            }
        }
        if (node.endTime.compareTo(key) == 1) {
            return node;
        }
        parent = node.parent;
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    AVLNode predecessorStart(LocalTime key) {
        AVLNode parent = null;
        AVLNode node = null;
        AVLNode z = root;
        while (z != null) {
            node = z;
            if (key.compareTo(z.startTime) == -1) {
                z = z.left;
            } else {
                z = z.right;
            }
        }
        if (node.startTime.compareTo(key) == -1) {
            return node;
        }
        parent = node.parent;
        while (parent != null && node == parent.left) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    boolean checkConflict(LocalTime startTime, LocalTime endTime) {
        if (successorEnd(startTime) == null && predecessorStart(endTime) == null) {
            if (root.startTime.isAfter(startTime) || root.endTime.isBefore(endTime)) {
                return true;
            }
        }
        if (successorEnd(startTime) == null || predecessorStart(endTime) == null) {
            return false;
        } else if (successorEnd(startTime).startTime.equals(startTime) && predecessorStart(endTime).startTime.equals(endTime) || predecessorStart(endTime).endTime.equals(endTime)) {
            return true;
        } else if (successorEnd(startTime).startTime.isBefore(endTime) && !successorEnd(startTime).startTime.equals(startTime)) {
            return true;
        } else if (predecessorStart(endTime).startTime.isAfter(startTime) && !predecessorStart(endTime).startTime.equals(endTime)) {
            return true;
        }
        return false;
    }

    void inOrder(AVLNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.startTime);
            inOrder(node.right);
        }
    }

    void preOrder(AVLNode node) {
        if (node != null) {
            System.out.println(node.startTime + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
}

class AVLNode {

    LocalTime startTime;
    LocalTime endTime;
    int height;
    AVLNode left, right, parent;

    AVLNode(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        height = 1;
        parent = left = right = null;
    }
}