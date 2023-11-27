package uz.pdp.eticket.exception;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
public class CannotBeChangedException extends RuntimeException{
    public CannotBeChangedException(String msg) {
        super(msg);
    }
}
