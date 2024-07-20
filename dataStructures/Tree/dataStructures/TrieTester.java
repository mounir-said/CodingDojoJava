package com.dataStructures;

public class TrieTester {
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Adding words to the trie
        trie.addWord("hello");
        trie.addWord("helium");
        trie.addWord("hey");
        trie.addWord("he");

        // Testing isPrefixValid
        System.out.println("Is prefix 'he' valid? " + trie.isPrefixValid("he")); // true
        System.out.println("Is prefix 'hel' valid? " + trie.isPrefixValid("hel")); // true
        System.out.println("Is prefix 'hex' valid? " + trie.isPrefixValid("hex")); // false

        // Testing isWordValid
        System.out.println("Is word 'hello' valid? " + trie.isWordValid("hello")); // true
        System.out.println("Is word 'helium' valid? " + trie.isWordValid("helium")); // true
        System.out.println("Is word 'hell' valid? " + trie.isWordValid("hell")); // false

        // Testing printAllKeys
        System.out.println("All keys in the trie:");
        trie.printAllKeys();
    }
}
