package algothriom.StategyModle.hanle;

public enum HandleEnum {
    HANDLE_ENUM1(1,"1"),
    HANDLE_ENUM2(2,"2"),
    HANDLE_ENUM3(3,"3"),
    HANDLE_ENUM4(4,"4");


    private int type;
    private String value;

    private HandleEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int type(){
        return type;
    }
    public String value(){
        return value;
    }

    public static HandleEnum valueOf(int type){
        for (HandleEnum handleEnum : HandleEnum.values()){
            if (handleEnum.type==type){
                return handleEnum;
            }
        }
        return null;
    }
}
