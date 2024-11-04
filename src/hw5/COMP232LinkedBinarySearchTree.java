package hw5;

import com.sun.jdi.ByteType;
import java.util.NoSuchElementException;

/**
 * Linked implementation of a binary search tree. The binary search tree
 * inherits the methods from the binary tree. The add and remove methods must
 * must be overridden so that they maintain the BST Property. The contains, get
 * and set methods are overridden to improve their performance by taking
 * advantage of the BST property. The inherited size and traversal methods work
 * well as they are.
 * 
 * @author William Goble
 * @author Dickinson College
 *
 */
public class COMP232LinkedBinarySearchTree<K extends Comparable<K>, V> extends COMP232LinkedBinaryTree<K, V> {

	/*
	 * NOTE: We inherit the size and root fields, and the BTNode class from the
	 * LinkedBinaryTree class because they were declared as protected in that
	 * class.
	 */

	/**
	 * Construct an empty binary search tree.
	 */
	public COMP232LinkedBinarySearchTree() {
		super();
	}

	/**
	 * Construct a binary search tree with a single node at the root.
	 * 
	 * @param key
	 *            the key for the root.
	 * @param value
	 *            the value for the root.
	 */
	public COMP232LinkedBinarySearchTree(K key, V value) {
		super(key, value);
	}

