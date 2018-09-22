package de.sonsts.rpi.iot.communication.common.messaage.payload;


public class CrudCommandPayload implements MessagePayload
{
    public enum Operation
    {
        CREATE, READ, UPDATE, DELETE
    }
    
    private Operation operation;

    public CrudCommandPayload()
    {
    }
    
    public CrudCommandPayload(Operation operation)
    {
        this.operation = operation;
    }

    public Operation getOperation()
    {
        return operation;
    }

    public void setOperation(Operation operation)
    {
        this.operation = operation;
    }
}
