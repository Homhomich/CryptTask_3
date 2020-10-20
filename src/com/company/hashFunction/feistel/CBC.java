package com.company.hashFunction.feistel;

import java.util.BitSet;

public class CBC {

    private BitSet[] text;
    private Feistel feistel;
    private BitSet IV;
    private BitSet[] encryptText;



    public CBC(BitSet[] text, Feistel feistel, BitSet IV) {
        this.text = text;
        this.feistel = feistel;
        this.IV = IV;
    }

    public BitSet[] encrypt() {
        BitSet[] text = new BitSet[this.text.length];
        BitSet[] encryptText = new BitSet[text.length];

        for (int i = 0; i < this.text.length; i++) {
            text[i] = this.text[i].get(0, 64);
        }

        text[0].xor(IV);
        encryptText[0] = feistel.encrypt(text[0]);

        for (int i = 1; i < text.length; i++) {
            text[i].xor(encryptText[i - 1]);
            encryptText[i] = feistel.encrypt(text[i]);
        }
        this.encryptText = encryptText;
        return encryptText;
    }

    public BitSet[] decrypt() {
        BitSet[] encryptText = new BitSet[this.encryptText.length];
        BitSet[] decryptText = new BitSet[encryptText.length];

        for (int i = 0; i < this.encryptText.length; i++) {
            encryptText[i] = this.encryptText[i].get(0, 64);
        }


        for (int i = encryptText.length - 1; i > 0; i--) {
            BitSet decryptMessage = feistel.decrypt(encryptText[i]);
            decryptMessage.xor(encryptText[i - 1]);
            decryptText[i] = decryptMessage;
        }

        BitSet decryptMessage = feistel.decrypt(encryptText[0]);
        decryptMessage.xor(IV);
        decryptText[0] = decryptMessage;

        return decryptText;
    }
}