	/**
	 * Construct a binary search tree using the provided keys and values. The
	 * key,value pairs are inserted into the tree in level order. If the
	 * resulting tree does not satisfy the BST Property, then an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param keys
	 *            the keys.
	 * @param values
	 *            the values.
	 * @throws IllegalArgumentException
	 *             if the keys and values do not have the same length, the keys
	 *             or the values are empty, or the keys are not specified in an
	 *             order that results in a valid binary search tree.
	 */
	public COMP232LinkedBinarySearchTree(K[] keys, V[] values) {
		super(keys, values);

		if (!checkBSTProperty()) {
			throw new IllegalArgumentException(
					"Key, Value pairs did not satisfy BST property.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(K key) {
		return containsHelper(root, key);
	}

	/*
	 * Recursive helper method that checks if the key can be found in the
	 * subtree rooted at subTreeRoot.
	 */
	private boolean containsHelper(BTNode<K, V> subTreeRoot, K key) {
		if (subTreeRoot == null) {
			return false; // off the tree.
		} else if (subTreeRoot.key.equals(key)) {
			return true; // found it.
		} else if (key.compareTo(subTreeRoot.key) < 0) {
			/*
			 * The key we are looking for is less than the key at the
			 * subTreeRoot so if it is in the tree it will be in the left
			 * subtree.
			 */
			return containsHelper(subTreeRoot.left, key);
		} else {
			/*
			 * The key we are looking for is greater than or equal to the key at
			 * the subTreeRoot so if it is in the tree it will be in the right
			 * subtree.
			 */
			return containsHelper(subTreeRoot.right, key);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public V get(K key) {
		return  getFriend(root, key);

	}

	public V getFriend(BTNode<K,V> subTreeRoot, K key){
		if(subTreeRoot == null){
			return null;
		}
		else if(key.equals(subTreeRoot.key)){
			return subTreeRoot.value;
		}
		else if(key.compareTo(subTreeRoot.key)<0){
			return getFriend(subTreeRoot.left, key);
		}
		else{
			return getFriend(subTreeRoot.right, key);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(K key, V value) {
		setFriend(root, key, value);
	}

	public void setFriend(BTNode<K,V> subTreeRoot, K key, V value){
		if(subTreeRoot == null){
			throw new NoSuchElementException("Where is the key????");
		}
		else if(key.equals(subTreeRoot.key)){
			subTreeRoot.value= value;
		}
		else if(key.compareTo(subTreeRoot.key)< 0){
			setFriend(subTreeRoot.left, key, value);
		}
		else{
			setFriend(subTreeRoot.right, key, value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(K key, V value) {
		BTNode<K, V> nodeToAdd = new BTNode<K, V>(key, value);

		if (size == 0) { // tree is empty!
			root = nodeToAdd;
		} else {
			addNodeToSubTree(root, nodeToAdd);
		}
		size++;
	}

	/*
	 * Add the nodeToAdd to the subtree rooted at subTreeRoot.
	 */
	private void addNodeToSubTree(BTNode<K, V> subTreeRoot,
			BTNode<K, V> nodeToAdd) {
		if (nodeToAdd.key.compareTo(subTreeRoot.key) < 0) {
			/*
			 * Key of the new node is less than the key at subTreeRoot so we are
			 * going to add the new node into the left sub tree.
			 */
			if (subTreeRoot.left == null) {
				/*
				 * The left subtree is empty, so make the new node the left
				 * child of the subtree root.
				 */
				subTreeRoot.left = nodeToAdd;
				nodeToAdd.parent = subTreeRoot;
			} else {
				/*
				 * The left subtree is not empty, so add the new node to that
				 * sub tree.
				 */
				addNodeToSubTree(subTreeRoot.left, nodeToAdd);
			}
		} else {
			/*
			 * The key of the new node is greater than or equal to the key at
			 * the subTreeRoot so we are going to add the new node to the right
			 * sub tree. This is exactly the complement of the approach used
			 * above.
			 */
			if (subTreeRoot.right == null) {
				subTreeRoot.right = nodeToAdd;
				nodeToAdd.parent = subTreeRoot;
			} else {
				addNodeToSubTree(subTreeRoot.right, nodeToAdd);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
		/**
 * Removes the node with the specified key from the BST, if it exists.
 *
 * @param key the key of the node to be removed
 * @return the value associated with the removed node, or null if the key was not found
 */
	public V remove(K key) {
    	BTNode<K, V> nodeToRemove = findNode(root, key);
    	if (nodeToRemove == null) {
       		return null;
    	}
    	V removedValue = nodeToRemove.value;
    	if (nodeToRemove.left == null && nodeToRemove.right == null) {     
        	removeLeafNode(nodeToRemove);
    	} 
		else if (nodeToRemove.left != null && nodeToRemove.right == null) {
        	replaceNodeWithChild(nodeToRemove, nodeToRemove.left);
    	} 
		else if (nodeToRemove.left == null && nodeToRemove.right != null) {
        	replaceNodeWithChild(nodeToRemove, nodeToRemove.right);
    	} 
		else {
        	BTNode<K, V> successor = findMin(nodeToRemove.right);
        	nodeToRemove.key = successor.key;
        	nodeToRemove.value = successor.value;
        	if (successor.right != null) {
            	replaceNodeWithChild(successor, successor.right);
        	} else {
           		removeLeafNode(successor);
        	}
   		}
   		size--;
    	return removedValue;
	}

	private BTNode<K, V> findNode(BTNode<K, V> subTreeRoot, K key) {
    	if (subTreeRoot == null) {
        	return null;
    	} 
		else if (key.compareTo(subTreeRoot.key) < 0) {
        	return findNode(subTreeRoot.left, key);
    	} 
		else if (key.compareTo(subTreeRoot.key) > 0) {
        	return findNode(subTreeRoot.right, key);
    	} 
		else {
        	return subTreeRoot;
		}
	}

	private void removeLeafNode(BTNode<K, V> node) {
    	if (node.parent == null) {
        	root = null;
    	} 
		else if (node.parent.left == node) {
       		node.parent.left = null;
    	} 
		else {
        	node.parent.right = null;
   		}
    	node.parent = null;
	}

	private void replaceNodeWithChild(BTNode<K, V> node, BTNode<K, V> child) {
    	if (node.parent == null) {
        	root = child;
    	} 
		else if (node.parent.left == node) {
        	node.parent.left = child;
    	} 	
		else {
        	node.parent.right = child;
		}
    	child.parent = node.parent;
    	node.parent = null;
	}

	private BTNode<K, V> findMin(BTNode<K, V> subTreeRoot) {
    	while (subTreeRoot.left != null) {
        	subTreeRoot = subTreeRoot.left;
    	}
    	return subTreeRoot;
	}

	/*
	 * Helper method that verifies the BST property of this tree by traversing
	 * the tree and verifying that at each node the key of the left child is <
	 * the key of the node and that the key of the right child is >= the key of
	 * the node.  This is used by the 
	 */
	boolean checkBSTProperty() {
		return checkBSTPropertyHelper(root);
	}

	private boolean checkBSTPropertyHelper(BTNode<K, V> subTreeRoot) {
		if (subTreeRoot == null) {
			return true; // off tree.
		} else {
			if (leftChildOK(subTreeRoot) && rightChildOK(subTreeRoot)) {
				return checkBSTPropertyHelper(subTreeRoot.left)
						&& checkBSTPropertyHelper(subTreeRoot.right);
			} else {
				return false;
			}
		}
	}

	private boolean leftChildOK(BTNode<K, V> node) {
		if (node.left == null) {
			return true;
		} else {
			// true if key at node is > key at left child.
			return node.key.compareTo(node.left.key) > 0;
		}
	}

	private boolean rightChildOK(BTNode<K, V> node) {
		if (node.right == null) {
			return true;
		} else {
			// true if key at node is <= key at right child.
			return node.key.compareTo(node.right.key) <= 0;
		}
	}
}
