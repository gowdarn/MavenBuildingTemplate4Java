package com.youdo.util.lang;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * 
 * @author shsun
 * 
 */
public class TreeNodeTest extends TestCase {

	private TreeNode instance;

	@Test
	public void test_TreeNode() {
		this.instance = new TreeNode(1, "foo");
		Assert.assertEquals("id should be 1", 1, this.instance.getNodeId());
		Assert.assertEquals("name should be foo", "foo", this.instance.getNodeName());
		Assert.assertEquals("nodeValue should be null", null, this.instance.getNodeValue());
		Assert.assertEquals("parent should be null", null, this.instance.getParentNode());
		//
		Date date = new Date();
		this.instance = new TreeNode(1, "foo", date);
		Assert.assertEquals("id should be 1", 1, this.instance.getNodeId());
		Assert.assertEquals("name should be foo", "foo", this.instance.getNodeName());
		Assert.assertEquals("nodeValue should be " + date, date, this.instance.getNodeValue());
		Assert.assertEquals("parent should be null", null, this.instance.getParentNode());
		//
		this.instance = new TreeNode(1, "foo");
		TreeNode node = new TreeNode(11, "child_1", date, this.instance);
		Assert.assertEquals("id should be 11", 11, node.getNodeId());
		Assert.assertEquals("name should be child_1", "child_1", node.getNodeName());
		Assert.assertEquals("nodeValue should be " + date, date, node.getNodeValue());
		Assert.assertEquals("parent should be null", this.instance, node.getParentNode());
	}

	@Test
	public void test_isRoot() {
		this.instance = new TreeNode(1, "foo");
		Assert.assertTrue("should be root", this.instance.isRoot());
	}

	@Test
	public void test_isLeaf() {
		this.instance = new TreeNode(1, "foo");
		Assert.assertTrue("should be leaf", this.instance.isLeaf());
	}

	@Test
	public void test_addChildNode() {
		this.instance = new TreeNode(1, "foo");
		Assert.assertEquals("children size should be 0", 0, this.instance.getChildren().size());
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		Assert.assertEquals("children size should be 1", 1, this.instance.getChildren().size());
		Assert.assertEquals("child id should be 11", 11, this.instance.getChildren().get(0).getNodeId());
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		Assert.assertEquals("children size should be 2", 2, this.instance.getChildren().size());
	}

	@Test
	public void test_getJuniorNodes() {
		this.instance = new TreeNode(1, "foo");
		Assert.assertEquals("JuniorNodes size should be 0", 0, this.instance.getJuniorNodes().size());
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		Assert.assertEquals("JuniorNodes size should be 1", 1, this.instance.getJuniorNodes().size());
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		Assert.assertEquals("JuniorNodes size should be 2", 2, this.instance.getJuniorNodes().size());
		//
		TreeNode childnode_1_1 = new TreeNode(111, "child_1_1");
		childnode_1.addChildNode(childnode_1_1);
		Assert.assertEquals("JuniorNodes size should be 3", 3, this.instance.getJuniorNodes().size());
	}

