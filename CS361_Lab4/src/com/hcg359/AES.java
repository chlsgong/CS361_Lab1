package com.hcg359;

/**
 * Created by charlesgong on 6/28/17.
 */

import java.io.IOException;


public class AES {
    public static void main(String[] args) {
        String flag = args[0];
        String keyFile = args[1];
        String inputFile = args[2];

        IOUtility ioUtility;
        Encryptor encryptor;
        Decryptor decryptor;

        switch(flag) {
            case "e":
                // encrypt
                try {
                    ioUtility = new IOUtility(inputFile, keyFile, inputFile + ".enc");

                    int[][] key = ioUtility.getKey();
                    int[][] matrix;
                    int[][] encrypted;

                    while((matrix = ioUtility.getNextMatrix()) != null) {
                        encryptor = new Encryptor(matrix, key);
                        encryptor.encrypt();
                        encrypted = encryptor.getState();

                        ioUtility.writeMatrix(encrypted);
                    }

                    ioUtility.close();
                }
                catch(IOException e) {
                    e.getLocalizedMessage();
                }

                break;
            case "d":
                // decrypt
                try {
                    ioUtility = new IOUtility(inputFile, keyFile, inputFile + ".dec");

                    int[][] key = ioUtility.getKey();
                    int[][] matrix;
                    int[][] decrypted;

                    while((matrix = ioUtility.getNextMatrix()) != null) {
                        decryptor = new Decryptor(key);
                        decryptor.decrypt();
                        // decrypted = decryptor.getState();

                        // ioUtility.writeMatrix(decrypted);
                    }

                    ioUtility.close();
                }
                catch(IOException e) {
                    e.getLocalizedMessage();
                }

                break;
            default:
                break;
        }
    }

}
