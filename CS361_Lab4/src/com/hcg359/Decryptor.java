package com.hcg359;

/**
 * Created by charlesgong on 6/30/17.
 */

public class Decryptor {
    private int[][] state;
    private int[][][] roundKeys;
    private int round;
    private MixColumn mixer;

    public Decryptor(int[][] key) {
        roundKeys = new int[11][4][4];
        roundKeys[0] = key;
        mixer = new MixColumn();
        expandKey();
    }

    void setState(int[][] plaintext) {
        state = plaintext;
        round = 0;
    }

    void decrypt() {
    }

    private void invAddRoundKey() {

    }

    private void invSubBytes() {

    }

    private void invShitfRows() {

    }

    private void invMixColumns() {

    }

    private void expandKey() {
        for(int i = 0; i < 10; i++) {
            int[][] currentKey = roundKeys[i];

            int[] column = rotWord(currentKey);
            for(int r = 0; r < 4; r++) {
                column[r] = getSubByte(column[r]);
                currentKey[0][r] = currentKey[0][r] ^ column[r] ^ Constant.RCON[i][r];
            }
            for(int c = 1; c < 4; c++) {
                for(int r = 0; r < 4; r++) {
                    currentKey[c][r] = currentKey[c][r] ^ currentKey[c-1][r];
                }
            }

            roundKeys[i+1] = currentKey;
        }

        for(int i = 0; i < 11; i++) {
            printKey(roundKeys[i]);
        }
    }

    private int[] rotWord(int[][] key) {
        int[] column = {key[3][1], key[3][2], key[3][3], key[3][0]};
        return column;
    }

    private int getSubByte(int hexByte) {
        int row = (hexByte & 0xF0) >>> 4;
        int col = hexByte & 0x0F;

        int sByte = Constant.SBOX[row][col];

        return sByte;
    }

    private int getInvSubByte(int hexByte) {
        int row = (hexByte & 0xF0) >>> 4;
        int col = hexByte & 0x0F;

        int sByte = Constant.INVSBOX[row][col];

        return sByte;
    }

    private void printKey(int[][] key) {
        for(int c = 0; c < 4; c++) {
            for(int r = 0; r < 4; r++) {
                String front = Integer.toHexString((key[c][r] & 0xF0) >>> 4).toUpperCase();
                String end = Integer.toHexString(key[c][r] & 0x0F).toUpperCase();
                System.out.print(front.concat(end));
            }
        }
        System.out.println();
    }
}