	@Test
	public void test_getElderNodes() {
		this.instance = new TreeNode(1, "foo");
		this.instance.getElderNodes();
		Assert.assertEquals("elderNodes size should be 0", 0, this.instance.getElderNodes().size());
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		Assert.assertEquals("elderNodes size of childnode_1 should be 1", 1, childnode_1.getElderNodes().size());
		Assert.assertEquals("the 1st elderNode's id of childnode_1 should be 1", 1, childnode_1.getElderNodes().get(0).getNodeId());
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		Assert.assertEquals("elderNodes size of childnode_2 should be 1", 1, childnode_2.getElderNodes().size());
		Assert.assertEquals("the 1st elderNode's id of childnode_2 should be 1", 1, childnode_2.getElderNodes().get(0).getNodeId());
		//
		TreeNode childnode_1_1 = new TreeNode(111, "child_1_1");
		childnode_1.addChildNode(childnode_1_1);
		Assert.assertEquals("elderNodes size of childnode_1_1 should be 2", 2, childnode_1_1.getElderNodes().size());
		List< TreeNode > list = childnode_1_1.getElderNodes();
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i).getNodeId();
		}
		Assert.assertEquals("sum(elderNodes.id) of childnode_1_1 should be 1+11=12", (1 + 11), sum);
	}

	@Test
	public void test_deleteNode() {
		this.instance = new TreeNode(1, "foo");
		this.instance.deleteNode();
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		//
		this.instance.deleteNode();
		Assert.assertEquals("can not delete self if the node without parent", 2, this.instance.getJuniorNodes().size());
		//
		Assert.assertEquals("JuniorNodes size should be 2 before child_1.deletedNode invoking", 2, this.instance.getJuniorNodes().size());
		childnode_1.deleteNode();
		Assert.assertEquals("JuniorNodes size should be 1 after child_1.deletedNode invoking", 1, this.instance.getJuniorNodes().size());
		//
		TreeNode childnode_2_1 = new TreeNode(121, "child_2_1");
		childnode_2.addChildNode(childnode_2_1);
		//
		Assert.assertEquals("JuniorNodes size should be 1 after child_2.deletedNode invoking", 2, this.instance.getJuniorNodes().size());
		childnode_2.deleteNode();
		Assert.assertEquals("JuniorNodes size should be 0 before child_2.deletedNode invoking", 0, this.instance.getJuniorNodes().size());
	}

	@Test
	public void test_deleteAllChildNodes() {
		this.instance = new TreeNode(1, "foo");
		this.instance.deleteAllChildNodes();
		Assert.assertEquals("JuniorNodes size should be 0 after deleteAllChildNodes invoking", 0, this.instance.getJuniorNodes().size());
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		this.instance.deleteAllChildNodes();
		Assert.assertEquals("JuniorNodes size should be 0 after deleteAllChildNodes invoking", 0, this.instance.getJuniorNodes().size());
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		//
		TreeNode childnode_2_1 = new TreeNode(121, "child_2_1");
		childnode_2.addChildNode(childnode_2_1);
		this.instance.deleteAllChildNodes();
		Assert.assertEquals("JuniorNodes size should be 0 after deleteAllChildNodes invoking", 0, this.instance.getJuniorNodes().size());
	}

	@Test
	public void test_deleteChildNode() {
		this.instance = new TreeNode(1, "foo");
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		//
		Assert.assertEquals("JuniorNodes size should be 2 before delete any child nodes", 2, this.instance.getJuniorNodes().size());
		this.instance.deleteChildNode(12);
		Assert.assertEquals("JuniorNodes size should be 1 after delete child node which without junior", 1, this.instance.getJuniorNodes().size());
		//
		TreeNode childnode_1_1 = new TreeNode(111, "child_1_1");
		childnode_1.addChildNode(childnode_1_1);
		Assert.assertEquals("JuniorNodes size should be 2 after the only child add junior", 2, this.instance.getJuniorNodes().size());
		this.instance.deleteChildNode(11);
		Assert.assertEquals("JuniorNodes size should be 0 after delete child node which with junior", 0, this.instance.getJuniorNodes().size());
	}

	@Test
	public void test_findTreeNodeById() {
		this.instance = new TreeNode(1, "foo");
		TreeNode resultnode;
		//
		resultnode = this.instance.findTreeNodeById(1);
		Assert.assertEquals("resultnode should be it self.", this.instance, resultnode);
		//
		resultnode = this.instance.findTreeNodeById(11);
		Assert.assertEquals("resultnode should be null", null, resultnode);
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		//
		resultnode = this.instance.findTreeNodeById(11);
		Assert.assertEquals("resultnode should be childnode_1", childnode_1, resultnode);
		//
		resultnode = this.instance.findTreeNodeById(12);
		Assert.assertEquals("resultnode should be childnode_2", childnode_2, resultnode);
		//
		TreeNode childnode_1_1 = new TreeNode(111, "child_1_1");
		childnode_1.addChildNode(childnode_1_1);
		resultnode = this.instance.findTreeNodeById(111);
		Assert.assertEquals("resultnode should be childnode_1_1", childnode_1_1, resultnode);
	}

	@Test
	public void test_emptyChildren() {
		this.instance = new TreeNode(1, "foo");
		this.instance.emptyChildren();
		Assert.assertEquals("JuniorNodes size should be 0 after emptyChildren", 0, this.instance.getJuniorNodes().size());
		//
		//
		TreeNode childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		//
		TreeNode childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		//
		Assert.assertEquals("JuniorNodes size should be 2 before emptyChildren", 2, this.instance.getJuniorNodes().size());
		this.instance.emptyChildren();
		Assert.assertEquals("JuniorNodes size should be 0 after emptyChildren", 0, this.instance.getJuniorNodes().size());
		//
		//
		childnode_1 = new TreeNode(11, "child_1");
		this.instance.addChildNode(childnode_1);
		//
		childnode_2 = new TreeNode(12, "child_2");
		this.instance.addChildNode(childnode_2);
		//
		TreeNode childnode_1_1 = new TreeNode(111, "child_1_1");
		childnode_1.addChildNode(childnode_1_1);
		//
		Assert.assertEquals("JuniorNodes size should be 3 before emptyChildren", 3, this.instance.getJuniorNodes().size());
		this.instance.emptyChildren();
		Assert.assertEquals("JuniorNodes size should be 0 after emptyChildren", 0, this.instance.getJuniorNodes().size());
	}
}
