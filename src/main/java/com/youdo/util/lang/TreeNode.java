package com.youdo.util.lang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author shsun
 * 
 */
public class TreeNode implements Serializable {

	private static final long serialVersionUID = -1631052081543550944L;
	//
	private int parentNodeId;
	protected TreeNode parentNode;
	//
	private int nodeId;
	protected String nodeName;
	protected Object nodeValue;
	//
	protected List< TreeNode > children;

	public TreeNode(int nodeId, String nodeName) {
		this(nodeId, nodeName, null, null);
	}

	public TreeNode(int nodeId, String nodeName, Object nodeValue) {
		this(nodeId, nodeName, nodeValue, null);
	}

	public TreeNode(int nodeId, String nodeName, Object nodeValue, TreeNode parentNode) {
		this.emptyChildren();
		//
		this.setNodeId(nodeId);
		this.setNodeName(nodeName);
		this.setNodeValue(nodeValue);
		this.setParentNode(parentNode);
	}

	public boolean isLeaf() {
		return this.getChildren().isEmpty();
	}

	public boolean isRoot() {
		return this.getParentNode() == null;
	}

	/** 插入一个child节点到当前节点中 */
	public void addChildNode( TreeNode node ) {
		this.getChildren().add(node);
		node.setParentNode(this);
	}

	/** 返回当前节点的父辈节点集合 */
	public List< TreeNode > getElderNodes() {
		List< TreeNode > list = new ArrayList< TreeNode >();
		TreeNode parentNode = this.getParentNode();
		if (parentNode != null) {
			list.add(parentNode);
			list.addAll(parentNode.getElderNodes());
		}
		return list;
	}

	/** 返回当前节点的晚辈集合 */
	public List< TreeNode > getJuniorNodes() {
		List< TreeNode > list = new ArrayList< TreeNode >();
		List< TreeNode > children = this.getChildren();
		if (children != null) {
			int n = children.size();
			for (int i = 0; i < n; i++) {
				TreeNode node = children.get(i);
				list.add(node);
				list.addAll(node.getJuniorNodes());
			}
		}
		return list;
	}

	/** 删除节点和它下面的晚辈 */
	/**
	 * delete self.
	 */
	public void deleteNode() {
		TreeNode parentNode = this.getParentNode();
		int id = this.getNodeId();
		if (parentNode != null) {
			parentNode.deleteChildNode(id);
		}
	}

	public void deleteAllChildNodes() {
		List< TreeNode > list = this.getChildren();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			this.deleteChildNode(list.get(i).getNodeId());
		}
	}

	/** 删除当前节点的某个子节点 */
	public void deleteChildNode( int childId ) {
		List< TreeNode > list = this.getChildren();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			if (list.get(i).getNodeId() == childId) {
				list.remove(i);
				break;
			}
		}
	}

	/** 找到一颗树中某个节点 */
	public TreeNode findTreeNodeById( int id ) {
		TreeNode node = null;
		if (this.getNodeId() == id) {
			node = this;
		} else if (!this.getChildren().isEmpty()) {
			int n = this.getChildren().size();
			for (int i = 0; i < n; i++) {
				TreeNode tmpNode = this.getChildren().get(i).findTreeNodeById(id);
				if (tmpNode != null) {
					node = tmpNode;
					break;
				}
			}
		}
		return node;
	}

	/** 遍历一棵树，层次遍历 */
	public void traverse() {
		if (this.getChildren().isEmpty()) {
			return;
		}
		System.out.println("nodeId=" + this.getNodeId() + ", nodeName=" + this.getNodeName());
		int n = this.getChildren().size();
		for (int i = 0; i < n; i++) {
			children.get(i).traverse();
		}
	}

	public void emptyChildren() {
		this.setChildren(new ArrayList< TreeNode >());
	}

	public void setChildren( List< TreeNode > childList ) {
		this.children = childList;
	}

	public List< TreeNode > getChildren() {
		return children;
	}

	public int getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId( int parentNodeId ) {
		this.parentNodeId = parentNodeId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId( int nodeId ) {
		this.nodeId = nodeId;
	}

	public TreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode( TreeNode parentNode ) {
		this.parentNode = parentNode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName( String nodeName ) {
		this.nodeName = nodeName;
	}

	public Object getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue( Object nodeValue ) {
		this.nodeValue = nodeValue;
	}
}
