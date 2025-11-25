class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }
    private void preorder(TreeNode node, List<Integer> list) {
        if (node == null) return;
        list.add(node.val);        
        preorder(node.left, list); 
        preorder(node.right, list);
    }
}
