package com.dsp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

//Exception Element Already Exist.
public class AlreadyExistsException extends RuntimeException 
{
    private static final long serialVersionUID = 1L;
    private String msg;
    private int id;
}
