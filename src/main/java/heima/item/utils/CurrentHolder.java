package heima.item.utils;

public class CurrentHolder {
    public static final ThreadLocal<Integer> CURRENT_LOCAL=new ThreadLocal<>();
    public static void setCurrentId(Integer employeeId){
        CURRENT_LOCAL.set(employeeId);
    }
    public static Integer getCurrentId(){
       return CURRENT_LOCAL.get();
    }
    public static void removeCurrentId(){
        CURRENT_LOCAL.remove();
    }
}
