package data;

public enum PrintTemplate {

    //프롬프트 입력 창
    PROMPT("%s >"),

    //구분선
    BOLDLINE("=================================================="),
    INTERLINE("--------------------------------------------------"),

    //메뉴 지시문
    MENU_FIRST_INSTRUCT("|type “/help” to learn how to use this program."),
    MENU_LAST_SAVE("|the last save file and the list of save files"),

    //게임 지시문
    GAME_BASE_INSTRUCT("Enter the STARTING and ENDING positions of the piece (e.g., \"e2 e4\")");

    private final String printTmp;
    PrintTemplate(String printTmp){
        this.printTmp = printTmp;
    }

    @Override
    public String toString() {
        return printTmp;
    }
}
