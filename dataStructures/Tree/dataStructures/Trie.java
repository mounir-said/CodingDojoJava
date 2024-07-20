package com.dataStructures;

import java.util.Map;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Method to add a word to the trie
    public void addWord(String word) {
        TrieNode currentNode = root;
        for (char c : word.toCharArray()) {
            currentNode.children.putIfAbsent(c, new TrieNode());
            currentNode = currentNode.children.get(c);
        }
        currentNode.isEndOfWord = true;
    }

    // Method to check if a prefix is valid
    public boolean isPrefixValid(String prefix) {
        TrieNode currentNode = root;
        for (char c : prefix.toCharArray()) {
            if (!currentNode.children.containsKey(c)) {
                return false;
            }
            currentNode = currentNode.children.get(c);
        }
        return true;
    }

    // Method to check if a word is valid
    public boolean isWordValid(String word) {
        TrieNode currentNode = root;
        for (char c : word.toCharArray()) {
            if (!currentNode.children.containsKey(c)) {
                return false;
            }
            currentNode = currentNode.children.get(c);
        }
        return currentNode.isEndOfWord;
    }

    // Method to print all keys in the trie
    public void printAllKeys() {
        printAllKeys(root, "");
    }

    // Helper method to print all keys in the trie recursively
    private void printAllKeys(TrieNode node, String prefix) {
        if (node == null) return;
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            System.out.println(prefix + entry.getKey());
            printAllKeys(entry.getValue(), prefix + entry.getKey());
        }
    }
}
