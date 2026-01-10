

class MIPS_ISA{
    private int RegDst, Branch,MemRead,MemtoReg,ALuOp,MemWrite,ALuSrc,RegWrite;
    private int PC;
    private String Instruction;
    private int Read_Data1, Read_Data2, Zero,ALU_Result;


    public static void main(String[] argv){
        String Instruction="000010010";
        int Opcode= Integer.parseInt(Instruction);
        System.out.println(Instruction);
    }


}



