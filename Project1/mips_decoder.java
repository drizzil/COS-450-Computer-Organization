import java.util.*;

// Best solution found for converting chars to binary and padding left side with 0's
// https://www.geeksforgeeks.org/java-program-to-convert-hexadecimal-to-binary/

public class mips_decoder {

  static void jFormat(String binary, int op) {
    String address = binary.substring(6, 32);
    int ad = Integer.parseInt(address, 2);
    System.out.println("J-format instruction");
    System.out.println("opcode | address");
    System.out.println("   " + op + "      " + ad);
    System.out.println("Instruction = j " + ad);
  }

  static void iFormat(String binary, int op) {
    System.out.println("I-format instruction");
    System.out.println("opcode | rs | rt | immediate/address");
    int rs = Integer.parseInt(binary.substring(6, 11), 2);
    int rt = Integer.parseInt(binary.substring(11, 16), 2);
    int ad = Integer.parseInt(binary.substring(16, 32), 2);
    System.out.println("   " + op + "      " + rs + "    " + rt + "          " + ad);
    System.out.println();
    System.out.print("instruction = ");
    if (op == 4) {
      System.out.println("beq $" + rs + ", $" + rt + ", " + ad);
    } else if (op == 23) {
      System.out.println("lw $" + rt + ", " + rs + "($" + ad + ")");
    } else {
      System.out.println("sw $" + rt + ", " + rs + "($" + ad + ")");
    }
  }

  static void rFormat(String binary, int op) {
    System.out.println("R-format instruction");
    System.out.println("opcode | rs | rt | rd | shamt | funct");
    int rs = Integer.parseInt(binary.substring(6, 11), 2);
    int rt = Integer.parseInt(binary.substring(11, 16), 2);
    int rd = Integer.parseInt(binary.substring(16, 21), 2);
    int sh = Integer.parseInt(binary.substring(21, 26), 2);
    int fu = Integer.parseInt(binary.substring(26, 32), 2);
    System.out.println("   " + op + "      " + rs + "   " + rt + "    " + rd + "     " + sh + "       " + fu);
    System.out.println();
    System.out.print("instruction = ");
    if (fu == 32) {
      System.out.println("add $" + rd + ", $" + rs + ", $" + rt);
    } else if (fu == 34) {
      System.out.println("sub $" + rd + ", $" + rs + ", $" + rt);
    } else if (fu == 36) {
      System.out.println("and $" + rd + ", $" + rs + ", $" + rt);
    } else if (fu == 37) {
      System.out.println("or $" + rd + ", $" + rs + ", $" + rt);
    } else if (fu == 42) {
      System.out.println("slt $" + rd + ", $" + rs + ", $" + rt);
    }
  }

  static void instructionFormatter(String binary) { // Check's opcode and sends binary to be formatted into instruction
    System.out.println("Binary equivalent:");
    System.out.println(binary);
    System.out.println();
    int op = Integer.parseInt(binary.substring(0, 6), 2);
    // Checks opcode for format type. Each function converts binary and prints
    // instruction.
    if (op == 0) {
      rFormat(binary, op);
    } else if (op == 2) {
      jFormat(binary, op);
    } else {
      iFormat(binary, op);
    }
  }

  static String hexToBinary(String hex) { // geeksforgeeks' solution to converting to Binary. Other solutions were
                                          // exclusive of letters, did not pad left side with 0's when needed, or just
                                          // converted the hex as if it were a single integer.

    // variable to store the converted
    // Binary Sequence
    String binary = "";

    // converting the accepted Hexadecimal
    // string to upper case
    hex = hex.toUpperCase();

    // initializing the HashMap class
    HashMap<Character, String> hashMap = new HashMap<Character, String>();

    // storing the key value pairs
    hashMap.put('0', "0000");
    hashMap.put('1', "0001");
    hashMap.put('2', "0010");
    hashMap.put('3', "0011");
    hashMap.put('4', "0100");
    hashMap.put('5', "0101");
    hashMap.put('6', "0110");
    hashMap.put('7', "0111");
    hashMap.put('8', "1000");
    hashMap.put('9', "1001");
    hashMap.put('A', "1010");
    hashMap.put('B', "1011");
    hashMap.put('C', "1100");
    hashMap.put('D', "1101");
    hashMap.put('E', "1110");
    hashMap.put('F', "1111");

    int i;
    char ch;

    // loop to iterate through the length
    // of the Hexadecimal String
    for (i = 0; i < hex.length(); i++) {
      // extracting each character
      ch = hex.charAt(i);

      // checking if the character is
      // present in the keys
      if (hashMap.containsKey(ch))

        // adding to the Binary Sequence
        // the corresponding value of
        // the key
        binary += hashMap.get(ch);

      // returning Invalid Hexadecimal
      // String if the character is
      // not present in the keys
      else {
        binary = "Invalid Hexadecimal String";
        return binary;
      }
    }

    // returning the converted Binary
    return binary;
  }

  static void decoder(String s) { // Converts hex to binary. Sends binary to be formatted.
    String binary = hexToBinary(s);
    instructionFormatter(binary);
  }

  static void driveLoop() { // Runs loop to scan and validate 8-digit hex value. Sends hex to decoder
                            // function.
    int i = 1;
    Scanner scan = new Scanner(System.in);
    while (i != 0) {
      System.out.print("Enter 8-digit hex value or exit: ");
      String hexValue = scan.nextLine();
      System.out.println();

      if (hexValue.equals("exit")) {
        i = 0;
      } else if (hexValue.length() == 8) {
        decoder(hexValue);
      } else {
        System.out.println("Wrong input, enter 8-digit hex value or exit.");
      }
    }
    scan.close();
  }

  public static void main(String[] args) {
    driveLoop();
  }
}
