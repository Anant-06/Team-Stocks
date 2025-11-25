class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }
    private void postorder(TreeNode node, List<Integer> list) {
        if (node == null) return;

        postorder(node.left, list);   
        postorder(node.right, list);  
        list.add(node.val);       
    }
}
