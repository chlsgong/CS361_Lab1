package com.hcg359;

/**
 * Created by charlesgong on 6/28/17.
 */

public class Encryptor {
    private int[][] state;
    private int[][] roundKey;
    private int round;
    private MixColumn mixer;

    public Encryptor(int[][] plaintext, int[][] key) {
        state = plaintext;
        roundKey = key;
        round = 0;
        mixer = new MixColumn();
    }

    void encrypt() {
        for(; round < Constant.MAX_ROUNDS; round++) {
            switch(round) {
                case 0:
                    addRoundKey();
                    break;
                case 10:
                    subBytes();
                    shiftRows();
                    expandKey();
                    addRoundKey();
                    break;
                default:
                    subBytes();
                    shiftRows();
                    mixColumns();
                    expandKey();
                    addRoundKey();
                    break;
            }
        }
    }

    int[][] getState() {
        return state;
    }

    private void addRoundKey() {
        for(int c = 0; c < 4; c++) {
            for(int r = 0; r < 4; r++) {
                state[c][r] = state[c][r] ^ roundKey[c][r];
            }
        }
        printState();
    }

    private void subBytes() {
        for(int c = 0; c < 4; c++) {
            for(int r = 0; r < 4; r++) {
                state[c][r] = getSubByte(state[c][r]);
            }
        }
        printState();
    }

    private void shiftRows() {
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < r; c++) {
                int[] shiftedRow = {state[1][r], state[2][r], state[3][r], state[0][r]};
                state[0][r] = shiftedRow[0];
                state[1][r] = shiftedRow[1];
                state[2][r] = shiftedRow[2];
                state[3][r] = shiftedRow[3];
            }
        }
        printState();
    }

    private void mixColumns() {
        for(int i = 0; i < 4; i++) {
            state = mixer.mixColumn2(state, i);
        }
        printState();
    }

    private void expandKey() {
        int[] column = rotWord();
        for(int r = 0; r < 4; r++) {
            column[r] = getSubByte(column[r]);
            roundKey[0][r] = roundKey[0][r] ^ column[r] ^ Constant.RCON[round-1][r];
        }
        for(int c = 1; c < 4; c++) {
            for(int r = 0; r < 4; r++) {
                roundKey[c][r] = roundKey[c][r] ^ roundKey[c-1][r];
            }
        }
        printKey();
    }

    private int[] rotWord() {
        int[] column = {roundKey[3][1], roundKey[3][2], roundKey[3][3], roundKey[3][0]};
        return column;
    }

    private int getSubByte(int hexByte) {
        int row = (hexByte & 0xF0) >>> 4;
        int col = hexByte & 0x0F;

        int sByte = Constant.SBOX[row][col];

        return sByte;
    }

    private void printState() {
        for(int c = 0; c < 4; c++) {
            for(int r = 0; r < 4; r++) {
                String front = Integer.toHexString((state[c][r] & 0xF0) >>> 4).toUpperCase();
                String end = Integer.toHexString(state[c][r] & 0x0F).toUpperCase();
                System.out.print(front.concat(end));
            }
        }
        System.out.println();
    }

    private void printKey() {
        for(int c = 0; c < 4; c++) {
            for(int r = 0; r < 4; r++) {
                String front = Integer.toHexString((roundKey[c][r] & 0xF0) >>> 4).toUpperCase();
                String end = Integer.toHexString(roundKey[c][r] & 0x0F).toUpperCase();
                System.out.print(front.concat(end));
            }
        }
        System.out.println();
    }
}
