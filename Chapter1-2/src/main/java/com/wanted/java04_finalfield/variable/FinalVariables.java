package com.wanted.java04_finalfield.variable;

public class FinalVariables {
    /*
    // class version 61.0 (61)
    // access flags 0x21
    public class com/wanted/java04_finalfield/variable/FinalVariables {

      // compiled from: FinalVariables.java

      // access flags 0x1A
      private final static Z FINAL_X = 0

      // access flags 0x1A
      private final static Z FINAL_Y = 1

      // access flags 0x1
      public <init>()V
       L0
        LINENUMBER 5 L0
        ALOAD 0
        INVOKESPECIAL java/lang/Object.<init> ()V
        RETURN
       L1
        LOCALVARIABLE this Lcom/wanted/java04_finalfield/variable/FinalVariables; L0 L1 0
        MAXSTACK = 1
        MAXLOCALS = 1

      // access flags 0x9
      public static main([Ljava/lang/String;)V
       L0
        LINENUMBER 50 L0
        GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
        LDC "y"
        INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
       L1
        LINENUMBER 53 L1
        RETURN
       L2
        LOCALVARIABLE args [Ljava/lang/String; L0 L2 0
        MAXSTACK = 2
        MAXLOCALS = 1
    }
     */

    private static final boolean FINAL_X = false;
    private static final boolean FINAL_Y = true;

    public static void main(String[] args) {
        System.out.println(FINAL_X ? "x" : "y");
    }
}
