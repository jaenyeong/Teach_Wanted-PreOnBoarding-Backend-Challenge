package com.wanted.java04_finalfield.variable;

public class NonFinalVariables {
    /*
    // class version 61.0 (61)
    // access flags 0x21
    public class com/wanted/java04_finalfield/variable/NonFinalVariables {

      // compiled from: NonFinalVariables.java

      // access flags 0xA
      private static Z nonFinalX

      // access flags 0xA
      private static Z nonFinalY

      // access flags 0x1
      public <init>()V
       L0
        LINENUMBER 5 L0
        ALOAD 0
        INVOKESPECIAL java/lang/Object.<init> ()V
        RETURN
       L1
        LOCALVARIABLE this Lcom/wanted/java04_finalfield/variable/NonFinalVariables; L0 L1 0
        MAXSTACK = 1
        MAXLOCALS = 1

      // access flags 0x9
      public static main([Ljava/lang/String;)V
       L0
        LINENUMBER 70 L0
        GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
        GETSTATIC com/wanted/java04_finalfield/variable/NonFinalVariables.nonFinalX : Z
        IFEQ L1
        LDC "x"
        GOTO L2
       L1
       FRAME SAME1 java/io/PrintStream
        LDC "y"
       L2
       FRAME FULL [[Ljava/lang/String;] [java/io/PrintStream java/lang/String]
        INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
       L3
        LINENUMBER 74 L3
        RETURN
       L4
        LOCALVARIABLE args [Ljava/lang/String; L0 L4 0
        MAXSTACK = 2
        MAXLOCALS = 1

      // access flags 0x8
      static <clinit>()V
       L0
        LINENUMBER 66 L0
        ICONST_0
        PUTSTATIC com/wanted/java04_finalfield/variable/NonFinalVariables.nonFinalX : Z
       L1
        LINENUMBER 67 L1
        ICONST_1
        PUTSTATIC com/wanted/java04_finalfield/variable/NonFinalVariables.nonFinalY : Z
        RETURN
        MAXSTACK = 1
        MAXLOCALS = 0
    }
     */

    private static boolean nonFinalX = false;
    private static boolean nonFinalY = true;

    public static void main(String[] args) {
        System.out.println(nonFinalX ? "x" : "y");
    }
}
